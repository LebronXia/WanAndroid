package com.xiamu.wanandroid.mvvm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.xiamu.baselibs.base.BaseFragment
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.databinding.HomeVieModelBinding
import com.xiamu.wanandroid.mvvm.viewmodel.MainHomeViewModel

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class HomeFragment: BaseFragment<HomeVieModelBinding, MainHomeViewModel>() {

    private lateinit var mHomeViewModel: MainHomeViewModel

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //ViewModel 和 binding绑定
        mHomeViewModel = ViewModelProviders.of(this).get(MainHomeViewModel::class.java)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        mBinding.viewModel = mHomeViewModel

        return mBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        mHomeViewModel.getHomeArticleList(true)
    }

    override fun startObserve() {
        super.startObserve()
        mHomeViewModel.apply {
            mArticleList.observe(this@HomeFragment, Observer {
                it?.let {

                }
            })
        }
    }

    override fun setData(data: Any) {
    }


}