package com.templatexuv.apresh.customerapp.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;

/**
 * Created by Apresh on 9/6/2015.
 */
public abstract class EndlessOnScrollListener implements AbsListView.OnScrollListener {
    private int bufferItemCount = 10;
    private int currentPage = 0;
    private int itemCount = 0;
    private boolean isLoading = true;

    int mLastFirstVisibleItem = 0;


    public EndlessOnScrollListener(int bufferItemCount) {
        this.bufferItemCount = bufferItemCount;
    }

    public EndlessOnScrollListener() {

    }

    public abstract void loadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Do Nothing
    }



    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        /*if (totalItemCount < itemCount) {
            this.itemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true; }
        }*/

            final int currentFirstVisibleItem = view.getFirstVisiblePosition();

            if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                onHide();
            } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                onShow();
            }

            mLastFirstVisibleItem = currentFirstVisibleItem;

        if (isLoading && (totalItemCount > itemCount)) {
            isLoading = false;
            itemCount = totalItemCount;
            currentPage++;
        }

        if (!isLoading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + bufferItemCount)) {
            loadMore(currentPage + 1, totalItemCount);
            isLoading = true;
        }
    }

    public abstract void onHide();
    public abstract void onShow();
}