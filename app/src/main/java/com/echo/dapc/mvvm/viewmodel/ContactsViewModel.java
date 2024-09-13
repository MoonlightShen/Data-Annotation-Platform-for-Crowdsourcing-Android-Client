package com.echo.dapc.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.dapc.base.BaseViewModel;
import com.echo.dapc.mvvm.model.ContactsModel;
import com.echo.dapc.mvvm.view.list.FriendRequestListActivity;
import com.echo.dapc.mvvm.view.list.TeamNoticeListActivity;

public class ContactsViewModel extends BaseViewModel<ContactsModel> {

    public ContactsViewModel(@NonNull Application application) {
        super(application);
    }

    public void openFriendApplicationsPage(){
        createIntent(FriendRequestListActivity.class);
    }
    public void openTeamNoticesPage(){createIntent(TeamNoticeListActivity.class);}
}
