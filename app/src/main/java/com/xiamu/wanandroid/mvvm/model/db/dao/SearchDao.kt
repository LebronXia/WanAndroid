package com.xiamu.wanandroid.mvvm.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.xiamu.wanandroid.mvvm.model.entry.SearchHistoryBean

/**
 * Created by zhengxiaobo in 2019-11-15
 */
@Dao
interface SearchDao{

    @Insert
    fun insertHistory(searchHistory: SearchHistoryBean)

    @Query("select * from search")
    fun getAllHistory(): List<SearchHistoryBean>

    @Query("delete from search")
    fun deleteAllHotKey()

    @Delete
    fun deleteHistory(searchHistory: SearchHistoryBean)

    @Query("select * from search where `key` = :key")
    fun queryHistoryByName(key: String): List<SearchHistoryBean>

    @Query("delete from search where `id` = :id")
    fun deleteHistoryById(id: Long)

//    @Query("delete from user")
//    void removeAllUsers();
}