package com.xiamu.wanandroid.mvvm.view.demo.nestedBehavior

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior

/**
 * RecycleView联动
 * Created by zxb in 2021/9/17
 */
class NestedRecycleView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    init {
        addOnScrollListener(object : OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

               // BottomSheetBehavior
            }
        })
    }

}