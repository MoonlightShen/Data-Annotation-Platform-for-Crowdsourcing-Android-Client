package com.echo.dapc.mvvm.view.task;

import com.echo.dapc.R;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.bean.logic.Task;
import com.echo.dapc.bean.logic.taggingscene.LabelClassification;
import com.echo.dapc.bean.ui.FlexBoxItem;
import com.echo.dapc.databinding.ActivityTaskCommonInfoBinding;
import com.echo.dapc.interfaces.callback.common.CommonEntityCallback;
import com.echo.dapc.mvvm.model.task.TaskCommonInfoModel;
import com.echo.dapc.mvvm.viewmodel.taskinfo.TaskCommonInfoViewModel;
import com.echo.dapc.util.business.TaskUtil;
import com.echo.dapc.util.system.JsonUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class TaskCommonInfoActivity extends BaseDataBindingActivity<TaskCommonInfoViewModel, TaskCommonInfoModel, ActivityTaskCommonInfoBinding> {

    @Override
    protected void initActivity() {
        super.initActivity();
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
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_task_common_info;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {

    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {
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
                    ToastUtil.normal("服务器未响应");
                }

                @Override
                public void unknownError(String errorCode) {
                    ToastUtil.info("未知错误");
                }
            }, taskId);
        }else {
            //TODO 异常情况
        }
    }
}