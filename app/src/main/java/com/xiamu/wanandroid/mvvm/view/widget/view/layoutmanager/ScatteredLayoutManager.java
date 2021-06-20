package com.xiamu.wanandroid.mvvm.view.widget.view.layoutmanager;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zxb in 2021/1/26
 */
public class ScatteredLayoutManager extends RecyclerView.LayoutManager {

    private int leftMargin, rightMargin;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        if (getChildCount() == 0 && state.isPreLayout()){
            return;
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) recycler.getViewForPosition(0).getLayoutParams();
        leftMargin = params.leftMargin;
        rightMargin = params.rightMargin;

        //所有Item分至scrap
        detachAndScrapAttachedViews(recycler);
        layoutItem(recycler);

    }

    private void layoutItem(RecyclerView.Recycler recycler) {
        int itemTop = getPaddingTop();
        View itemView = recycler.getViewForPosition(0);
        int width = (Resources.getSystem().getDisplayMetrics().widthPixels
                - getRightDecorationWidth(itemView)
                - getLeftDecorationWidth(itemView)
                - leftMargin - rightMargin) / 2;

        for(int i = 0; ; i++){
            if (itemTop > getHeight() - getPaddingBottom()){
                break;
            }
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);

            int height = getDecoratedMeasuredHeight(view);
            Rect mTmpRect = new Rect();
            calculateItemDecorationsForChild(view, mTmpRect);

            if (i == 0 || i == 1 || i ==2){
                switch (i){
                    case 0:
                        layoutDecoratedWithMargins(view, 0, itemTop, width, itemTop + height);
                        break;
                    case 1:
                        layoutDecoratedWithMargins(view, width, itemTop, 2 * width, itemTop + height / 2);
                        break;
                    case 2:
                        layoutDecoratedWithMargins(view, width, itemTop + height / 2, 2 * width, itemTop + height);
                        break;
                }
            } else {

                if (i % 2 == 0){
                    //左
                    layoutDecoratedWithMargins(view, 0, itemTop, width, itemTop + height);
                } else {
                    //右
                    layoutDecoratedWithMargins(view, width, itemTop, 2 * width, itemTop + height);
                    itemTop = itemTop + height;
                }
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        fillVertical(recycler, dy > 0);
        offsetChildrenVertical(-dy);
       // recyclerChildView(dy > 0, recycler);
        return dy;
    }

    private void fillVertical(RecyclerView.Recycler recycler, boolean fillEnd) {


    }
}
