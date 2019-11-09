package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeDetailBean
import com.xiamu.wanandroid.mvvm.model.repository.KnowTreeRepository
import com.xiamu.wanandroid.mvvm.model.repository.ProjectRepository
import com.xiamu.wanandroid.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-11-09
 */
class ProjectViewModel : BaseViewModel(){

    //委托
    private val projectRepository by lazy { ProjectRepository() }
    var projectState : MutableLiveData<List<TreeBean>> = MutableLiveData()

    var projectListState: MutableLiveData<TreeDetailBean> = MutableLiveData()

    fun getProjecctTree(){
        launch{
            val result = withContext(Dispatchers.IO){ projectRepository.getProjectTree() }
            executeResponse(result,{projectState.value = result.data}, {mException.value = IOException(result.errorMsg) })
        }
    }

    fun getProjectDetailData(currentPage: Int, id: Int){
        launch {

            val result = withContext(Dispatchers.IO){
                projectRepository.getProjectList(currentPage, id)
            }

            executeResponse(result,
                {projectListState.value = result.data},
                {mException.value = IOException(result.errorMsg)
                })
        }
    }

}