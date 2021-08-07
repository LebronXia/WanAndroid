package com.xiamu.wanandroid.mvvm.demo.navigation

import android.view.View
import androidx.navigation.fragment.findNavController
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_nav_home.*

/**
 * Created by zxb in 2021/7/25
 */
class Nav_HomeFragment: BaseVMFragment<LoginViewModel>() {
    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_nav_home
    }

    override fun initView(view: View) {
        showPageContent()
        btn_nav_ordinary.setOnClickListener {
            findNavController().navigate(R.id.action_nav_HomeFragment_to_nav_TwoFragment)
        }

        btn_nav__canshu_ordinary.setOnClickListener{
            val action = Nav_HomeFragmentDirections.actionNavHomeFragmentToNavTwoFragment("daad")
            findNavController().navigate(action)
        }

    }

    override fun lazyLoad() {
    }


}