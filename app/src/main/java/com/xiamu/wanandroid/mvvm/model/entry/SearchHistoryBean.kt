package com.xiamu.wanandroid.mvvm.model.entry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by zhengxiaobo in 2019-11-15
 */
@Entity(tableName = "search")
data class SearchHistoryBean(
    @ColumnInfo(name = "key")
    val key: String = ""
){
    @Ignore
    constructor(): this("")

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}