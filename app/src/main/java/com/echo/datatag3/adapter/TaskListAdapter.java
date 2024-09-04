package com.echo.datatag3.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.viewholder.task.TaskAbnormalViewHolder;
import com.echo.datatag3.adapter.viewholder.task.TaskAuditingViewHolder;
import com.echo.datatag3.adapter.viewholder.task.TaskCompletedViewHolder;
import com.echo.datatag3.adapter.viewholder.task.TaskEditingViewHolder;
import com.echo.datatag3.adapter.viewholder.task.TaskPauseViewHolder;
import com.echo.datatag3.adapter.viewholder.task.TaskRunningViewHolder;
import com.echo.datatag3.adapter.viewholder.task.TaskViewHolder;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.bean.enumeration.Gender;
import com.echo.datatag3.bean.enumeration.TaskState;
import com.echo.datatag3.bean.logic.Task;
import com.echo.datatag3.mvvm.view.LoadingErrorPage;
import com.echo.datatag3.mvvm.view.task.TaskCommonInfoPage;
import com.echo.datatag3.mvvm.view.task.TaskCompletedInfoPage;
import com.echo.datatag3.mvvm.view.task.TaskDetailsPage;
import com.echo.datatag3.mvvm.view.task.TaskEditingInfoPage;
import com.echo.datatag3.util.business.AvatarUtil;
import com.echo.datatag3.util.business.InfoUtil;
import com.echo.datatag3.util.system.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class TaskListAdapter extends BaseItemListAdapter<Task, TaskViewHolder> {

    private final Context mContext;
    private final ActivityResultLauncher<Intent> mLauncher;
    private final ClickCallback mClickCallback;

    public TaskListAdapter(@NonNull Context context, @NonNull ActivityResultLauncher<Intent> launcher, @NonNull ClickCallback clickCallback) {
        this(context, launcher, clickCallback, new ArrayList<>());
    }

    public TaskListAdapter(@NonNull Context context, @NonNull ActivityResultLauncher<Intent> launcher, @NonNull ClickCallback clickCallback, @NonNull List<Task> data) {
        super(data);
        mContext = context;
        mLauncher = launcher;
        mClickCallback = clickCallback;
        init();
    }

    private void init() {
        setClickListener((itemview, position) -> {
            mClickCallback.clickPosition(position);
            Class<?> targetClass;
            Task task = getItem(position);
            if (InfoUtil.getUserId().longValue() == task.getOwnerId()) {
                switch (task.getState()) {
                    case EDITING -> targetClass = TaskEditingInfoPage.class;
                    case AUDITING, ABNORMAL, RUNNING, PAUSE ->
                            targetClass = TaskCommonInfoPage.class;
                    case COMPLETED -> targetClass = TaskCompletedInfoPage.class;
                    default -> targetClass = LoadingErrorPage.class;
                }
            } else {
                targetClass = TaskDetailsPage.class;
            }
            Intent intent = new Intent(mContext, targetClass);
            if (task.getTaskId() != null) intent.putExtra("task_id", task.getTaskId());
            if (task.getTaskIndex() != null) intent.putExtra("task_index", task.getTaskIndex());
            mLauncher.launch(intent);
        });
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getState().getIndex();
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        TaskState state = TaskState.getByIndex(viewType);
        if (state != null) {
            return switch (state) {
                case EDITING -> R.layout.task_editing;
                case AUDITING -> R.layout.task_auditing;
                case ABNORMAL -> R.layout.task_abnormal;
                case RUNNING -> R.layout.task_running;
                case PAUSE -> R.layout.task_pause;
                case COMPLETED -> R.layout.task_completed;
                default -> 0;
            };
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    protected TaskViewHolder createViewHolder(@NonNull View view, int viewType) {
        TaskState state = TaskState.getByIndex(viewType);
        if (state!=null){
            return switch (state) {
                case EDITING -> new TaskEditingViewHolder(view);
                case AUDITING -> new TaskAuditingViewHolder(view);
                case ABNORMAL -> new TaskAbnormalViewHolder(view);
                case RUNNING -> new TaskRunningViewHolder(view);
                case PAUSE -> new TaskPauseViewHolder(view);
                case COMPLETED -> new TaskCompletedViewHolder(view);
                default -> new TaskViewHolder(view);
            };
        }else {
            return new TaskViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bindViewHolder(@NonNull Task task, @NonNull TaskViewHolder viewHolder) {
        if (task.getTitle() != null) {
            viewHolder.taskTitle.setText(task.getTitle());
        }
        switch (task.getState()) {
            case EDITING -> {
                LinearLayout tagsLayout = ((TaskEditingViewHolder) viewHolder).tags;
                tagsLayout.removeAllViews();
                if (task.getTags() == null || task.getTags().isEmpty()) {
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                            0,
                            0
                    );
                    tagsLayout.setLayoutParams(params);
                } else {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 15, 0);
                    for (String tag : task.getTags()) {
                        View view = LayoutInflater.from(tagsLayout.getContext()).inflate(R.layout.tag_item, null);
                        view.setLayoutParams(params);
                        ((TextView) (view.findViewById(R.id.content))).setText(tag);
                        tagsLayout.addView(view);
                    }
                }
                ((TaskEditingViewHolder) viewHolder).unitPoint.setText(task.getUnitPoint() == null || task.getUnitPoint() == 0 ? "暂未设置单位积分" : task.getUnitPoint() + " 积分/条数据");
                ((TaskEditingViewHolder) viewHolder).groupSize.setText(task.getGroupSize() == null || task.getGroupSize() == 0 ? "暂未设置分组规模" : task.getGroupSize() + " 数据/组任务");
                ((TaskEditingViewHolder) viewHolder).endTime.setText(task.getEndTime() == null ? "暂未设置截止时间" : TimeUtil.format(task.getEndTime(), TimeUtil.zh_yyyyMMddHHmm));
                ((TaskEditingViewHolder) viewHolder).taggingScene.setText(task.getTaggingSceneType() == null ? "未指定标注场景" : task.getTaggingSceneType().getTag());
//                ((TaskEditingViewHolder) viewHolder).visibleLevel.setText(task.getVisibleLevel()==null?"未设置可见等级": task.getVisibleLevel().getTag());
            }
            case AUDITING -> {
                if (task.getUploadTime()!=null){
                    ((TaskAuditingViewHolder) viewHolder).uploadedTime.setText(TimeUtil.format(task.getUploadTime(), TimeUtil.zh_yyyyMMddHHmm));
                }
            }
            case RUNNING -> {
                if (task.getStartTime() != null && task.getEndTime() != null) {
                    ((TaskRunningViewHolder) viewHolder).remainingTime.setText(TimeUtil.format(task.getEndTime() - TimeUtil.getCurrentTime(), TimeUtil.zh_ddHHmmSS));
                    float progress = (TimeUtil.getCurrentTime() - task.getStartTime())*100f / (task.getEndTime() - task.getStartTime());
                    ((TaskRunningViewHolder) viewHolder).remainingTimePercent.setText(String.format(Locale.CHINA, "%.0f%%", progress));
                    ((TaskRunningViewHolder) viewHolder).remainingTimeProgressBar.setProgress(progress, 1000);
                }
                if (task.getTotalQuota() != null && task.getRemainingQuota() != null) {
                    ((TaskRunningViewHolder) viewHolder).remainingQuota.setText(String.format(Locale.CHINA, "%d (%.0f%%)", task.getRemainingQuota(), task.getRemainingQuota()*100f / task.getTotalQuota()));
                    ((TaskRunningViewHolder) viewHolder).remainingQuotaProgressBar.setProgress((task.getTotalQuota() - task.getRemainingQuota())*100f / task.getTotalQuota(), 1000);
                }
                if (task.getLikeNum() != null) {
                    ((TaskRunningViewHolder) viewHolder).likeNum.setText(String.valueOf(task.getLikeNum()));
                }
                if (task.getStarNum() != null) {
                    ((TaskRunningViewHolder) viewHolder).starNum.setText(String.valueOf(task.getStarNum()));
                }
                if (task.getReadNum() != null) {
                    ((TaskRunningViewHolder) viewHolder).readNum.setText(String.valueOf(task.getReadNum()));
                }
                if (task.getApplyUsers() != null) {
                    if (task.getApplyUsers().size() > 99) {
                        ((TaskRunningViewHolder) viewHolder).applyUsersNum.setText("99+人已参加");
                    } else if (task.getApplyUsers().size() > 0) {
                        ((TaskRunningViewHolder) viewHolder).applyUsersNum.setText(task.getApplyUsers().size() + "人已参加");
                    } else {
                        ((TaskRunningViewHolder) viewHolder).applyUsersNum.setText("暂无人参与");
                    }
                    if (task.getApplyUsers().size()>0){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(0), ((TaskRunningViewHolder) viewHolder).applyUser1, false);
                    }
                    if (task.getApplyUsers().size()>1){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(1), ((TaskRunningViewHolder) viewHolder).applyUser2, false);
                    }
                    if (task.getApplyUsers().size()>2){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(2), ((TaskRunningViewHolder) viewHolder).applyUser3, false);
                    }
                    if (task.getApplyUsers().size()>3){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(3), ((TaskRunningViewHolder) viewHolder).applyUser4, false);
                    }
                    if (task.getApplyUsers().size()>4){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(4), ((TaskRunningViewHolder) viewHolder).applyUser5, false);
                    }
                }
            }
            case PAUSE -> {
                if (task.getStartTime() != null && task.getEndTime() != null) {
                    ((TaskRunningViewHolder) viewHolder).remainingTime.setText(TimeUtil.format(task.getEndTime() - TimeUtil.getCurrentTime(), TimeUtil.zh_ddHHmmSS));
                    float progress = (TimeUtil.getCurrentTime() - task.getStartTime())*100f / (task.getEndTime() - task.getStartTime());
                    ((TaskRunningViewHolder) viewHolder).remainingTimePercent.setText(String.format(Locale.CHINA, "%.0f%%", progress));
                    ((TaskRunningViewHolder) viewHolder).remainingTimeProgressBar.setProgress(progress, 1000);
                }
                if (task.getTotalQuota() != null && task.getRemainingQuota() != null) {
                    ((TaskRunningViewHolder) viewHolder).remainingQuota.setText(String.format(Locale.CHINA, "%d (%.0f%%)", task.getRemainingQuota(), task.getRemainingQuota()*100f / task.getTotalQuota()));
                    ((TaskRunningViewHolder) viewHolder).remainingQuotaProgressBar.setProgress((task.getTotalQuota() - task.getRemainingQuota())*100f / task.getTotalQuota(), 1000);
                }
                if (task.getLikeNum() != null) {
                    ((TaskRunningViewHolder) viewHolder).likeNum.setText(String.valueOf(task.getLikeNum()));
                }
                if (task.getStarNum() != null) {
                    ((TaskRunningViewHolder) viewHolder).starNum.setText(String.valueOf(task.getStarNum()));
                }
                if (task.getReadNum() != null) {
                    ((TaskRunningViewHolder) viewHolder).readNum.setText(String.valueOf(task.getReadNum()));
                }
                if (task.getApplyUsers() != null) {
                    if (task.getApplyUsers().size() > 99) {
                        ((TaskRunningViewHolder) viewHolder).applyUsersNum.setText("99+人已参加");
                    } else if (task.getApplyUsers().size() > 0) {
                        ((TaskRunningViewHolder) viewHolder).applyUsersNum.setText(task.getApplyUsers().size() + "人已参加");
                    } else {
                        ((TaskRunningViewHolder) viewHolder).applyUsersNum.setText("暂无人参与");
                    }
                    if (task.getApplyUsers().size()>0){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(0), ((TaskRunningViewHolder) viewHolder).applyUser1, false);
                    }
                    if (task.getApplyUsers().size()>1){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(1), ((TaskRunningViewHolder) viewHolder).applyUser2, false);
                    }
                    if (task.getApplyUsers().size()>2){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(2), ((TaskRunningViewHolder) viewHolder).applyUser3, false);
                    }
                    if (task.getApplyUsers().size()>3){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(3), ((TaskRunningViewHolder) viewHolder).applyUser4, false);
                    }
                    if (task.getApplyUsers().size()>4){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(4), ((TaskRunningViewHolder) viewHolder).applyUser5, false);
                    }
                }
            }
            case COMPLETED -> {
//                if (task.get)
                if (task.getLikeNum() != null) {
                    ((TaskCompletedViewHolder) viewHolder).likeNum.setText(String.valueOf(task.getLikeNum()));
                }
                if (task.getStarNum() != null) {
                    ((TaskCompletedViewHolder) viewHolder).starNum.setText(String.valueOf(task.getStarNum()));
                }
                if (task.getReadNum() != null) {
                    ((TaskCompletedViewHolder) viewHolder).readNum.setText(String.valueOf(task.getReadNum()));
                }
                if (task.getApplyUsers() != null) {
                    if (task.getApplyUsers().size() > 99) {
                        ((TaskCompletedViewHolder) viewHolder).applyUsersNum.setText("99+人已参加");
                    } else if (task.getApplyUsers().size() > 0) {
                        ((TaskCompletedViewHolder) viewHolder).applyUsersNum.setText(task.getApplyUsers().size() + "人已参加");
                    } else {
                        ((TaskCompletedViewHolder) viewHolder).applyUsersNum.setText("暂无人参与");
                    }
                    if (task.getApplyUsers().size()>0){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(0), ((TaskCompletedViewHolder) viewHolder).applyUser1, false);
                    }
                    if (task.getApplyUsers().size()>1){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(1), ((TaskCompletedViewHolder) viewHolder).applyUser2, false);
                    }
                    if (task.getApplyUsers().size()>2){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(2), ((TaskCompletedViewHolder) viewHolder).applyUser3, false);
                    }
                    if (task.getApplyUsers().size()>3){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(3), ((TaskCompletedViewHolder) viewHolder).applyUser4, false);
                    }
                    if (task.getApplyUsers().size()>4){
                        AvatarUtil.loadUserAvatar(Gender.UNKNOWN, task.getApplyUsers().get(4), ((TaskCompletedViewHolder) viewHolder).applyUser5, false);
                    }
                }
            }
        }
    }

}
