package com.xiamu.wanandroid.mvvm.viewmodel

import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.repository.KnowTreeRepository
import com.xiamu.wanandroid.mvvm.model.repository.SearchRepository

/**
 * Created by zhengxiaobo in 2019-11-14
 */
class SearchViewModel : BaseViewModel(){


    //委托
    private val searchRepository by lazy { SearchRepository() }


}