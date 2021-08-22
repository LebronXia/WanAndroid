package com.xiamu.wanandroid.mvvm.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxz.wanandroid.widget.RecyclerViewItemDecoration
import com.hss01248.dialog.StyledDialog.context
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.baselibs.util.dp2px
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.constant.AppConstant
import com.xiamu.wanandroid.databinding.HomeVieModelBinding
import com.xiamu.wanandroid.mvvm.view.activity.LoginActivity
import com.xiamu.wanandroid.mvvm.view.activity.WebActivity
import com.xiamu.wanandroid.mvvm.view.adapter.HomeArticleAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.MainHomeViewModel
import com.xiamu.wanandroid.util.GlideImageLoader
import com.xiamu.wanandroid.util.Preference
import com.xiamu.wanandroid.util.onNetError
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class HomeFragment: BaseVMFragment<MainHomeViewModel>() {

    private var isLogin by Preference(AppConstant.LOGIN_KEY, false)

    private var isFresh = true
    private var bannerView: Banner ?= null

   // lateinit var mBinding: HomeVieModelBinding

    private val homeArticleAdapter: HomeArticleAdapter by lazy{HomeArticleAdapter(R.layout.item_homelist, null)}

    override fun providerVMClass(): Class<MainHomeViewModel>? = MainHomeViewModel::class.java

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            RecyclerViewItemDecoration(it, LinearLayoutManager.VERTICAL)
        }
    }

    override fun attachLayoutRes(): Int {
        return  R.layout.fragment_home
    }

    override fun initView(view: View) {
        initBanner()
        initRecycleview()
        refreshlayout.run {
            setOnRefreshListener {refresh()}
        }
    }

    override fun lazyLoad() {
        mViewModel.getBanner()
        refresh()
    }

//    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        //ViewModel 和 binding绑定
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//        mBinding.viewModel = mViewModel
//        initBanner()
//        initRecycleview()
//        mBinding.refreshlayout.run {
//            setOnRefreshListener {refresh()}
//        }
//        return mBinding.root
//    }

    private fun initBanner() {
        bannerView = layoutInflater.inflate(R.layout.item_banner, null) as Banner?
        bannerView?.run {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(230))
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
        recycleview.run {
            adapter = homeArticleAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it)}
        }

        homeArticleAdapter.run {
            addHeaderView(bannerView)
            setOnLoadMoreListener({
                mViewModel.getHomeArticleList(false)
            }, recycleview)
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            setOnItemClickListener{ _, _, position ->
                var item = homeArticleAdapter.getItem(position)
                Intent(activity, WebActivity::class.java).run {
                    putExtra(AppConstant.EXTRA_URL_KEY, item?.link)
                    startActivity(this)
                }
            }
        }
    }

    private fun refresh(){
        isFresh = true
        homeArticleAdapter.setEnableLoadMore(false)
        mViewModel.getHomeArticleList(true)
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener{ _, view: View, position: Int ->

        if (homeArticleAdapter.data.size != 0){
            val item = homeArticleAdapter.getItem(position)

            when(view.id){
                R.id.iv_article_like -> {
                    if (isLogin){
                        item?.let {
                            val collect = item.collect
                            item.collect = !collect

                            homeArticleAdapter.setData(position, item)

                            if (collect){
                                mViewModel.unCollectArticle(item.id)
                            } else {
                                mViewModel.collectArticle(item.id)
                            }
                        }
                    } else {
                        startActivity(Intent(activity, LoginActivity::class.java))
                        activity?.toast(resources.getString(R.string.login_please))
                    }

                }
            }
        }
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

                    if(homeArticleAdapter.data.isEmpty()){
                        showPageEmpty()
                    } else {
                        showPageContent()
                    }
                }

                it.showError?.let {
                    context?.toast(it.toString())
                    showPageError()
                    homeArticleAdapter.run {
                        if (isFresh){
                            setEnableLoadMore(true)
                        } else {
                            homeArticleAdapter.loadMoreComplete()
                        }
                    }
                }
            })

            collectAction.observe(this@HomeFragment, Observer {

                it?.let {
                    activity?.toast(it.toString())
                }
            })
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        showPageError()
        activity?.onNetError(e){
            Log.d("activity", e.message?:"")
        }
    }


}