package com.xiamu.wanandroid.mvvm.demo.paging3.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.model.entry.Article
import kotlinx.android.synthetic.main.item_paging_homeliist.view.*

/**
 * Created by zxb in 2021/8/19
 */
class PagingNormalAdapter(): BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_paging_homeliist, emptyList()) {
    override fun convert(helper: BaseViewHolder?, item: Article) {
        helper?.run {
            itemView.tv_article_author.text = item.shareUser
            itemView.tv_article_title.text = item.title
            itemView.tv_article_chapterName.text = item.superChapterName + item.chapterName
        }
    }
}