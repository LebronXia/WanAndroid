package com.pince.compose_app.model.db

import com.pince.compose_app.model.db.SearchHistoryHelper.insertSearchHistory
import com.pince.compose_app.model.entry.BannerData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by zxb in 2021/11/8
 */
object SearchHistoryHelper {

    //查询全部数据
    fun getSearchHistoryAll(): Flow<List<SearchHistoryBean>> =
        SearchHistoryDB.get().searchDao().getAllHistory()

    //删除全部历史数据
    fun CoroutineScope.clearAllHistory(){
        launch(Dispatchers.IO) {
            SearchHistoryDB.get().searchDao().deleteAllHotKey()
        }
    }

    //删除历史数据某一项
    fun CoroutineScope.deleteHistory(searchHistory: SearchHistoryBean){
        launch {
            withContext(Dispatchers.IO){
                SearchHistoryDB.get().searchDao().deleteHistory(searchHistory)
            }
        }
    }

    //新增搜索历史数据
    fun CoroutineScope.insertSearchHistory(searchHistory: SearchHistoryBean){
        launch (Dispatchers.IO){
            val count = SearchHistoryDB.get().searchDao().querySearchHistoryCount(searchHistory.key)

            if (count == 1) return@launch

            val check = SearchHistoryDB.get().searchDao().getSearchHistoryAll() ?: return@launch

            if (check.size >= 10){
                //删除第一项
                check.first().id?.let {SearchHistoryDB.get().searchDao().deleteHistoryById(it)  }
            }

            SearchHistoryDB.get().searchDao().insertHistory(searchHistory)

        }
    }
}