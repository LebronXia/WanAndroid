package com.xiamu.wanandroid.mvvm.demo.navigation

import android.view.View
import androidx.navigation.fragment.navArgs
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.baselibs.util.showToast
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.viewmodel.LoginViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.MainHomeViewModel

/**
 * Created by zxb in 2021/7/25
 */
class Nav_TwoFragment : BaseVMFragment<LoginViewModel>(){

    val args by navArgs<Nav_TwoFragmentArgs>()
    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java
    override fun attachLayoutRes(): Int {
        return R.layout.fragment_nav_two
    }

    override fun initView(view: View) {
        showPageContent()
        showToast(args.size + "")

    }

    override fun lazyLoad() {
    }
}