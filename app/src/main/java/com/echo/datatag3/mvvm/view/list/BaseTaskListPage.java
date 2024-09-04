package com.echo.datatag3.mvvm.view.list;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.echo.datatag3.R;
import com.echo.datatag3.adapter.ClickCallback;
import com.echo.datatag3.adapter.TaskListAdapter;
import com.echo.datatag3.adapter.viewholder.task.TaskViewHolder;
import com.echo.datatag3.base.BaseItemListActivity;
import com.echo.datatag3.base.BaseItemListAdapter;
import com.echo.datatag3.bean.logic.Task;
import com.echo.datatag3.mvvm.view.task.TaskEditingInfoPage;
import com.echo.datatag3.util.GlobalConstant;
import com.echo.datatag3.widget.FloatingDragButton;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public abstract class BaseTaskListPage extends BaseItemListActivity<Task, TaskViewHolder> {

    @NonNull
    @Override
    protected BaseItemListAdapter<Task, TaskViewHolder> createAdapter() {
        return new TaskListAdapter(getContext(), getModel().getLauncher(), new ClickCallback() {
            @Override
            public void clickPosition(int position) {
                getModel().setLastClickPosition(position);
            }
        });
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
    protected void init() {
        super.init();
        if (buildFloatingButton()){
            new FloatingDragButton.Builder()
                    .setActivity(this)
                    .setIcon(R.drawable.common_add_floating_button)
                    .setOnClick(view -> {
                        newTask = true;
                        Intent intent = new Intent(getViewModel().getModel().getContext(), TaskEditingInfoPage.class);
                        getViewModel().getModel().getLauncher().launch(intent);
                        return null;
                    })
                    .build();
        }
    }
}
