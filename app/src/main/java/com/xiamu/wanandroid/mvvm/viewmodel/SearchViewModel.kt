package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.app.WanAndApplication
import com.xiamu.wanandroid.mvvm.model.db.dao.SearchDao
import com.xiamu.wanandroid.mvvm.model.db.database.AppDatabase
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.entry.HotKey
import com.xiamu.wanandroid.mvvm.model.entry.SearchHistoryBean
import com.xiamu.wanandroid.mvvm.model.repository.KnowTreeRepository
import com.xiamu.wanandroid.mvvm.model.repository.SearchRepository
import com.xiamu.wanandroid.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-11-14
 */
class SearchViewModel : BaseViewModel(){


    //委托
    private val searchRepository by lazy { SearchRepository() }
    //private val searchDao by lazy { SearchDao }
    //创建数据库
    private val database = AppDatabase.get()

    var mHotKeyState: MutableLiveData<List<HotKey>> = MutableLiveData()
    var mSearchResultState: MutableLiveData<ArticleList> = MutableLiveData()

    var mSearchHistoryState: MutableLiveData<List<SearchHistoryBean>> = MutableLiveData()

    fun getSearchHotkey(){
        launch {
            val result = withContext(Dispatchers.IO){ searchRepository.getSearchHotKey() }
            executeResponse(result,{mHotKeyState.value = result.data}, {mException.value = IOException(result.errorMsg) })
        }
    }

    fun getSearchResult(page: Int, key: String){
        launch {
            val result = withContext(Dispatchers.IO){ searchRepository.getSearchResult(page, key) }
            executeResponse(result,{mSearchResultState.value = result.data}, {mException.value = IOException(result.errorMsg) })
        }
    }

    fun saveSearchHistory(searchHistory: SearchHistoryBean){
        launch {
            withContext(Dispatchers.IO){
                val beans = database.searchDao().queryHistoryByName(searchHistory.key)
                if (beans.isEmpty()){
                    database.searchDao().insertHistory(searchHistory)
                } else {
                    database.searchDao().deleteHistoryById(beans[0].id)
                    database.searchDao().insertHistory(searchHistory)
                }
            }
        }
    }

    fun getAllHistory(){
        launch {
            val result = withContext(Dispatchers.IO){database.searchDao().getAllHistory() }
            withContext(Dispatchers.Main){mSearchHistoryState.value = result}
        }
    }

    fun clearAllHistory(){
        launch {
            withContext(Dispatchers.IO){
                database.searchDao().deleteAllHotKey()
            }
        }

    }

    fun removeHistory(searchHistory: SearchHistoryBean){
        launch {
            withContext(Dispatchers.IO){
                database.searchDao().deleteHistory(searchHistory)
            }
        }

    }


}