package com.echo.dapc.mvvm.view.task;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.echo.dapc.R;
import com.echo.dapc.adapter.FlexboxLayoutAdapter;
import com.echo.dapc.adapter.SimpleTagListAdapter;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.bean.entity.LocalTask;
import com.echo.dapc.bean.enumeration.TaskTaggingSceneType;
import com.echo.dapc.bean.enumeration.TaskVisibleLevel;
import com.echo.dapc.bean.logic.DataFile;
import com.echo.dapc.bean.logic.taggingscene.LabelClassification;
import com.echo.dapc.bean.ui.FlexBoxItem;
import com.echo.dapc.databinding.ActivityTaskEditingInfoBinding;
import com.echo.dapc.interfaces.callback.task.QueryLocalTaskCallback;
import com.echo.dapc.mvvm.model.task.TaskEditingInfoModel;
import com.echo.dapc.mvvm.viewmodel.taskinfo.TaskEditingInfoViewModel;
import com.echo.dapc.util.business.TaskUtil;
import com.echo.dapc.util.kt.DialogUtil;
import com.echo.dapc.util.system.FileUtil;
import com.echo.dapc.util.system.JsonUtil;
import com.echo.dapc.util.system.ThreadUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskEditingInfoActivity extends BaseDataBindingActivity<TaskEditingInfoViewModel, TaskEditingInfoModel, ActivityTaskEditingInfoBinding> {

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_task_editing_info;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        getBinding().taskEditTbTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                if (getViewModel().getModel().isHasEdited()) {
                    getViewModel().saveTask();
                } else {
                    finish();
                }
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                getViewModel().openMenu(getBinding().taskEditTbTitle.getRightView());
            }
        });
    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {
        SimpleTagListAdapter tagsAdapter = new SimpleTagListAdapter(new ArrayList<>());
        FlexboxLayoutAdapter tagOptionsAdapter = new FlexboxLayoutAdapter(new ArrayList<>());
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this);
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setJustifyContent(JustifyContent.FLEX_START);
        getBinding().options.setLayoutManager(manager);

        tagsAdapter.setClickListener((itemview, position) -> {
            DialogUtil.editTag(this, "修改标签", tagsAdapter.getItem(position).getContent(), (materialDialog, charSequence) -> {
                FlexBoxItem item = tagsAdapter.getItem(position);
                item.setContent(charSequence.toString());
                tagsAdapter.changeItem(item, position);
                getViewModel().getModel().setHasEdited(true);
                return null;
            }, materialDialog -> {
                tagsAdapter.removeItem(position);
                getViewModel().getModel().setHasEdited(true);
                return null;
            });
        });
        tagOptionsAdapter.setLongClickListener((itemview, position) -> {
            tagOptionsAdapter.removeItem(position);
            getViewModel().getModel().setHasEdited(true);
        });
        getViewModel().getModel().setTagsAdapter(tagsAdapter);
        getViewModel().getModel().setTagOptionsAdapter(tagOptionsAdapter);

        long taskIndex = getLongExtra("task_index", 0);
        if (taskIndex!=0) {
            TaskUtil.queryLocalTask(new QueryLocalTaskCallback() {
                @Override
                public void onSuccess(LocalTask task) {
                    getViewModel().getModel().setTask(task);
                    List<FlexBoxItem> tags = new ArrayList<>();
                    for (String tag:task.getTags()){
                        tags.add(new FlexBoxItem(tag));
                    }
                    List<FlexBoxItem> tagOptions = new ArrayList<>();

                    if (task.getTaggingSceneType()== TaskTaggingSceneType.LABEL_CLASSIFICATION.getIndex()) {
                        getBinding().taskEditTagSceneType.setSelection(0);
                        getViewModel().getModel().setLabelClassification(JsonUtil.fromJson(task.getTaggingSceneSettings(), LabelClassification.class));
                        if (getViewModel().getModel().getLabelClassification().isMultiSelect()) {
                            getBinding().multiSelect.setChecked(true);
                        }
                        if (getViewModel().getModel().getLabelClassification().isCustomOption()) {
                            getBinding().customOption.setChecked(true);
                        }
                        if (getViewModel().getModel().getLabelClassification().getTagOptions() != null) {
                            for (String tagOption : getViewModel().getModel().getLabelClassification().getTagOptions()) {
                                tagOptions.add(new FlexBoxItem(tagOption));
                            }
                        }
                    }
                    if (task.getVisibleLevel() != null && task.getVisibleLevel() != TaskVisibleLevel.UNKNOWN.getIndex()) {
                        getBinding().radioGroup.check(getBinding().radioGroup.getChildAt(task.getVisibleLevel() - 1).getId());
                    }
                    if (task.getGroupSize() != null && task.getGroupSize() != 0) {
                        getModel().setGroupSize(String.valueOf(task.getGroupSize()));
                    }
                    if (task.getUnitPoint() != null && task.getUnitPoint() != 0) {
                        getModel().setUnitPoint(String.valueOf(task.getUnitPoint()));
                    }
                    if (task.getLocalDataFiles() != null && !task.getLocalDataFiles().isEmpty()) {
                        if (getModel().getDataFiles() == null)
                            getModel().setDataFiles(new ArrayList<>());
                        for (Long dataFileIndex : task.getLocalDataFiles()) {
                            ThreadUtil.runInBackground(() -> {
                                DataFile dataFile = DataFile.fromLocalInBackground(dataFileIndex);
                                getModel().getDataFiles().add(dataFile);
                            }, ThreadUtil.BackgroundStrategy.CACHED);
                        }
                    }
                    if (task.getUploadedDataFiles() != null && !task.getUploadedDataFiles().isEmpty()) {
                        if (getModel().getDataFiles() == null)
                            getModel().setDataFiles(new ArrayList<>());
                        //TODO 网络查询数据文件信息
                    }
                    runOnUiThread(() -> {
                        getViewModel().getModel().getTagsAdapter().refreshItems(tags);
                        if (task.getTaggingSceneType() == TaskTaggingSceneType.LABEL_CLASSIFICATION.getIndex()) {
                            getViewModel().getModel().getTagOptionsAdapter().refreshItems(tagOptions);
                        }
                    });
                }

                @Override
                public void onError(Object errorMessage) {
                    ToastUtil.error("加载失败");
                }
            }, taskIndex);
        }else {
            getModel().getTask().setTaggingSceneType(TaskTaggingSceneType.LABEL_CLASSIFICATION.getIndex());
            getModel().getTask().setStartDelay(0L);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        getBinding().taskEditTagSceneType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getViewModel().getModel().getTask().setTaggingSceneType(position + 1);
                getViewModel().getModel().setHasEdited(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getBinding().radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            TaskVisibleLevel level = TaskVisibleLevel.UNKNOWN;
            if (checkedId == R.id.task_edit_open_level_1) level = TaskVisibleLevel.OPEN;
            if (checkedId == R.id.task_edit_open_level_2) level = TaskVisibleLevel.PARTIALLY;
            if (checkedId == R.id.task_edit_open_level_3) level = TaskVisibleLevel.INVISIBLE;
            getViewModel().getModel().getTask().setVisibleLevel(level.getIndex());
            getViewModel().getModel().setHasEdited(true);
        });
        getBinding().multiSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            getViewModel().getModel().getLabelClassification().setMultiSelect(isChecked);
            getViewModel().getModel().setHasEdited(true);
        });
        getBinding().customOption.setOnCheckedChangeListener((buttonView, isChecked) -> {
            getViewModel().getModel().getLabelClassification().setCustomOption(isChecked);
            getViewModel().getModel().setHasEdited(true);
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getViewModel().getModel().setHasEdited(true);
            }
        };
        getBinding().taskEditTitle.addTextChangedListener(textWatcher);
        getBinding().taskEditDescription.addTextChangedListener(textWatcher);
        getBinding().taskEditGroupSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getModel().setHasEdited(true);
                if (!s.toString().isEmpty()) {
                    try {
                        int groupSize = Integer.parseInt(s.toString());
                        getModel().getTask().setGroupSize(groupSize);
                        int totalData = getModel().getTotalData();
                        if (totalData != 0) {
                            if (totalData < groupSize) {
                                getModel().getTask().setTotalQuota(0);
                                getBinding().taskEditQuota.setText("分组规模不能大于数据量！");
                            } else {
                                getModel().getTask().setTotalQuota((int) Math.floor((double) totalData / groupSize));
                            }
                        } else {
                            getModel().getTask().setTotalQuota(0);
                            getBinding().taskEditQuota.setText("待计算");
                        }
                    } catch (NumberFormatException e) {
                        getModel().getTask().setGroupSize(0);
                        getBinding().taskEditQuota.setText("分组规模过大！");
                    }
                }
            }
        });
        getBinding().taskEditUnitPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getModel().setHasEdited(true);
                if (!s.toString().isEmpty()) {
                    try {
                        int unitPoint = Integer.parseInt(s.toString());
                        if (unitPoint > 1000) {
                            getModel().getTask().setUnitPoint(0);
                            getBinding().taskEditTotalPoint.setText("单条数据积分过大！");
                        } else if (unitPoint >= 0) {
                            getModel().getTask().setUnitPoint(unitPoint);
                            int totalData = getModel().getTotalData();
                            if (totalData != 0) {
                                getBinding().taskEditTotalPoint.setText(String.valueOf(unitPoint * totalData));
                            } else {
                                getBinding().taskEditTotalPoint.setText("待计算");
                            }
                        }
                    } catch (NumberFormatException e) {
                        getModel().getTask().setGroupSize(0);
                        getBinding().taskEditQuota.setText("单条数据积分过大！");
                    }
                }
            }
        });
    }

    @Override
    protected void handleRes(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Map<String, Double> uploaded = JsonUtil.fromJson(data.getStringExtra("uploaded_datafile_id"), Map.class);
            Map<String, Double> local = JsonUtil.fromJson(data.getStringExtra("local_datafile_index"), Map.class);
            if (uploaded != null && !uploaded.isEmpty()) {
                for (Map.Entry<String, Double> entry : uploaded.entrySet()) {
                    if (getModel().getTask().getUploadedDataFiles() == null)
                        getModel().getTask().setUploadedDataFiles(new ArrayList<>());
                    getModel().getTask().getUploadedDataFiles().add(Long.parseLong(entry.getKey()));
                    getModel().addToTotalData(entry.getValue().intValue());
                    getModel().addDataFile(new DataFile());
                }
                edit();
            } else if (local != null && !local.isEmpty()) {
                for (Map.Entry<String, Double> entry : local.entrySet()) {
                    if (getModel().getTask().getLocalDataFiles() == null)
                        getModel().getTask().setLocalDataFiles(new ArrayList<>());
                    getModel().getTask().getLocalDataFiles().add(Long.parseLong(entry.getKey()));
                    getModel().addToTotalData(entry.getValue().intValue());
                    getModel().addDataFile(new DataFile());
                }
                edit();
            }

            if (null != data.getClipData()) {
                List<String> paths = new ArrayList<>();
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    if (uri == null) {
                        ToastUtil.normal("文件获取失败,请重试");
                        return;
                    }
                    String path;
                    try {
                        path = FileUtil.getFileAbsolutePath(this, uri);
                        paths.add(path);
                    } catch (Exception e) {
                        ToastUtil.normal("文件路径解析失败");
                        return;
                    }
                }
                getModel().getDialog().addPaths(paths);
                paths.clear();
            } else {
                Uri uri = data.getData();
                if (uri == null) {
                    ToastUtil.normal("文件获取失败,请重试");
                    return;
                }
                List<String> paths = new ArrayList<>();
                String path;
                try {
                    path = FileUtil.getFileAbsolutePath(this, uri);
                    paths.add(path);
                } catch (Exception e) {
                    ToastUtil.normal("文件路径解析失败");
                    return;
                }
                getModel().getDialog().addPaths(paths);
                paths.clear();
            }
        }
    }

    /**
     * 监控的组件类型列表，如果这些组件拥有焦点且用户产生了不满足对应监控策略的行为，则清除其焦点并隐藏键盘
     *
     * @return
     */
    @Override
    protected MonitorClass[] clearFocusOfClasses() {
        return new MonitorClass[]{new MonitorClass(MaterialEditText.class, MonitorStrategy.ROW)};
    }

    @Override
    protected void initActivity() {
        super.initActivity();
    }

    private void edit() {
        getModel().setHasEdited(true);
    }
}