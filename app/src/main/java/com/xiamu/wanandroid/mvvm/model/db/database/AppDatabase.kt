package com.xiamu.wanandroid.mvvm.model.db.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xiamu.wanandroid.app.WanAndApplication
import com.xiamu.wanandroid.mvvm.model.db.dao.SearchDao
import com.xiamu.wanandroid.mvvm.model.entry.SearchHistoryBean

/**
 * Created by zhengxiaobo in 2019-11-15
 */
@Database(entities = [SearchHistoryBean::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun searchDao(): SearchDao

        companion object{
            var DATABASE_NAME = "db"
            private var instance: AppDatabase ?= null
                get() {
                    if (field == null){
                        field = Room.databaseBuilder(WanAndApplication.CONTEXT, AppDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().build()
                    }
                    return field
                }

            @Synchronized
            fun get(): AppDatabase{
                return instance!!
            }

            fun destoryInstance(){
                instance = null
            }
        }

}