package com.xiamu.wanandroid.mvvm.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cxz.wanandroid.widget.RecyclerViewItemDecoration
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.constant.AppConstant
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.view.activity.KnowTreeDetailActivity
import com.xiamu.wanandroid.mvvm.view.adapter.KnowTreeAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.TreeViewModel
import kotlinx.android.synthetic.main.fragment_refresh_layout.*
import timber.log.Timber

/**
 * Created by zhengxiaobo in 2019-10-30
 */
class KnowledgeTreeFragment: BaseVMFragment<TreeViewModel>(){

    override fun providerVMClass(): Class<TreeViewModel>? = TreeViewModel::class.java
    private val knowTreeAdapter: KnowTreeAdapter by lazy { KnowTreeAdapter(R.layout.item_konw_tree, null) }

    private var treeDatas :List<TreeBean> = ArrayList<TreeBean>()

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            RecyclerViewItemDecoration(it, LinearLayoutManager.VERTICAL)
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_refresh_layout
    }

    override fun initView(view: View) {
        refreshlayout.isEnabled = false
        initRecycleview()
    }

    override fun lazyLoad() {
        mViewModel.getTreeData()
    }

    private fun initRecycleview() {
        recycleview.run {
            adapter = knowTreeAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it)}
        }

        knowTreeAdapter.run {
            bindToRecyclerView(recycleview)
            onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                if (treeDatas.isNotEmpty()){
                    val data = treeDatas[position]
                    Intent(activity, KnowTreeDetailActivity::class.java).run {
                        putExtra(AppConstant.EXTRA_TREE_NAME, data.name)
                        putExtra(AppConstant.EXTRA_TREE_DATA, data)
                        startActivity(this)
                    }
                }
            }
        }
    }

    override fun startObserve() {
        super.startObserve()

        mViewModel.treeBeanState.observe(this, Observer {
            treeDatas = it
            it?.let {
                knowTreeAdapter.run {
                    replaceData(it)
                }
            }
        })

    }

}