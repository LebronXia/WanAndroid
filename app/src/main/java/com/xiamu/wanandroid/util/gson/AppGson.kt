package com.xiaoyu.rightone.utils.gson

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @author： xiamu
 * @time： 2023/6/9
 * @description：
 */
object AppGson {

    val gson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapterFactory(GsonDefaultAdapterFactory())
            .create()
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T?{
        try {
            return gson.fromJson(json, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T> fromJson(json: JsonElement): T? {
        return fromJson(json, T::class.java)
    }

    fun <T> fromJson(json: JsonElement, clazz: Class<T>): T? {
        try {
            return gson.fromJson(json, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun toJson(src: Any?): String{
        return gson.toJson(src)
    }

//    fun print(){
//        val content = """
//    {"title":null,"author":null,"price":56,"factory1":null,"factory2":{"name":"CCC","enable":false}}
//    """.trimIndent()
//
//        var hahaJson = Json {
//            ignoreUnknownKeys = true // JSON和数据模型字段可以不匹配
//            coerceInputValues = true // 如果JSON字段是Null则使用默认值
//        }
//
//        val book: Book = hahaJson.decodeFromString(content)  //AppGson.fromJson(content, Book::class.java)
//        Log.i("Test----zxb",hahaJson.encodeToString(book) + book?.name)
//
//    }
}