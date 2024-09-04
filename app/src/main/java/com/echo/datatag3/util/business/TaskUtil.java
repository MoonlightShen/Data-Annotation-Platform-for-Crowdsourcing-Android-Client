package com.echo.datatag3.util.business;

import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import com.echo.datatag3.bean.entity.LocalTask;
import com.echo.datatag3.bean.enumeration.StatusCode;
import com.echo.datatag3.bean.enumeration.TaskState;
import com.echo.datatag3.bean.enumeration.TaskTaggingSceneType;
import com.echo.datatag3.bean.enumeration.TaskVisibleLevel;
import com.echo.datatag3.bean.logic.Task;
import com.echo.datatag3.bean.logic.taggingscene.LabelClassification;
import com.echo.datatag3.interfaces.callback.common.CommonBooleanCallback;
import com.echo.datatag3.interfaces.callback.common.CommonCallback;
import com.echo.datatag3.interfaces.callback.common.CommonEntityCallback;
import com.echo.datatag3.interfaces.callback.common.CommonEntityListCallback;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBDeleteCallback;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBQueryCallback;
import com.echo.datatag3.interfaces.callback.task.LoadAllLocalTasksCallback;
import com.echo.datatag3.interfaces.callback.task.LoadAllTasksCallback;
import com.echo.datatag3.interfaces.callback.task.QueryLocalTaskCallback;
import com.echo.datatag3.interfaces.callback.task.SaveLocalTaskCallback;
import com.echo.datatag3.interfaces.callback.task.TaskListCallback;
import com.echo.datatag3.interfaces.callback.task.UploadTaskCallback;
import com.echo.datatag3.util.database.DBUtilShop;
import com.echo.datatag3.util.network.request.TaskRequest;
import com.echo.datatag3.util.network.response.common.ExistRes;
import com.echo.datatag3.util.network.response.common.StatusCodeRes;
import com.echo.datatag3.util.network.response.task.TaskInfoRes;
import com.echo.datatag3.util.network.response.task.TaskInfoListRes;
import com.echo.datatag3.util.network.response.task.TaskResponse;
import com.echo.datatag3.util.system.EncryptionUtil;
import com.echo.datatag3.util.system.JsonUtil;
import com.echo.datatag3.util.system.ThreadUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public final class TaskUtil {

    public static void queryLocalTask(QueryLocalTaskCallback callback, long taskIndex){
        new Thread(() -> {
            try{
                LocalTask localTask = DBUtilShop.localTaskDBUtil.queryEntity(taskIndex);
                callback.onSuccess(localTask);
            }catch (SQLiteException e){
                callback.onError(e.getMessage());
            }
        }).start();
    }

    public static void saveLocalTask(SaveLocalTaskCallback callback, LocalTask localTask){
        new Thread(() -> {
            try {
                if (localTask.getTaskIndex() == null) DBUtilShop.localTaskDBUtil.insertEntity(localTask);
                else DBUtilShop.localTaskDBUtil.updateEntity(localTask);
                callback.onSuccess();
            } catch (SQLiteException e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

    public static void removeLocalTask(@NonNull SQLiteDBDeleteCallback callback, @NonNull LocalTask localTask) {
        new Thread(() -> {
            try {
                DBUtilShop.localTaskDBUtil.deleteEntity(localTask);
                callback.onSuccess();
            } catch (SQLiteException e) {
                callback.onSQLiteError();
            }
        }).start();
    }

    public static void loadAllLocalTasks(LoadAllLocalTasksCallback callback){
        ThreadUtil.runInBackground(()->{
            try{
                callback.onSuccess(DBUtilShop.localTaskDBUtil.loadAllEntities());
            }catch (SQLiteException e){
                callback.onError(e.getMessage());
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }

    @Deprecated
    private static List<Task> createExampleTasks(TaskState state){
        List<Task> tasks = new ArrayList<>();
        Random random =new Random();
        List<String> names = new ArrayList<>();
        names.add("分辨成语所表达的情绪");
        names.add("中日名字分类");
        names.add("筛选有价值的评论");
        names.add("动物分类");
        names.add("植物科属分析");
        for (int i=0;i<5;i++){
            Task task =new Task();
            task.setOwnerId((long)Math.abs(random.nextInt(90))+11);
            task.setState(state);
            long currentTime = System.currentTimeMillis();
            task.setTitle(names.get(0));
            names.remove(0);
            task.setStartTime(currentTime-(currentTime%10000+Math.abs(random.nextInt(100000)))*Math.abs(random.nextInt(10000))-Math.abs(random.nextInt(1000000000)));
            task.setEndTime(currentTime+(currentTime%10000+Math.abs(random.nextInt(100000)))*Math.abs(random.nextInt(10000))+Math.abs(random.nextInt(1000000000)));
            int totalQuota = Math.abs(random.nextInt(100))+10;
            task.setTotalQuota(totalQuota);
            task.setRemainingQuota(totalQuota - Math.abs(random.nextInt(totalQuota-1)));
            int likeNum = Math.abs(random.nextInt(200)+1);
            task.setLikeNum(likeNum);
            task.setStarNum(likeNum/2 + 1 + Math.abs(random.nextInt(20))%likeNum);
            task.setReadNum(likeNum*3 + 1 + Math.abs(random.nextInt(50)));
            Set<Long> set = new HashSet<>();
            int applyNum = Math.abs(random.nextInt(20))+6;
            while (set.size()<applyNum){
                set.add((long)Math.abs(random.nextInt(90))+11);
            }
            task.setApplyUsers(new ArrayList<>(set));
            List<String> tags = new ArrayList<>();
            int tagsSize = Math.abs(random.nextInt(9))+1;
            while (tags.size()<tagsSize){
                tags.add(EncryptionUtil.string2md5(String.valueOf(random.nextInt(99999999)+1)).substring(0, Math.abs(random.nextInt(3))+3));
            }
            task.setTags(tags);
            task.setGroupSize(Math.abs(random.nextInt(100)+10));
            task.setUnitPoint(Math.abs(random.nextInt(10)+1));

            task.setVisibleLevel(TaskVisibleLevel.OPEN);
            task.setTaggingSceneType(TaskTaggingSceneType.LABEL_CLASSIFICATION);
            List<String> tagOptions = new ArrayList<>();
            int optionsNum = Math.abs(random.nextInt(5)+2);
            while (tagOptions.size()<optionsNum){
                tagOptions.add(EncryptionUtil.string2md5(String.valueOf(random.nextInt(99999999)+1)).substring(0, Math.abs(random.nextInt(5))+3));
            }
            task.setTaggingSceneSettings(JsonUtil.toJson(new LabelClassification(tagOptions, random.nextBoolean(), random.nextBoolean())));

            task.setUnitPoint(Math.abs(random.nextInt(10)+1));
            task.setAutoApproveFetch(true);
            task.setAutoReviewAnswer(false);
            tasks.add(task);
        }
        return tasks;
    }

    public static void loadHistoricalTasks(LoadAllTasksCallback callback){
        new Thread(() -> {
            List<Task> tasks = new ArrayList<>();
            try {
                tasks.addAll(createExampleTasks(TaskState.COMPLETED));
                //TODO 网络请求查询历史任务(已完成的任务）
                callback.onSuccess(tasks);
            } catch (SQLiteException e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

    public static void getRecommendationTasks(TaskListCallback callback, int count) {
        new Thread(() -> {
            try {
                List<Task> tasks = new ArrayList<>();
                tasks.addAll(createExampleTasks(TaskState.RUNNING));
                TaskInfoListRes res = TaskRequest.requestRecommendationTasks(count);
                if (StatusCode.SUCCESS.getCode().equals(res.code)) {
                    for (TaskResponse response : res.data) {
                        response.state = TaskState.RUNNING.getIndex();
                        tasks.add(new Task(response));
                    }
                    callback.onSuccess(tasks, false);
                }
            } catch (SQLiteException e) {
                callback.onSQLiteError();
            } catch (IOException e) {
                callback.onIOException();
            }
        }).start();
    }

    public static void uploadTask(@NonNull UploadTaskCallback callback, @NonNull Task task) {
        new Thread(() -> {
            try {
                TaskInfoRes res = TaskRequest.uploadTask(task);
                if (StatusCode.SUCCESS.getCode().equals(res.code)) {
                    callback.onSuccess(res.data.taskId);
                } else {
                    callback.unknownError(res.code);
                }
            } catch (IOException e) {
                callback.onIOException();
            }
        }).start();
    }

    public static void initLoadTasks(@NonNull SQLiteDBQueryCallback<Task> callback){
        ThreadUtil.submitToCached(()->{
            try {
                List<Task> tasks = new ArrayList<>();
                for (LocalTask localTask:DBUtilShop.localTaskDBUtil.loadAllEntities()){
                    Task task = Task.fromLocalInBackground(localTask.getTaskIndex());
                    if (task!=null)tasks.add(task);
                }
                //TODO 网络请求任务
                callback.onSuccess(tasks);
            }catch (SQLiteException e){
                callback.onSQLiteDBError();
            }
        });
    }

    public static void refreshTask(@NonNull CommonEntityCallback<Task> callback, long taskIndex, long taskId) {
        ThreadUtil.submitToCached(()->{
            try {
                Task task = null;
                if (taskIndex!=0){
                    task = Task.fromLocalInBackground(taskIndex);
                }else {
                    //TODO 网络请求
                }
                callback.onSuccess(task);
            }catch (SQLiteException e){
                callback.onSQLiteDBError();
            }
        });
    }

    public static void queryTask(@NonNull CommonEntityCallback<Task> callback, long taskId) {
        ThreadUtil.submitToCached(()->{
            try {
                Task task = new Task();
                //TODO 网络请求
                callback.onSuccess(task);
            }catch (SQLiteException e){
                callback.onSQLiteDBError();
            }
        });
    }

    public static void likeTask(@NonNull CommonCallback callback, long taskId){
        ThreadUtil.runInBackground(()->{
            try {
                StatusCodeRes res = TaskRequest.likeTask(taskId);
                if (StatusCode.success(res.code)){
                    callback.onSuccess();
                }else{
                    callback.onError(res.code);
                }
            }catch (IOException e){
                callback.onIOException();
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }

    public static void cancelLikeTask(@NonNull CommonCallback callback, long taskId){
        ThreadUtil.runInBackground(()->{
            try {
                StatusCodeRes res = TaskRequest.cancelLikeTask(taskId);
                if (StatusCode.success(res.code)){
                    callback.onSuccess();
                }else{
                    callback.onError(res.code);
                }
            }catch (IOException e){
                callback.onIOException();
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }

    public static void checkLikeTask(@NonNull CommonBooleanCallback callback, long taskId){
        ThreadUtil.runInBackground(()->{
            try {
                ExistRes res = TaskRequest.checkLikeTask(taskId);
                if (StatusCode.success(res.code)){
                    callback.onSuccess(res.data.exist);
                }else{
                    callback.onError(res.code);
                }
            }catch (IOException e){
                callback.onIOException();
            }
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }

    public static void queryUncompletedTask(@NonNull CommonEntityListCallback<Task> callback){
        ThreadUtil.runInBackground(()->{
            //TODO 网络查询
            callback.onSuccess(createExampleTasks(TaskState.RUNNING));
        }, ThreadUtil.BackgroundStrategy.CACHED);
    }

}
