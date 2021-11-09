package com.pince.compose_app.model.db

import androidx.room.*
import com.pince.compose_app.WanAndApplication
import kotlinx.coroutines.flow.Flow

/**
 * Created by zxb in 2021/11/8
 */

//定义entity
@Entity(tableName = "search")
data class SearchHistoryBean(
    //搜索内容
    @ColumnInfo(name = "key")
    val key: String = ""
){
    @Ignore
    constructor(): this("")

    //自增
    @PrimaryKey(autoGenerate = true)
    //主健
    var id: Long = 0
}

//定义Dao层
@Dao
interface SearchDao{

    //增加某一项
    @Insert
    fun insertHistory(searchHistory: SearchHistoryBean)

    //查找全部
    @Query("select * from search")
    fun getAllHistory(): Flow<List<SearchHistoryBean>>

    //查找全部, 不同数据返回
    @Query("select * from search")
    fun getSearchHistoryAll(): List<SearchHistoryBean>

    //查询该值是否已经存在
    @Query("select count(*) from search where `key` = :key limit 1" )
    fun querySearchHistoryCount(key: String): Int

    //删除全部
    @Query("delete from search")
    fun deleteAllHotKey()

    //删除某一项
    @Delete
    fun deleteHistory(searchHistory: SearchHistoryBean)

    //根据name查询某一项
    @Query("select * from search where `key` = :key")
    fun queryHistoryByName(key: String): Flow<List<SearchHistoryBean>>

    //根据id删除某一项
    @Query("delete from search where `id` = :id")
    fun deleteHistoryById(id: Long)
}

//定义数据库
@Database(entities = [SearchHistoryBean::class], version = 1, exportSchema = false)
abstract class SearchHistoryDB : RoomDatabase(){
    abstract fun searchDao(): SearchDao

    companion object{
        var DATABASE_NAME = "search_history_db"

        //保证不同线程对这个共享变量进行操作的可见性，并且禁止进行指令重排序.
        @Volatile
        private var instance: SearchHistoryDB ?= null
            get() {
                if (field == null){
                    field = Room.databaseBuilder(WanAndApplication.CONTEXT, SearchHistoryDB::class.java, DATABASE_NAME).allowMainThreadQueries().build()
                }
                return field
            }

        @Synchronized
        fun get(): SearchHistoryDB{
            return instance!!
        }

        fun destoryInstance(){
            instance = null
        }
    }

}

