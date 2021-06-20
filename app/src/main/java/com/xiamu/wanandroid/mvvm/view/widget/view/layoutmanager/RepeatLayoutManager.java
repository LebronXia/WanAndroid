package com.xiamu.wanandroid.mvvm.view.widget.view.layoutmanager;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zxb in 2021/1/23
 */
public class RepeatLayoutManager extends RecyclerView.LayoutManager {

   // https://juejin.cn/post/6870770285247725581
    //https://juejin.cn/post/6909363022980972552

    @RecyclerView.Orientation
    int mOrientation = RecyclerView.HORIZONTAL;

    public RepeatLayoutManager(@RecyclerView.Orientation int orientation) {
        mOrientation = orientation;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollHorizontally() {
        return mOrientation == RecyclerView.HORIZONTAL;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0){
            return;
        }
        if (state.isPreLayout()){
            return;
        }

        //所有Item分至scrap
        detachAndScrapAttachedViews(recycler);
        int itemLeft = getPaddingLeft();
        for (int i =0; ; i++){
            //超出布局宽度
            if (itemLeft >= getWidth() - getPaddingRight()){
                break;
            }

            //从缓存中获取子View
            View itemView = recycler.getViewForPosition(i % getItemCount());
            addView(itemView);
            //进行测量
            measureChildWithMargins(itemView, 0, 0);

            int right = itemLeft + getDecoratedMeasuredWidth(itemView);
            int top = getPaddingTop();
            int bottom = top + getDecoratedMeasuredHeight(itemView) - getPaddingBottom();
            //进行布局
            layoutDecorated(itemView, itemLeft, top, right, bottom);
            itemLeft = right;
        }
    }

    /**
     * 在滑动的时候填充和回收ItemView
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        fill(recycler, dx > 0);
        //对子View整体进行左右移动
        offsetChildrenHorizontal(-dx);
        recycleChildView(dx > 0, recycler);
        return dx;
    }

    /**
     * 重写isAutoMeasure，支持RecyclerView的wrap_content的
     * @return
     */
    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    /**
     * 滑动填充课件的未填充区域
     * @param recycler
     * @param fillEnd
     */
    private void fill(RecyclerView.Recycler recycler, boolean fillEnd) {
        if (getChildCount() == 0) return;

        if (fillEnd){
            //填充尾部
            //得到最后一位子View
            View anchorView = getChildAt(getChildCount() - 1);
            int anchorPosition = getPosition(anchorView);
            while (anchorView.getRight() < getWidth() - getPaddingRight()) {
                //ScrapVie的位置， 如果是最后一位则是0
                int position = (anchorPosition + 1) % getItemCount();
                if (position < 0) position += getItemCount();

                View scrapItem = recycler.getViewForPosition(position);
                addView(scrapItem);
                measureChildWithMargins(scrapItem, 0, 0);
                int left = anchorView.getRight();
                int top = getPaddingTop();
                int right = left + getDecoratedMeasuredWidth(scrapItem);
                int bottom = top + getDecoratedMeasuredHeight(scrapItem) - getPaddingBottom();
                layoutDecorated(scrapItem, left, top, right, bottom);
                anchorView = scrapItem;
            }

        } else {
            //填充首部
            View anchorView = getChildAt(0);
            int anchorPosition = getPosition(anchorView);
            for (; anchorView.getLeft() > getPaddingLeft();){
                //需要填充区域的位置
                int position = (anchorPosition - 1) % getItemCount();
                if (position < 0) position += getItemCount();

                View scrapItem = recycler.getViewForPosition(position);
                addView(scrapItem, 0);
                measureChildWithMargins(scrapItem, 0, 0);
                int top = getPaddingTop();
                int right = anchorView.getLeft();
                int left = right - getDecoratedMeasuredWidth(scrapItem);
                int bottom = top + getDecoratedMeasuredHeight(scrapItem) - getPaddingBottom();
                layoutDecorated(scrapItem, left, top, right, bottom);
                anchorView = scrapItem;
            }
        }
    }

    /**
     * 回收不可见的子View
     * @param fillEnd
     * @param recycler
     */
    private void recycleChildView(boolean fillEnd, RecyclerView.Recycler recycler) {
        if (fillEnd){
            for (int i = 0; ; i++){
                View view = getChildAt(i);
                boolean needRecycler = view != null && view.getRight() < getPaddingLeft();
                if (needRecycler){
                    removeAndRecycleView(view, recycler);
                } else {
                    return;
                }
            }
        } else {
            //回收尾部
            for (int i = getChildCount() - 1; ; i--) {
                View view = getChildAt(i);
                boolean needRecycler = view != null && view.getLeft() > getWidth() - getPaddingRight();
                if (needRecycler) {
                    removeAndRecycleView(view, recycler);
                } else {
                    return;
                }
            }
        }

    }
}


