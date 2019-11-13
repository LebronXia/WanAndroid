package com.xiamu.wanandroid.mvvm.view.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import com.hss01248.dialog.StyledDialog.context
import com.xiamu.wanandroid.R
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

/**
 * Created by zhengxiaobo in 2019-11-11
 */
class NaviTabAdapter(context: Context?, var tablist: List<String>): TabAdapter{
    override fun getIcon(position: Int): ITabView.TabIcon? {
        return null
    }

    override fun getBadge(position: Int): ITabView.TabBadge? {
        return null
    }

    override fun getBackground(position: Int): Int {
        return -1
    }

    override fun getTitle(position: Int): ITabView.TabTitle {
        return ITabView.TabTitle.Builder()
            .setContent(tablist[position])
            .setTextColor(
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.Grey))
            .build()
    }

    override fun getCount(): Int {
        return tablist.size
    }

}