package com.echo.datatag3.mvvm.view.task;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.FlexboxLayoutAdapter;
import com.echo.datatag3.adapter.SimpleTagListAdapter;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.bean.entity.LocalTask;
import com.echo.datatag3.bean.enumeration.TaskTaggingSceneType;
import com.echo.datatag3.bean.enumeration.TaskVisibleLevel;
import com.echo.datatag3.bean.logic.DataFile;
import com.echo.datatag3.bean.logic.taggingscene.LabelClassification;
import com.echo.datatag3.bean.ui.FlexBoxItem;
import com.echo.datatag3.databinding.PageTaskEditingInfoBinding;
import com.echo.datatag3.interfaces.callback.task.QueryLocalTaskCallback;
import com.echo.datatag3.mvvm.model.task.TaskEditingInfoModel;
import com.echo.datatag3.mvvm.viewmodel.taskinfo.TaskEditingInfoViewModel;
import com.echo.datatag3.util.business.TaskUtil;
import com.echo.datatag3.util.kt.DialogUtil;
import com.echo.datatag3.util.system.FileUtil;
import com.echo.datatag3.util.system.JsonUtil;
import com.echo.datatag3.util.system.ThreadUtil;
import com.echo.datatag3.util.system.ToastUtil;
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

public class TaskEditingInfoPage extends BaseCustomActivity<TaskEditingInfoViewModel, TaskEditingInfoModel, PageTaskEditingInfoBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.page_task_editing_info;
    }

    @Override
    protected void init() {
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
        dispatchFromClasses(new Class[]{MaterialEditText.class});
    }

    /**
     * 创建子线程处理耗时操作，重写此方法时必须重写loadSuccess()方法
     *
     * @return
     */
    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> list = new ArrayList<>();
        list.add(() -> {
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
        });
//        list.add(()->{
//            boolean editable = getBooleanExtra("editable", true);
//            if (!editable){
//                getBinding().taskEditTitle.setEnabled(false);
//                getBinding().taskEditDescription.setEnabled(false);
//                getBinding().taskEditEndTimeBtn.setClickable(false);
//                getBinding().taskEditAddTag.setClickable(false);
//                getModel().getTagsAdapter().setClickable(false);
//                getModel().getTagsAdapter().setLongClickable(false);
//
//                getBinding().taskEditChooseDatafile.setClickable(false);
//                getBinding().taskEditGroupSize.setEnabled(false);
//                getBinding().radioGroup.setEnabled(false);
//                getBinding().taskEditInviteUser.setClickable(false);
//                getBinding().taskEditUnitPoint.setEnabled(false);
//                getBinding().taskEditTagSceneType.setEnabled(false);
//                getBinding().taskEditLabelClassificationAddOption.setClickable(false);
//                getModel().getTagOptionsAdapter().setClickable(false);
//                getModel().getTagOptionsAdapter().setLongClickable(false);
//                getBinding().multiSelect.setEnabled(false);
//                getBinding().customOption.setEnabled(false);
//            }
//        });
        return list;
    }

    /**
     * 所有子线程成功处理完成后，会调用此方法
     */
    @Override
    protected void loadSuccess() {
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
                        ToastUtil.toast("文件获取失败,请重试");
                        return;
                    }
                    String path;
                    try {
                        path = FileUtil.getFileAbsolutePath(this, uri);
                        paths.add(path);
                    } catch (Exception e) {
                        ToastUtil.toast("文件路径解析失败");
                        return;
                    }
                }
                getModel().getDialog().addPaths(paths);
                paths.clear();
            } else {
                Uri uri = data.getData();
                if (uri == null) {
                    ToastUtil.toast("文件获取失败,请重试");
                    return;
                }
                List<String> paths = new ArrayList<>();
                String path;
                try {
                    path = FileUtil.getFileAbsolutePath(this, uri);
                    paths.add(path);
                } catch (Exception e) {
                    ToastUtil.toast("文件路径解析失败");
                    return;
                }
                getModel().getDialog().addPaths(paths);
                paths.clear();
            }
        }
    }

    private void edit() {
        getModel().setHasEdited(true);
    }
}