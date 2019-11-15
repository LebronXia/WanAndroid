package com.xiamu.wanandroid.mvvm.view.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.model.entry.Article
import com.xiamu.wanandroid.mvvm.model.entry.NaviBean
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Created by zhengxiaobo in 2019-11-13
 */
class NaviListAdapter(data: List<NaviBean>?): BaseQuickAdapter<NaviBean, BaseViewHolder>(R.layout.item_navi_layout, data){

    override fun convert(helper: BaseViewHolder?, item: NaviBean?) {

        helper ?: return
        item ?: return

        helper.setText(R.id.item_title, item.name)
        var tagFlowLayout = helper.getView<TagFlowLayout>(R.id.item_flowlayout)
        tagFlowLayout.adapter = object : TagAdapter<Article>(item.articles){
            override fun getView(parent: FlowLayout?, position: Int, article : Article?): View? {

                article ?: return null
                var mTvTag: TextView =
                    mLayoutInflater.inflate(R.layout.item_flow_text, tagFlowLayout, false) as TextView
                mTvTag.setText(article.title)
                return mTvTag
            }

        }

    }


}


