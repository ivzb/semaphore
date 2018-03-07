package com.ivzb.semaphore.conversations.ui;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ivzb.semaphore._base.data.Result;
import com.ivzb.semaphore._base.data.callbacks.SaveCallback;
import com.ivzb.semaphore._base.ui.endless.DefaultEndlessScrollPresenter;
import com.ivzb.semaphore.conversations.data.ConversationEntity;
import com.ivzb.semaphore.conversations.data.ConversationsDataSource;

public class ConversationsPresenter
        extends DefaultEndlessScrollPresenter<ConversationEntity, ConversationsContract.View, ConversationsDataSource>
        implements ConversationsContract.Presenter {

    protected static final int REQUEST_USER_SAVE = 301;
    protected static final int RESULT_OK = -1;

    public ConversationsPresenter(
            @NonNull Context context,
            @NonNull ConversationsContract.View view,
            @NonNull ConversationsDataSource dataSource) {

        super(context, view, dataSource);
    }

    @Override
    public void result(int requestCode, int resultCode, String message) {
        if (requestCode == REQUEST_USER_SAVE && resultCode == RESULT_OK) {
            mView.setSuccessMessage(message);
            mView.showSuccessMessage(true);
        }
    }

    @Override
    public void clickConversation(ConversationEntity conversation) {
        if (!mView.isActive()) return;

        mView.openConversation(conversation);
    }

    @Override
    public void clickRemoveConversation(ConversationEntity user) {
        if (!mView.isActive()) return;

        mView.setLoadingIndicator(true);

        mDataSource.remove(user.getId(), mConversationSaveCallback);
    }

    private SaveCallback<Boolean> mConversationSaveCallback = new SaveCallback<Boolean>() {
        @Override
        public void onSuccess(Result<Boolean> result) {
            if (!mView.isActive()) return;

            mView.setLoadingIndicator(false);

            Boolean added = result.getResults();

            if (!added) {
                mView.setErrorMessage(result.getMessage());
                mView.showErrorMessage(true);

                return;
            }

            result(REQUEST_USER_SAVE, RESULT_OK, result.getMessage());
        }

        @Override
        public void onFailure(String message) {
            if (!mView.isActive()) return;

            mView.setLoadingIndicator(false);

            mView.setErrorMessage(message);
            mView.showErrorMessage(true);
        }
    };
}