package com.ivzb.semaphore.users.ui;


import android.content.Intent;

import com.ivzb.semaphore._base.ui.endless_scroll.EndlessScrollViewTest;
import com.ivzb.semaphore.users.data.UserEntity;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UsersViewTest
        extends EndlessScrollViewTest<UserEntity, UsersContract.View, UsersContract.Presenter, UsersContract.ViewModel> {

    private @Mock UsersContract.Presenter mPresenter;
    private @Mock UsersContract.ViewModel mViewModel;

    @Override
    public UsersView getView() {
        return (UsersView) mFragment;
    }

    @Override
    public UsersContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public UsersContract.ViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public UserEntity initEntity(String id) {
        return new UserEntity(id);
    }

    @Override
    public UsersView initView() {
        return new UsersView();
    }

    @Test
    public void openConversation() {
        // arrange
        UserEntity model = initEntity("5");

        // act
        getView().openConversation(model);

        // assert
        verify(mContext).startActivity(isA(Intent.class));
    }

    @Test
    public void onUserClick() {
        // arrange
        UserEntity entity = mock(UserEntity.class);

        // act
        getView().onClickOpenConversation(entity);

        // assert
        verify(getPresenter()).clickUser(eq(entity));
    }
}

