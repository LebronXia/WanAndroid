package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeDetailBean
import com.xiamu.wanandroid.mvvm.model.repository.WxArticleRepository
import com.xiamu.wanandroid.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-11-09
 */
class WxArticleViewModel : BaseViewModel(){

    private val wxArticleReop by lazy { WxArticleRepository() }
    private val wxArticleDetailRepo by lazy { WxArticleRepository() }

    var wxArticleState : MutableLiveData<List<TreeBean>> = MutableLiveData()

    var wxArticleDetailState:  MutableLiveData<TreeDetailBean> = MutableLiveData()

    fun getWxArticleData(){
        launch {
            val result = withContext(Dispatchers.IO){ wxArticleReop.getWxArticles() }
            executeResponse(result,{wxArticleState.value = result.data}, {mException.value = IOException(result.errorMsg) })
        }
    }

    fun getTreeDetailData(currentPage: Int, id: Int){
        launch {

            val result = withContext(Dispatchers.IO){
                wxArticleDetailRepo.getWxArticleDetail(currentPage, id)
            }

            executeResponse(result,
                {wxArticleDetailState.value = result.data},
                {mException.value = IOException(result.errorMsg)
            })
        }
    }

}