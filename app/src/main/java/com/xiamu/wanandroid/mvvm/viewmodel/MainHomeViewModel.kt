package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.entry.Banner
import com.xiamu.wanandroid.mvvm.model.repository.MainHomeModel
import com.xiamu.wanandroid.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class MainHomeViewModel: CollectViewModel() {

    //委托
    private val homeModel by lazy { MainHomeModel() }
    private var currentPage = 0
    //val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val _uistate: MutableLiveData<UiModel<ArticleList>> = MutableLiveData()
    val banners: MutableLiveData<List<Banner>> = MutableLiveData()

    fun getBanner(){
        launch {
            val result = withContext(Dispatchers.IO){ homeModel.getBanner() }
            executeResponse(result,{banners.value = result.data}, {mException.value = IOException(result.errorMsg)})
        }
    }

    fun getHomeArticleList(isRefresh: Boolean = false){

        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main){
                emitUiState(true)
            }
            if (isRefresh) currentPage = 0
            val result = homeModel.getArticleList(currentPage)
            withContext(Dispatchers.Main){
                if (result is Result.Success){
                    //mArticleList.value = result.data
                    val articleList = result.data
                    if (articleList!!.offset >= articleList.total){
                        return@withContext
                    }
                    currentPage++
                    emitUiState(showLoading = false, showSuccess = result.data)


                } else if (result is Result.Error){
                    emitUiState(showLoading = false, showError = result.exception.message)
                }
            }
        }
    }

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String ?= null,
        showSuccess: ArticleList ?= null
    ){
        val uiModel = UiModel(showLoading, showError, showSuccess)
        _uistate.value = uiModel
    }

}