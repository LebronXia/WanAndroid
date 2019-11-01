package com.xiamu.wanandroid.mvvm.view.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class BottomNavigationBehavior(context: Context, attributeSet: AttributeSet): CoordinatorLayout.Behavior<View>() {
    private var outAnimator: ObjectAnimator?= null
    private var inAnimator: ObjectAnimator?= null

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return type == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (dy > 0) {// 上滑隐藏
            if (outAnimator == null) {
                outAnimator = ObjectAnimator.ofFloat(child, "translationY", 0f, child.height.toFloat())
                outAnimator?.duration = 200
            }
            if (!outAnimator!!.isRunning && child.translationY <= 0) {
                outAnimator?.start()
            }
        } else if (dy < 0) {// 下滑显示
            if (inAnimator == null) {
                inAnimator = ObjectAnimator.ofFloat(child, "translationY", child.getHeight().toFloat(), 0f);
                inAnimator?.duration=200
            }
            if (!inAnimator!!.isRunning && child.translationY>= child.height) {
                inAnimator?.start()
            }
        }
    }

}