package com.echo.dapc.bean.logic;

import com.echo.dapc.bean.entity.LocalDataFile;
import com.echo.dapc.bean.entity.LocalTask;
import com.echo.dapc.bean.enumeration.TaskState;
import com.echo.dapc.bean.enumeration.TaskTaggingSceneType;
import com.echo.dapc.bean.enumeration.TaskVisibleLevel;
import com.echo.dapc.util.business.InfoUtil;
import com.echo.dapc.util.database.DBUtilShop;
import com.echo.dapc.util.network.response.task.TaskResponse;
import com.echo.dapc.util.system.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private Long taskIndex; // 本地数据库实体主键
    private Long taskId; //逻辑主键
    private Long ownerId; //任务创建者实体主键
    private List<DataFile> dataFiles; //数据文件列表
    private String title; //任务标题
    private String description; //任务描述
    private Long startTime; //任务开始的时间，时间戳格式
    private Long endTime; //任务结束的时间，时间戳格式
    private Long startDelay;
    private Long duration;
    private List<String> tags; //任务标签
    private TaskState state;
    private Integer likeNum;
    private Integer starNum;
    private Integer readNum;
    private Integer groupSize; //数据分组规模
    private TaskVisibleLevel visibleLevel; //任务可见等级
    private Integer unitPoint; //每条数据的积分
    private TaskTaggingSceneType taggingSceneType; //标注场景类型
    private String taggingSceneSettings; //标注场景配置
    private List<Long> inviteUsers; //原来的targetUsers，表示被邀请的用户
    private Integer totalQuota; //任务总指标
    private Integer remainingQuota; //剩余指标（注：仅通过了报名的用户会占用指标
    private List<Long> applyUsers; //报名了任务的用户集（注：无论该用户的报名是否通过了审核，都计算在内
    private Boolean autoReviewAnswer; //自动审查标注结果
    private Boolean autoApproveFetch; //自动批准接取任务

    private Long uploadTime;


    public Task() {
    }

    public Task(Long taskIndex,Long ownerId, List<DataFile> dataFiles, String title, String description, Long startDelay, Long duration, List<String> tags, TaskState state, Integer groupSize, TaskVisibleLevel visibleLevel, Integer unitPoint, TaskTaggingSceneType taggingSceneType, String taggingSceneSettings, List<Long> inviteUsers, Integer totalQuota) {
        this.taskIndex = taskIndex;
        this.ownerId = ownerId;
        this.dataFiles = dataFiles;
        this.title = title;
        this.description = description;
        this.startDelay = startDelay;
        this.duration = duration;
        this.tags = tags;
        this.state = state;
        this.groupSize = groupSize;
        this.visibleLevel = visibleLevel;
        this.unitPoint = unitPoint;
        this.taggingSceneType = taggingSceneType;
        this.taggingSceneSettings = taggingSceneSettings;
        this.inviteUsers = inviteUsers;
        this.totalQuota = totalQuota;
    }

    public Task(TaskResponse response) {
        taskId = response.taskId;
        ownerId = response.ownerId;
        title = response.title;
        description = response.description;
        startTime = response.startTime;
        endTime = response.endTime;
        tags = response.tags;
        state = TaskState.getByIndex(response.state);
        likeNum = response.likeNum;
        starNum = response.starNum;
        readNum = response.readNum;
        groupSize = response.groupSize;
        if (response.visibleLevel!=null)visibleLevel = TaskVisibleLevel.getByIndex(response.visibleLevel);
        unitPoint = response.unitPoint;
        if (response.taggingSceneType!=null)taggingSceneType = TaskTaggingSceneType.getByIndex(response.taggingSceneType);
        taggingSceneSettings = response.taggingSceneSettings;
        inviteUsers = response.inviteUsers;
        totalQuota = response.totalQuota;
        remainingQuota = response.remainingQuota;
        applyUsers = response.applyUsers;
        autoReviewAnswer = response.autoReviewAnswer!=null&&response.autoReviewAnswer == 1;
        autoApproveFetch = response.autoApproveFetch!=null&&response.autoApproveFetch == 1;
    }

    public List<DataFile> getDataFiles() {
        return dataFiles;
    }

    public Long getTaskIndex() {
        return taskIndex;
    }

    public void setTaskIndex(Long taskIndex) {
        this.taskIndex = taskIndex;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setDataFiles(List<DataFile> dataFiles) {
        this.dataFiles = dataFiles;
    }

    public List<Long> getInviteUsers() {
        return inviteUsers;
    }

    public String getTitle() {
        return title;
    }

    public Long getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(Long startDelay) {
        this.startDelay = startDelay;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public TaskVisibleLevel getVisibleLevel() {
        return visibleLevel;
    }

    public void setVisibleLevel(TaskVisibleLevel visibleLevel) {
        this.visibleLevel = visibleLevel;
    }

    public Integer getUnitPoint() {
        return unitPoint;
    }

    public void setUnitPoint(Integer unitPoint) {
        this.unitPoint = unitPoint;
    }

    public TaskTaggingSceneType getTaggingSceneType() {
        return taggingSceneType;
    }

    public void setTaggingSceneType(TaskTaggingSceneType taggingSceneType) {
        this.taggingSceneType = taggingSceneType;
    }

    public String getTaggingSceneSettings() {
        return taggingSceneSettings;
    }

    public void setTaggingSceneSettings(String taggingSceneSettings) {
        this.taggingSceneSettings = taggingSceneSettings;
    }

    public void setInviteUsers(List<Long> inviteUsers) {
        this.inviteUsers = inviteUsers;
    }

    public Integer getTotalQuota() {
        return totalQuota;
    }

    public void setTotalQuota(Integer totalQuota) {
        this.totalQuota = totalQuota;
    }

    public Integer getRemainingQuota() {
        return remainingQuota;
    }

    public void setRemainingQuota(Integer remainingQuota) {
        this.remainingQuota = remainingQuota;
    }

    public List<Long> getApplyUsers() {
        return applyUsers;
    }

    public void setApplyUsers(List<Long> applyUsers) {
        this.applyUsers = applyUsers;
    }

    public Boolean getAutoReviewAnswer() {
        return autoReviewAnswer;
    }

    public void setAutoReviewAnswer(Boolean autoReviewAnswer) {
        this.autoReviewAnswer = autoReviewAnswer;
    }

    public Boolean getAutoApproveFetch() {
        return autoApproveFetch;
    }

    public void setAutoApproveFetch(Boolean autoApproveFetch) {
        this.autoApproveFetch = autoApproveFetch;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public static Task fromLocal(LocalTask localTask){
        List<DataFile> dataFiles = new ArrayList<>();
        if (localTask.getLocalDataFiles()!=null){
            for (Long localDataFileIndex:localTask.getLocalDataFiles()){
                LocalDataFile localDataFile = DBUtilShop.localDataFileDBUtil.queryEntity(localDataFileIndex);
                if (localDataFile!=null){
                    dataFiles.add(new DataFile().fromLocal(localDataFile));
                }
            }
        }
        if (localTask.getUploadedDataFiles()!=null){
            for (Long uploadedDataFileId:localTask.getUploadedDataFiles()){
                //TODO 本地或网络请求已上传的数据文件的信息
            }
        }
       return new Task(localTask.getTaskIndex(), InfoUtil.getUserId(), dataFiles, localTask.getTitle(),
                localTask.getDescription(), localTask.getStartDelay(), localTask.getDuration(),
                localTask.getTags(), TaskState.EDITING, localTask.getGroupSize(), localTask.getVisibleLevel()==null? TaskVisibleLevel.UNKNOWN:TaskVisibleLevel.getByIndex(localTask.getVisibleLevel()),
                localTask.getUnitPoint(), localTask.getTaggingSceneType()==null? TaskTaggingSceneType.UNKNOWN:TaskTaggingSceneType.getByIndex(localTask.getTaggingSceneType()),
                localTask.getTaggingSceneSettings(), localTask.getInviteUsers(), localTask.getTotalQuota());
    }

    public static Task fromLocalInBackground(long localTaskIndex){
        if (ThreadUtil.isMainThread()){
            throw new RuntimeException("请在子线程调用该方法");
        }else {
            LocalTask localTask = DBUtilShop.localTaskDBUtil.queryEntity(localTaskIndex);
            if (localTask==null)return null;
            List<DataFile> dataFiles = new ArrayList<>();
            if (localTask.getLocalDataFiles()!=null){
                for (Long localDataFileIndex:localTask.getLocalDataFiles()){
                    LocalDataFile localDataFile = DBUtilShop.localDataFileDBUtil.queryEntity(localDataFileIndex);
                    if (localDataFile!=null){
                        dataFiles.add(new DataFile().fromLocal(localDataFile));
                    }
                }
            }
            if (localTask.getUploadedDataFiles()!=null){
                for (Long uploadedDataFileId:localTask.getUploadedDataFiles()){
                    //TODO 本地或网络请求已上传的数据文件的信息
                }
            }
            return new Task(localTaskIndex, InfoUtil.getUserId(), dataFiles, localTask.getTitle(),
                    localTask.getDescription(), localTask.getStartDelay(), localTask.getDuration(),
                    localTask.getTags(), TaskState.EDITING, localTask.getGroupSize(), localTask.getVisibleLevel()==null?TaskVisibleLevel.UNKNOWN:TaskVisibleLevel.getByIndex(localTask.getVisibleLevel()),
                    localTask.getUnitPoint(), localTask.getTaggingSceneType()==null?TaskTaggingSceneType.UNKNOWN:TaskTaggingSceneType.getByIndex(localTask.getTaggingSceneType()),
                    localTask.getTaggingSceneSettings(), localTask.getInviteUsers(), localTask.getTotalQuota());
        }
    }

//    public static class FromLocalTask extends AsyncTask<Long, Void, Task> {
//
//        private final Callback callback;
//
//        public FromLocalTask(Callback callback) {
//            this.callback = callback;
//        }
//
//        @Override
//        protected Task doInBackground(Long... params) {
//            long localTaskIndex = params[0];
//            Task task = new Task();
//            LocalTask localTask = DBUtilShop.localTaskDBUtil.queryEntity(localTaskIndex);
//            if (localTask != null) {
//                task.setState(TaskState.EDITING);
//                task.setTaskIndex(localTask.getTaskIndex());
//                task.setTitle(localTask.getTitle());
//                task.setDescription(localTask.getDescription());
//                task.setStartTime(localTask.getStartTime());
//                task.setEndTime(localTask.getEndTime());
//                task.setTags(localTask.getTags());
//                List<DataFile> dataFiles = new ArrayList<>();
//                if (localTask.getDataFiles()!=null){
//                    for (Long dataFileIndex : localTask.getDataFiles()) {
//                        dataFiles.add(new DataFile(DBUtilShop.localDataFileDBUtil.queryEntity(dataFileIndex)));
//                    }
//                }
//                task.setDataFiles(dataFiles);
//                task.setGroupSize(localTask.getGroupSize());
//                if (localTask.getVisibleLevel()!=null) task.setVisibleLevel(TaskVisibleLevel.getByIndex(localTask.getVisibleLevel()));
//                task.setTotalQuota(localTask.getTotalQuota());
//                task.setUnitPoint(localTask.getUnitPoint());
//                if (localTask.getTaggingSceneType()!=null) task.setTaggingSceneType(TaskTaggingSceneType.getByIndex(localTask.getTaggingSceneType()));
//                task.setTaggingSceneSettings(localTask.getTaggingSceneSettings());
//                task.setInviteUsers(localTask.getInviteUsers());
//            }
//            return task;
//        }
//
//        @Override
//        protected void onPostExecute(Task result) {
//            callback.onExecute(result);
//        }
//
//        public interface Callback {
//            void onExecute(Task result);
//        }
//    }

    public void addLike(){
        this.likeNum++;
    }

    public void reduceLike(){
        this.likeNum--;
    }

    public void addStar(){
        this.starNum++;
    }

    public void reduceStar(){
        this.starNum--;
    }


}
