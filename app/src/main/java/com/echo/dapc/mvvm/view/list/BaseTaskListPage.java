package com.echo.dapc.mvvm.view.list;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.adapter.TaskListAdapter;
import com.echo.dapc.adapter.viewholder.task.TaskViewHolder;
import com.echo.dapc.base.activity.BaseItemListActivity;
import com.echo.dapc.base.adapter.BaseRecycleListAdapter;
import com.echo.dapc.bean.logic.Task;
import com.echo.dapc.mvvm.view.task.TaskEditingInfoActivity;
import com.echo.dapc.widget.FloatingDragButton;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

public abstract class BaseTaskListPage extends BaseItemListActivity<Task, TaskViewHolder> {

    @NonNull
    @Override
    protected BaseRecycleListAdapter<Task, TaskViewHolder> createAdapter() {
        return new TaskListAdapter(getContext(), getModel().getLauncher(), position -> getModel().setLastClickPosition(position));
    }

    protected abstract void onRightClick(TitleBar titleBar);

    @NonNull
    @Override
    protected OnTitleBarListener getTitleBarListener() {
        return new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                BaseTaskListPage.this.onRightClick(titleBar);
            }
        };
    }

    protected abstract boolean buildFloatingButton();

    protected boolean newTask=false;

    @Override
    protected void initActivity() {
        super.initActivity();
        if (buildFloatingButton()){
            new FloatingDragButton.Builder()
                    .setActivity(this)
                    .setIcon(R.drawable.common_add_floating_button)
                    .setOnClick(view -> {
                        newTask = true;
                        Intent intent = new Intent(getViewModel().getModel().getContext(), TaskEditingInfoActivity.class);
                        getViewModel().getModel().getLauncher().launch(intent);
                        return null;
                    })
                    .build();
        }
    }
}
