package com.xiamu.wanandroid.mvvm.view.adapter

import com.xiamu.baselibs.mvvm.binding.BaseBindAdapter
import com.xiamu.baselibs.mvvm.binding.BaseBindHolder
import com.xiamu.wanandroid.BR
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.model.entry.SearchHistoryBean

/**
 * Created by zhengxiaobo in 2019-11-18
 */
class SearchHistoryAdapter(data: List<SearchHistoryBean>?): BaseBindAdapter<SearchHistoryBean>(R.layout.item_search_history, data) {
    override fun convert(helper: BaseBindHolder?, item: SearchHistoryBean?) {

        val binding = helper?.getBinding()
        helper?.addOnClickListener(R.id.iv_delete)
        binding?.setVariable(BR.searchHistory, item)

        //它使数据绑定刷新所有挂起的更改。否则，它将视为另一个布局失效了
        binding?.executePendingBindings()
    }
}