package com.xiamu.wanandroid.mvvm.view.activity

import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.baselibs.base.BaseVmActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.databinding.KnowTreeBinding
import com.xiamu.wanandroid.mvvm.viewmodel.TreeViewModel

/**
 * Created by zhengxiaobo in 2019-11-07
 */
class KnowTreeDetailActivity: BaseVmActivity<KnowTreeBinding,TreeViewModel>(){
    override fun getLayoutResId(): Int {
        return R.layout.activity_knowtree_detail
    }

    override fun initView() {
    }

    override fun initData() {

    }

}