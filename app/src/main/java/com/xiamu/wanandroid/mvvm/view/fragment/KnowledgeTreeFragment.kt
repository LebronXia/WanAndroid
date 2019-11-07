package com.xiamu.wanandroid.mvvm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxz.wanandroid.widget.RecyclerViewItemDecoration
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.view.adapter.KnowTreeAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.TreeViewModel
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * Created by zhengxiaobo in 2019-10-30
 */
class KnowledgeTreeFragment: BaseVMFragment<TreeViewModel>(){

    override fun providerVMClass(): Class<TreeViewModel>? = TreeViewModel::class.java
    private val knowTreeAdapter: KnowTreeAdapter by lazy { KnowTreeAdapter(R.layout.item_konw_tree, null) }

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            RecyclerViewItemDecoration(it, LinearLayoutManager.VERTICAL)
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater.inflate(R.layout.fragment_refresh_layout, container, false)
        return view
    }

    private fun initRecycleview() {
        recycleview.run {
            adapter = knowTreeAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it)}
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        refreshlayout.isEnabled = false
        initRecycleview()
        mViewModel.getTreeData()
    }

    override fun startObserve() {
        super.startObserve()

        mViewModel.treeBeanState.observe(this, Observer {
            it?.let {
                knowTreeAdapter.run {
                    replaceData(it)
                }
            }
        })

    }

    override fun setData(data: Any) {

    }

}