package com.xiamu.wanandroid.mvvm.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xiamu.baselibs.base.BaseFragment
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.databinding.HomeVieModelBinding
import com.xiamu.wanandroid.mvvm.view.adapter.HomeArticleAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.MainHomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class HomeFragment: BaseFragment<HomeVieModelBinding, MainHomeViewModel>() {

    private var isFresh = true
    private val homeArticleAdapter: HomeArticleAdapter by lazy{HomeArticleAdapter(R.layout.item_homelist, null)}

    override fun providerVMClass(): Class<MainHomeViewModel>? = MainHomeViewModel::class.java

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //ViewModel 和 binding绑定
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        mBinding.viewModel = mViewModel

        mBinding.recycleview.run {
            adapter = homeArticleAdapter
        }
        mBinding.refreshlayout.run {
            setOnRefreshListener { SwipeRefreshLayout.OnRefreshListener {
                isFresh = true
                homeArticleAdapter.setEnableLoadMore(false)
                mViewModel.getHomeArticleList(true)
            } }
        }
        homeArticleAdapter.run {
            setOnLoadMoreListener({
                mViewModel.getHomeArticleList(false)
            }, recycleview)
            setOnItemClickListener{ _, _, position ->
                context?.toast("你点击了波泥河~~")
            }
        }
        return mBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        //mViewModel.getHomeArticleList(true)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            _uistate.observe(this@HomeFragment, Observer {
                it.showSuccess?.let {
                    homeArticleAdapter.run {
                        if (isFresh) replaceData(it.datas) else addData(it.datas)
                        isFresh = false
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                it.showError?.let {

                }
            })
        }
    }

    override fun setData(data: Any) {
    }


}