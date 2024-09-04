package com.echo.datatag3.mvvm.view.task;

import com.echo.datatag3.R;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.bean.logic.Task;
import com.echo.datatag3.bean.logic.taggingscene.LabelClassification;
import com.echo.datatag3.bean.ui.FlexBoxItem;
import com.echo.datatag3.databinding.PageTaskCommonInfoBinding;
import com.echo.datatag3.interfaces.callback.common.CommonEntityCallback;
import com.echo.datatag3.mvvm.model.task.TaskCommonInfoModel;
import com.echo.datatag3.mvvm.viewmodel.taskinfo.TaskCommonInfoViewModel;
import com.echo.datatag3.util.business.TaskUtil;
import com.echo.datatag3.util.system.JsonUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class TaskCommonInfoPage extends BaseCustomActivity<TaskCommonInfoViewModel, TaskCommonInfoModel, PageTaskCommonInfoBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.page_task_common_info;
    }

    @Override
    protected void init() {
        getBinding().taskInfoTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }
            @Override
            public void onRightClick(TitleBar titleBar) {
                getViewModel().openMenu(getBinding().taskInfoTitleBar.getRightView());
            }
        });
    }

    /**
     * 创建子线程处理耗时操作，重写此方法时必须重写loadSuccess()方法
     *
     * @return
     */
    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(()->{
            long taskId = getLongExtra("task_id", 0);
            if (taskId!=0){
                getModel().setTaskId(taskId);
                TaskUtil.queryTask(new CommonEntityCallback<Task>() {
                    @Override
                    public void onSuccess(Task task) {
                        getModel().setState(task.getState());
                        getModel().setTitle(task.getTitle());
                        getModel().setDescription(task.getDescription());
                        getModel().setEndTime(task.getEndTime());
                        List<FlexBoxItem> tags = new ArrayList<>();
                        for (String s:task.getTags()){
                            tags.add(new FlexBoxItem(s));
                        }
                        getModel().getTagsAdapter().refreshItems(tags);
                        //TODO 数据总量
//                        getModel().setTotalData();
                        getModel().setGroupSize(task.getGroupSize());
                        getModel().setVisibleLevel(task.getVisibleLevel());
                        getModel().setUnitPoint(task.getUnitPoint());
                        getModel().setTaggingSceneType(task.getTaggingSceneType());
                        if (task.getTaggingSceneType()!=null){
                            switch (task.getTaggingSceneType()){
                                case LABEL_CLASSIFICATION -> {
                                    LabelClassification labelClassification = JsonUtil.fromJson(task.getTaggingSceneSettings(), LabelClassification.class);
                                    getModel().setLabelClassification(labelClassification);
                                    List<FlexBoxItem> options = new ArrayList<>();
                                    for (String s:labelClassification.getTagOptions()){
                                        options.add(new FlexBoxItem(s));
                                    }
                                    getModel().getTaggingOptionsAdapter().refreshItems(options);
                                }
                            }
                        }
                    }

                    @Override
                    public void onSQLiteDBError() {
                        ToastUtil.error("数据库异常");
                    }

                    @Override
                    public void onIOException() {
                        ToastUtil.toast("服务器未响应");
                    }

                    @Override
                    public void unknownError(String errorCode) {
                        ToastUtil.info("未知错误");
                    }
                }, taskId);
            }else {
                //TODO 异常情况
            }
        });
        return runnableList;
    }

    /**
     * 所有子线程成功处理完成后，会调用此方法
     */
    @Override
    protected void loadSuccess() {

    }
}