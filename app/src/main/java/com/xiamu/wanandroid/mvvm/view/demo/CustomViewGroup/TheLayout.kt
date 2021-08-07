package com.xiamu.wanandroid.mvvm.view.demo.CustomViewGroup

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xiamu.wanandroid.R

/**
 * Created by zxb in 2021/6/20
 */
class TheLayout : CustomLayout{

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    constructor(context: Context): super(context, null)

    val header = AppCompatImageView(context).apply {
        scaleType = ImageView.ScaleType.CENTER_CROP
        setImageResource(R.drawable.ic_mycat)
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 280.dp)
        addView(this)
    }

    val fab = FloatingActionButton(context).apply {
        setImageResource(R.drawable.ic_mycat)
        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).also {
            it.marginEnd = 12.dp
        }

        addView(this)
    }

    val avatar = AppCompatImageView(context).apply {
        setImageResource(R.drawable.ic_mycat)
        layoutParams = LayoutParams(48.dp, 48.dp).also {
            it.marginStart= 16.dp
            it.topMargin = 24.dp
        }
        addView(this)
    }

    val itemTitle = AppCompatTextView(context).apply {
        text = "hahahahah"
        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).also {
            it.marginStart = 16.dp
            it.marginEnd = 16.dp
        }
        addView(this)
    }

    val itemMessage = AppCompatTextView(context).apply {
        text = "这个是很长的文章。。。。。。。。。"
        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).also {
            it.marginStart = 16.dp
            it.marginEnd = 16.dp
        }
        addView(this)
    }

    val reply = AppCompatImageView(context).apply {
        setImageResource(R.drawable.ic_mycat)
        layoutParams = LayoutParams(24.dp, 24.dp).also {
            it.setMargins(24.dp, 24.dp, 24.dp, 24.dp)
        }
        addView(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        header.autoMeasure()
        fab.autoMeasure()
        avatar.autoMeasure()
        reply.autoMeasure()
        reply.autoMeasure()

        val itemTextWidth = (measuredWidth
                - avatar.measureWidthWithMargins
                - reply.measureWidthWithMargins
                - itemTitle.marginLeft
                - itemTitle.marginRight)

        itemTitle.measure(itemTextWidth.toExactlyMeasureSpec(), itemTitle.defaultHeightMeasureSpec(this))
        itemMessage.measure(itemTextWidth.toExactlyMeasureSpec(), itemMessage.defaultHeightMeasureSpec(this))


        val max = (avatar.marginTop + itemTitle.measureHeightWithMargins + itemMessage.measureHeightWithMargins)
            .coerceAtLeast(avatar.measureHeightWithMargins)

        val wrapContentHeight = header.measureHeightWithMargins + max
        setMeasuredDimension(measuredWidth, wrapContentHeight)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        header.layout(0, 0)
        fab.let{it.layout(it.marginRight, header.bottom - (it.measuredHeight/2), true)}
        avatar.let { it.layout(it.marginLeft, header.bottom + it.marginTop) }
        itemTitle.let { it.layout(avatar.right + it.marginLeft, avatar.top + it.marginTop) }
        itemMessage.let { it.layout(avatar.right + it.marginLeft, itemTitle.bottom + it.marginTop) }
        reply.let { it.layout(it.marginRight, avatar.top + it.marginTop, true) }
    }

}