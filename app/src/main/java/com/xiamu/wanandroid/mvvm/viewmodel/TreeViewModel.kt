package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeDetailBean
import com.xiamu.wanandroid.mvvm.model.repository.KnowTreeRepository
import com.xiamu.wanandroid.mvvm.model.repository.MainHomeModel
import com.xiamu.wanandroid.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-11-06
 */
class TreeViewModel : BaseViewModel(){

    //委托
    private val treeRepository by lazy { KnowTreeRepository() }
    var treeBeanState : MutableLiveData<List<TreeBean>> = MutableLiveData()

    var treeDetailState: MutableLiveData<TreeDetailBean> = MutableLiveData()

    fun getTreeData(){
        launch{
            val result = withContext(Dispatchers.IO){ treeRepository.getTreeData() }
            executeResponse(result,{treeBeanState.value = result.data}, {mException.value = IOException(result.errorMsg) })
        }
    }

    fun getTreeDetailData(id: Int){
        launch {
            val result = withContext(Dispatchers.IO){ treeRepository.getTreeListData(id)}
            executeResponse(result, {treeDetailState.value = result.data}, {mException.value = IOException(result.errorMsg) })
        }
    }


}