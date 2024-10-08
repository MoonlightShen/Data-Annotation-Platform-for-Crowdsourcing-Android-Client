package com.echo.dapc.mvvm.viewmodel.taskinfo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.bean.base.FileInfo;
import com.echo.dapc.bean.entity.LocalTask;
import com.echo.dapc.bean.enumeration.DataType;
import com.echo.dapc.bean.enumeration.FileType;
import com.echo.dapc.bean.enumeration.TaskTaggingSceneType;
import com.echo.dapc.bean.logic.DataFile;
import com.echo.dapc.bean.logic.Task;
import com.echo.dapc.bean.ui.FlexBoxItem;
import com.echo.dapc.interfaces.callback.common.SQLiteDBDeleteCallback;
import com.echo.dapc.interfaces.callback.task.SaveLocalTaskCallback;
import com.echo.dapc.interfaces.callback.task.UploadTaskCallback;
import com.echo.dapc.mvvm.model.task.TaskEditingInfoModel;
import com.echo.dapc.mvvm.view.task.TaskEditingInfoActivity;
import com.echo.dapc.mvvm.view.list.ChooseDataFilePage;
import com.echo.dapc.util.GlobalConstant;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.business.TaskUtil;
import com.echo.dapc.util.kt.DialogUtil;
import com.echo.dapc.util.system.CacheUtil;
import com.echo.dapc.util.system.FileUtil;
import com.echo.dapc.util.system.JsonUtil;
import com.echo.dapc.util.system.ThreadUtil;
import com.echo.dapc.util.system.TimeUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.echo.dapc.widget.PopupMenu.MenuItem;
import com.echo.dapc.widget.PopupMenu.TopRightMenu;
import com.echo.dapc.widget.bottomdialog.ImportFilesDialog;

import java.util.ArrayList;
import java.util.List;

public class TaskEditingInfoViewModel extends BaseViewModel<TaskEditingInfoModel> {
    TopRightMenu topRightMenu;

    public TaskEditingInfoViewModel(@NonNull Application application) {
        super(application);
    }

    public void openMenu(View view) {
        if (topRightMenu == null) {
            topRightMenu = new TopRightMenu((TaskEditingInfoActivity) getModel().getContext());
            List<MenuItem> menuItems = new ArrayList<>();
            menuItems.add(new MenuItem(R.drawable.common_left_gray, "选择模板"));
            menuItems.add(new MenuItem(R.drawable.common_left_gray, "删除任务"));
            menuItems.add(new MenuItem(R.drawable.common_left_gray, "提交送审"));
            topRightMenu
                    .addMenuList(menuItems)
                    .setOnMenuItemClickListener(position -> {
                        switch (position) {
                            case 0 -> chooseTemplate();
                            case 1 -> deleteTask();
                            case 2 -> uploadTask();
                        }
                    })
                    .showAsDropDown(view, -100, 0);    //带偏移量
        } else {
            topRightMenu.showAsDropDown(view, -100, 0);
        }
    }

    public void chooseStartDelay(){

    }

    public void chooseDuration() {

    }

    @SuppressLint("CheckResult")
    public void addTag() {
        DialogUtil.addTag(getModel().getContext(), "添加一个新的标签", (materialDialog, charSequence) -> {
            String content = charSequence.toString().strip();
            if (!content.isEmpty()){
                getModel().getTagsAdapter().addItem(new FlexBoxItem(content));
                getModel().setHasEdited(true);
            }
            return null;
        });
    }

    @SuppressLint("CheckResult")
    public void addTagOption() {
        DialogUtil.addTag(getModel().getContext(), "添加一个新的选项", (materialDialog, charSequence) -> {
            getModel().getTagOptionsAdapter().addItem(new FlexBoxItem(charSequence.toString()));
            getModel().setHasEdited(true);
            return null;
        });
    }

    public void saveTask(){
        LocalTask task = getModel().getTask();

        List<String> tags = new ArrayList<>();
        for (FlexBoxItem item:getModel().getTagsAdapter().getData()){
            tags.add(item.getContent());
        }
        task.setTags(tags);

        if (task.getTaggingSceneType()==TaskTaggingSceneType.LABEL_CLASSIFICATION.getIndex()){
            List<String> options = new ArrayList<>();
            for (FlexBoxItem item:getModel().getTagOptionsAdapter().getData()){
                options.add(item.getContent());
            }
            getModel().getLabelClassification().setTagOptions(options);
            task.setTaggingSceneSettings(JsonUtil.toJson(getModel().getLabelClassification()));
        }

        TaskUtil.saveLocalTask(new SaveLocalTaskCallback() {
            @Override
            public void onSuccess() {
                ToastUtil.success("保存成功");
                getModel().setHasEdited(false);
            }

            @Override
            public void onError(String errorMessage) {
                ToastUtil.error("保存失败");
            }
        }, task);
    }
    public void chooseDatafiles() {
        DialogUtil.chooseDataFileImportMethod(getModel().getContext(), (materialDialog, integer, charSequence) -> {
            if (integer==0){
                createIntent(ChooseDataFilePage.class);
            } else if (integer==1) {
                if (getModel().getDialog()==null){
                    getModel().setDialog(new ImportFilesDialog(getModel().getFragmentManager(), new ImportFilesDialog.Callback() {
                        @Override
                        public void onConfirm(List<String> paths) {
                            for (String path:paths){
                                DataFile dataFile = new DataFile(null, null, InfoUtil.getUserId(),"\n", null, TimeUtil.getCurrentTime(),
                                        getModel().getDialog().isLocalCaching(), null, null, null, null,getModel().getDialog().getFileTag(), new FileInfo(path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")),
                                        path,
                                        FileType.getBySuffix(path.substring(path.lastIndexOf(".") + 1)),
                                        null, null,
                                        getModel().getDialog().getFileTag()));
                                CacheUtil.cachingDataFile(dataFile1 -> {
                                    getModel().addDataFile(dataFile1);
                                    getModel().addToTotalData(dataFile1.getDataNum());
                                }, dataFile);
                            }
                        }

                        @Override
                        public void onChoose(DataType dataType) {
                            FileUtil.selectFiles(getModel().getLauncher(), dataType);
                        }
                    }));
                }
                getModel().getDialog().show();
            }
            return null;
        });
    }

    public void chooseTemplate() {

    }

    public void deleteTask() {
        DialogUtil.confirmDeleteTask(getModel().getContext(), getModel().getTask().getTitle(),
                (materialDialog, charSequence) -> {
                    if (charSequence.toString().equals(getModel().getTask().getTitle())) {
                        TaskUtil.removeLocalTask(new SQLiteDBDeleteCallback() {
                            @Override
                            public void onSuccess() {
                                ToastUtil.success("删除成功");
                                getModel().setHasEdited(true);
                                postDelayed(() -> ((TaskEditingInfoActivity) getModel().getContext()).finish(), 300);
                            }

                            @Override
                            public void onSQLiteError() {
                                ToastUtil.error("删除失败");
                            }
                        }, getModel().getTask());
                    } else {
                        ToastUtil.normal("请输入正确名称");
                    }
                    return null;
                });
    }

    public void uploadTask() {
        if (getModel().getTask().getTitle()==null||getModel().getTask().getTitle().isEmpty()||getModel().getTask().getTitle().length()<5||getModel().getTask().getTitle().length()>40){
            ToastUtil.error("任务标题应为5-20字");
        }else if (getModel().getTask().getDescription()==null||getModel().getTask().getDescription().isEmpty()||getModel().getTask().getDescription().length()>400){
            ToastUtil.error("任务描述应为1-400字");
        } else if (getModel().getDataFiles()==null||getModel().getDataFiles().isEmpty()){
            ToastUtil.error("请选择数据文件");
        } else if (getModel().getTask().getDuration()==null){
            ToastUtil.error("未设置任务持续时间");
        } else if (getModel().getTask().getGroupSize()==null||getModel().getTask().getGroupSize()==0){
            ToastUtil.error("请正确输入分组规模");
        } else if (getModel().getTask().getVisibleLevel()==null||getModel().getTask().getVisibleLevel()==0){
            ToastUtil.error("请选择任务可见范围");
        } else if (getModel().getTask().getUnitPoint()==null||getModel().getTask().getUnitPoint()==0){
            ToastUtil.error("请正确输入单条数据积分");
        } else if (getModel().getTask().getTaggingSceneType()==null||getModel().getTask().getTaggingSceneType()==0){
            ToastUtil.error("请选择评测场景");
        } else if (getModel().getTask().getTaggingSceneType()!=null&&getModel().getTask().getTaggingSceneType()==1&&getModel().getTagOptionsAdapter().getItemCount()<=1){
            ToastUtil.error("请至少给出两个标签选项");
        }else {
            ThreadUtil.runInBackground(()->{
                Task task = Task.fromLocalInBackground(getModel().getTask().getTaskIndex());
                if (task!=null){
                    TaskUtil.uploadTask(new UploadTaskCallback() {
                        @Override
                        public void onSuccess(Long taskId) {
                            TaskUtil.removeLocalTask(new SQLiteDBDeleteCallback() {
                                @Override
                                public void onSuccess() {
                                    postDelayed(() -> {
                                        ToastUtil.success("提交成功");
                                        ((TaskEditingInfoActivity) getModel().getContext()).setResult(GlobalConstant.LIST_AUTO_REFRESH, new Intent());
                                        ((TaskEditingInfoActivity) getModel().getContext()).finish();
                                    }, 300);
                                }

                                @Override
                                public void onSQLiteError() {
                                    ToastUtil.normal("数据库异常");
                                }
                            }, getModel().getTask());
                        }

                        @Override
                        public void unknownError(String errorCode) {
                            ToastUtil.info("未知错误" + errorCode);
                        }

                        @Override
                        public void onIOException() {
                            ToastUtil.normal("服务器无响应");
                        }
                    }, task);
                }else {
                    ToastUtil.error("提交失败");
                    //TODO log
                }
            }, ThreadUtil.BackgroundStrategy.CACHED);
        }
    }


}
