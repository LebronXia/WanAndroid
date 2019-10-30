package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.repository.MainHomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class MainHomeViewModel: BaseViewModel() {

    //委托
    private val homeModel by lazy { MainHomeModel() }
    private var currentPage = 0
    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()

    fun getHomeArticleList(isRefresh: Boolean = false){

        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main){}
            if (isRefresh) currentPage = 0;
            val result = homeModel.getArticleList(currentPage)
            withContext(Dispatchers.Main){
                if (result is Result.Success){
                    mArticleList.value = result.data
                } else if (result is Result.Error){

                }
            }

        }

    }

}