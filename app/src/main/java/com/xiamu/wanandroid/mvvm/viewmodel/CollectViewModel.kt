package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.repository.CollectRepository
import com.xiamu.wanandroid.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-11-19
 */
class CollectViewModel: BaseViewModel(){

    private val collectRepository by lazy { CollectRepository() }

    var collectState: MutableLiveData<ArticleList> = MutableLiveData()

    fun getCollectList(page: Int){
        launch{
            var result = withContext(Dispatchers.IO){ collectRepository.getCollectList(page)}
            executeResponse(result,{collectState.value = result.data}, {mException.value = IOException(result.errorMsg) })
        }

    }
}