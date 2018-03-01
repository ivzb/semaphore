package com.ivzb.encrypted_chat._base.ui.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ivzb.encrypted_chat._base.ui.DefaultView;
import com.ivzb.encrypted_chat._base.ui._contracts.adapters.BaseAdapter;
import com.ivzb.encrypted_chat._base.ui._contracts.presenters.BaseEndlessAdapterPresenter;
import com.ivzb.encrypted_chat._base.ui._contracts.view_models.BaseEndlessAdapterViewModel;
import com.ivzb.encrypted_chat._base.ui._contracts.views.BaseEndlessAdapterView;
import com.ivzb.encrypted_chat._base.ui.adapters.DefaultActionHandlerAdapter;
import com.ivzb.encrypted_chat._base.ui.event_listeners.DefaultEndlessRecyclerViewScrollListener;

import java.util.List;

public abstract class DefaultEndlessScrollView<M, P extends BaseEndlessAdapterPresenter<M>, VM extends BaseEndlessAdapterViewModel<M>>
        extends DefaultView<P, VM>
        implements BaseEndlessAdapterView<M, P, VM> {

    protected BaseAdapter<M> mAdapter;

    private DefaultEndlessRecyclerViewScrollListener mScrollListener;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void show(List<M> entities) {
        mAdapter.add(entities);
    }

    @Override
    public void clear() {
        mAdapter.clear();
        mScrollListener.resetState();
    }

    @Override
    public void openUi(M entity) {

    }

    @Override
    public void onRefresh() {
        mPresenter.refresh(mViewModel.getContainerId());
    }

    @Override
    public int getPage() {
        return mViewModel.getPage();
    }

    @Override
    public void setPage(int page) {
        mViewModel.setPage(page);
    }

    @Override
    public void setMore(boolean more) {
        mViewModel.setMore(more);

        if (mAdapter.size() == 0) {
            showEntities(false);
        }
    }

    @Override
    public RecyclerView.LayoutManager instantiateLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    protected void setUpRecycler(
            Context context,
            DefaultActionHandlerAdapter<M> adapter,
            final RecyclerView recyclerView) {

        mAdapter = adapter;
        LinearLayoutManager layoutManager = (LinearLayoutManager) instantiateLayoutManager(context);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        mScrollListener = new DefaultEndlessRecyclerViewScrollListener(
                layoutManager,
                mViewModel.getPage()) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!mViewModel.hasMore()) {
                    return;
                }

                mPresenter.load(mViewModel.getContainerId(), page);
            }
        };

        recyclerView.addOnScrollListener(mScrollListener);
    }
}