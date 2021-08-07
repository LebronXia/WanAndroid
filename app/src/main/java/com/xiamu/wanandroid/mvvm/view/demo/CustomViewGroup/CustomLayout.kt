package com.xiamu.wanandroid.mvvm.view.demo.CustomViewGroup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginEnd
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

/**
 * Created by zxb in 2021/6/21
 */
abstract class CustomLayout(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    protected val View.measureWidthWithMargins get() = (measuredWidth + marginLeft + marginRight)

    protected val View.measureHeightWithMargins get() = (measuredHeight + marginTop + marginEnd)

    //width进行测量模式的选择
    protected fun View.defaultWidthMeasureSpec(parentView: ViewGroup): Int{
        return when(layoutParams.width){
            ViewGroup.LayoutParams.MATCH_PARENT -> parentView.measuredWidth.toExactlyMeasureSpec()
            ViewGroup.LayoutParams.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT.toAtMostMeasureSpec()
            0 -> throw IllegalAccessException("Need special treatment for $this")

            else -> layoutParams.width.toExactlyMeasureSpec()
        }
    }

    //height进行测量模式的选择
    protected fun View.defaultHeightMeasureSpec(parentView: ViewGroup): Int{
        return when(layoutParams.width){
            ViewGroup.LayoutParams.MATCH_PARENT -> parentView.measuredWidth.toExactlyMeasureSpec()
            ViewGroup.LayoutParams.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT.toAtMostMeasureSpec()
            0 -> throw IllegalAccessException("Need special treatment for $this")

            else -> layoutParams.height.toExactlyMeasureSpec()
        }
    }

    //MeasureSpec.EXACTLY 当前值的测量
    protected fun Int.toExactlyMeasureSpec(): Int{
        return MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.EXACTLY)
    }

    //MeasureSpec.AT_MOST 当前值的测量
    protected fun Int.toAtMostMeasureSpec(): Int{
        return MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.AT_MOST)
    }

    //缩写测量measure方法
    protected fun View.autoMeasure(){
        measure(
            this.defaultWidthMeasureSpec(parentView = this@CustomLayout),
            this.defaultHeightMeasureSpec(parentView = this@CustomLayout)
        )
    }

    //左右进行布局
    protected fun View.layout(x: Int, y: Int, fromRight: Boolean = false){
        if (!fromRight){
            layout(x,y, x + measuredWidth, y + measuredHeight)
        } else {
            layout(this@CustomLayout.measuredWidth -x - measuredWidth, y)
        }
    }

    protected val Int.dp: Int get() = (this* resources.displayMetrics.density + 0.5f).toInt()

    protected class LayoutParams(width: Int, height: Int): MarginLayoutParams(width, height)
}