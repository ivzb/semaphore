package com.ivzb.semaphore.users.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivzb.semaphore.R;
import com.ivzb.semaphore._base.ui._contracts.BaseAdapter;
import com.ivzb.semaphore._base.ui._contracts.BaseEntityActionHandler;
import com.ivzb.semaphore._base.ui.endless_scroll.EndlessScrollView;
import com.ivzb.semaphore._base.ui.prompt.DialogListener;
import com.ivzb.semaphore._base.ui.prompt.PromptDialogFragment;
import com.ivzb.semaphore.conversation.ui.ConversationActivity;
import com.ivzb.semaphore.users.data.UserEntity;

public class UsersView
        extends EndlessScrollView<UserEntity, UsersContract.Presenter, UsersContract.ViewModel>
        implements UsersContract.View {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mPresenter.refresh(mViewModel.getContainerId());

        return view;
    }

    @Override
    public View inflateFragment(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.users_frag, container, false);
    }

    @Override
    public BaseAdapter<UserEntity> initEndlessAdapter() {
        return new UsersAdapter(
                        mContext,
                        mUserClickListener,
                        mAddUserClickListener,
                        mRemoveUserClickListener);
    }

    @Override
    public void openConversation(UserEntity user) {
        Intent intent = new Intent(mContext, ConversationActivity.class);
//        intent.putExtra(ConversationActivity.EXTRA_USER_ID, user.getId());
        mContext.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh(mViewModel.getContainerId());
    }

    @Override
    public void onClickOpenConversation(UserEntity user) {
        mPresenter.clickUser(user);
    }

    private BaseEntityActionHandler<UserEntity> mUserClickListener = new BaseEntityActionHandler<UserEntity>() {
        @Override
        public void onAdapterEntityClick(UserEntity user) {
            onClickOpenConversation(user);
        }
    };

    @Override
    public void onClickAddUser(UserEntity user) {
        mPresenter.clickAddUser(user);
    }

    private BaseEntityActionHandler<UserEntity> mAddUserClickListener = new BaseEntityActionHandler<UserEntity>() {
        @Override
        public void onAdapterEntityClick(UserEntity user) {
            onClickAddUser(user);
        }
    };

    @Override
    public void onClickRemoveUser(UserEntity user) {
        mPresenter.clickRemoveUser(user);
    }

    private BaseEntityActionHandler<UserEntity> mRemoveUserClickListener = new BaseEntityActionHandler<UserEntity>() {
        @Override
        public void onAdapterEntityClick(final UserEntity user) {
            PromptDialogFragment dialogFragment = new PromptDialogFragment()
                .setTitle(getResources().getString(R.string.remove_user))
                .setListener(new DialogListener() {
                    @Override
                    public void onDialogPositiveClick() {
                        onClickRemoveUser(user);
                    }

                    @Override
                    public void onDialogNegativeClick() {
                        // no action needed
                    }
                });

            dialogFragment.show(getFragmentManager(), "remove_user_dialog");
        }
    };

}
