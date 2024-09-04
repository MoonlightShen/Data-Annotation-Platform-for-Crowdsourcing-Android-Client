package com.echo.datatag3.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.echo.datatag3.base.BaseViewModel;
import com.echo.datatag3.mvvm.model.ContactsModel;
import com.echo.datatag3.mvvm.view.list.FriendRequestListActivity;
import com.echo.datatag3.mvvm.view.list.TeamNoticeListActivity;

public class ContactsViewModel extends BaseViewModel<ContactsModel> {

    public ContactsViewModel(@NonNull Application application) {
        super(application);
    }

    public void openFriendApplicationsPage(){
        createIntent(FriendRequestListActivity.class);
    }
    public void openTeamNoticesPage(){createIntent(TeamNoticeListActivity.class);}
}
