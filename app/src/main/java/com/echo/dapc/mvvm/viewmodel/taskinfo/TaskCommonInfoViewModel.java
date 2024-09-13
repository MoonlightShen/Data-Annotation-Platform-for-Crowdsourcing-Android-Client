package com.echo.dapc.mvvm.viewmodel.taskinfo;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.echo.dapc.R;
import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.task.TaskCommonInfoModel;
import com.echo.dapc.mvvm.view.task.TaskEditingInfoActivity;
import com.echo.dapc.widget.PopupMenu.MenuItem;
import com.echo.dapc.widget.PopupMenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

public class TaskCommonInfoViewModel extends BaseViewModel<TaskCommonInfoModel> {
    public TaskCommonInfoViewModel(@NonNull Application application) {
        super(application);
    }

    TopRightMenu topRightMenu = null;
    public void openMenu(View view) {
        if (topRightMenu == null) {
            topRightMenu = new TopRightMenu((TaskEditingInfoActivity) getModel().getContext());
            List<MenuItem> menuItems = new ArrayList<>();
            menuItems.add(new MenuItem(R.drawable.common_left_gray, "删除任务"));
            menuItems.add(new MenuItem(R.drawable.common_left_gray, "撤回审核"));
            topRightMenu
                    .addMenuList(menuItems)
                    .setOnMenuItemClickListener(position -> {
                        switch (position) {
                            case 0 -> deleteTask();
                            case 1 -> withdrawAudit();
                        }
                    })
                    .showAsDropDown(view, -100, 0);    //带偏移量
        } else {
            topRightMenu.showAsDropDown(view, -100, 0);
        }
    }

    public void deleteTask(){

    }

    public void withdrawAudit(){

    }
}
