package com.xiamu.wanandroid.mvvm.demo.twoView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout

class MyRelativeLayout: RelativeLayout {
    constructor(context: Context):super(context){

    }
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){

    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr){

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("MyRelativeLayout","dispatchTouchEvent ACTION_DOWN")
            }
            MotionEvent.ACTION_UP -> {
                Log.e("MyRelativeLayout","dispatchTouchEvent ACTION_UP")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("MyRelativeLayout","dispatchTouchEvent ACTION_MOVE")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.e("MyRelativeLayout","dispatchTouchEvent ACTION_CANCEL")
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("MyRelativeLayout","onInterceptTouchEvent ACTION_DOWN")
            }
            MotionEvent.ACTION_UP -> {
                Log.e("MyRelativeLayout","onInterceptTouchEvent ACTION_UP")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("MyRelativeLayout","onInterceptTouchEvent ACTION_MOVE")
            }

        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("MyRelativeLayout","onTouchEvent ACTION_DOWN")
            }
            MotionEvent.ACTION_UP -> {
                Log.e("MyRelativeLayout","onTouchEvent ACTION_UP")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.e("MyRelativeLayout","onTouchEvent ACTION_MOVE")
            }
        }
        return super.onTouchEvent(event)
    }

}