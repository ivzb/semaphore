package com.ivzb.semaphore.user_search.ui;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ivzb.semaphore._base.data.config.DefaultConfig;
import com.ivzb.semaphore.users.data.UsersDataSource;
import com.ivzb.semaphore.users.ui.UsersPresenter;

public class UserSearchPresenter
        extends UsersPresenter
        implements UserSearchContract.Presenter {

    UserSearchPresenter(
            @NonNull Context context,
            @NonNull UserSearchContract.View view,
            @NonNull UsersDataSource dataSource) {

        super(context, view, dataSource);
    }

    @Override
    public void result(int requestCode, int resultCode, String message) {
        if (requestCode == REQUEST_USER_SAVE && resultCode == RESULT_OK) {
            ((UserSearchView) mView).finish(message);
        }
    }

    @Override
    public void refresh(String id) {
        if (id.equals(DefaultConfig.NO_ID)) {
            mView.showEntities(false);
            mView.showErrorMessage(false);
            mView.showNoEntities(false);
            return;
        }

        super.refresh(id);
    }

    @Override
    public void searchUser(String email) {
        if (!mView.isActive()) return;

        refresh(email);
    }
}
