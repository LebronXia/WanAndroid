package com.xiamu.wanandroid.mvvm.view.adapter

import com.xiamu.baselibs.mvvm.binding.BaseBindAdapter
import com.xiamu.baselibs.mvvm.binding.BaseBindHolder
import com.xiamu.wanandroid.BR
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.model.entry.Article

/**
 * Created by zhengxiaobo in 2019-11-21
 */
class CollectAdapter(layoutResId: Int, data: List<Article>?): BaseBindAdapter<Article>(layoutResId, data) {

    override fun convert(helper: BaseBindHolder?, item: Article?) {
        val binding = helper?.getBinding()
        binding?.setVariable(BR.article, item)
        //它使数据绑定刷新所有挂起的更改。否则，它将视为另一个布局失效了
        binding?.executePendingBindings()

        helper?.addOnClickListener(R.id.iv_article_like)
            ?.setImageResource(R.id.iv_article_like, R.drawable.ic_like)
    }

}