package com.xiamu.wanandroid.mvvm.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.xiamu.wanandroid.util.GlideImageLoader
import com.xiamu.wanandroid.util.onNetError
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class HomeFragment: BaseFragment<HomeVieModelBinding, MainHomeViewModel>() {

    private var isFresh = true
    private var bannerView: Banner ?= null

    private val homeArticleAdapter: HomeArticleAdapter by lazy{HomeArticleAdapter(R.layout.item_homelist, null)}

    override fun providerVMClass(): Class<MainHomeViewModel>? = MainHomeViewModel::class.java

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //ViewModel 和 binding绑定
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        mBinding.viewModel = mViewModel
        initBanner()
        initRecycleview()
        mBinding.refreshlayout.run {
            setOnRefreshListener {refresh()}
        }
        return mBinding.root
    }

    private fun initBanner() {
        bannerView = layoutInflater.inflate(R.layout.item_banner, null) as Banner?
        bannerView?.run {
            setImageLoader(GlideImageLoader())
            setIndicatorGravity(BannerConfig.CENTER)
            setOnBannerListener {
                run {
                    context.toast("hahahhaha:--" + it)
                }
            }
        }

    }

    private fun initRecycleview() {
        mBinding.recycleview.run {
            adapter = homeArticleAdapter
        }

        homeArticleAdapter.run {
            addHeaderView(bannerView)
            setOnLoadMoreListener({
                mViewModel.getHomeArticleList(false)
            }, recycleview)
            setOnItemClickListener{ _, _, position ->
                context?.toast("你点击了波泥河~~")
            }
        }
    }


    override fun initData(savedInstanceState: Bundle?) {
        mViewModel.getBanner()
        refresh()
    }

    private fun refresh(){
        isFresh = true
        homeArticleAdapter.setEnableLoadMore(false)
        mViewModel.getHomeArticleList(true)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            banners.observe(this@HomeFragment, Observer {
                val bannerImgs = ArrayList<String>()
                val bannerTitles = ArrayList<String>()
                it?.let {
                    for (banner in it){
                        bannerImgs.add(banner.imagePath)
                        bannerTitles.add(banner.title)
                    }
                }

                bannerView?.run {
                    setImages(bannerImgs)
                    setBannerTitles(bannerTitles)
                    setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    setDelayTime(3000)
                    start()
                }
            })

            _uistate.observe(this@HomeFragment, Observer {

                refreshlayout.isRefreshing = it.showLoading

                it.showSuccess?.let {
                    homeArticleAdapter.run {
                        if (isFresh) replaceData(it.datas) else addData(it.datas)
                        isFresh = false
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                it.showError?.let {
                    context?.toast(it.toString())
                    homeArticleAdapter.loadMoreComplete()

                }
            })
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        activity?.onNetError(e){
            Log.d("activity", e.message)
        }
    }

    override fun setData(data: Any) {
    }


}