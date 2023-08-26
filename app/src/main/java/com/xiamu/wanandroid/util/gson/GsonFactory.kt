package com.xiaoyu.rightone.utils.gson

import com.google.gson.*
import com.google.gson.internal.bind.TreeTypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author： xiamu
 * @time： 2023/6/10
 * @description：
 */
class GsonFactory(private val originGson: Gson) : TypeAdapterFactory {
    private val adapter = GsonAdapter(originGson)
    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
        return TreeTypeAdapter.newFactoryWithMatchRawType(type, adapter).create(originGson, type)
    }

    class GsonAdapter(private val gson: Gson) : JsonDeserializer<Any> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Any {
            if (json?.isJsonObject == true) {
                val obj = json.asJsonObject
                searchInObject(obj)
                return gson.fromJson(obj, typeOfT)
            }
            if (json?.isJsonArray == true) {
                val array = json.asJsonArray
                searchInArray(array)
                return gson.fromJson(array, typeOfT)
            }
            return gson.fromJson(json, typeOfT)
        }

        private fun searchInObject(obj: JsonObject) {
            val nullKey = ArrayList<String>()
            obj.keySet().forEach {
                val element = obj.get(it)
                when {
                    element.isJsonNull -> nullKey.add(it)
                    element.isJsonObject -> searchInObject(element.asJsonObject)
                    element.isJsonArray -> searchInArray(element.asJsonArray)
                }
            }
            nullKey.forEach { obj.remove(it) }
        }

        private fun searchInArray(arr: JsonArray) {
            val nullIndex = LinkedList<Int>()
            arr.forEachIndexed { index, jsonElement ->
                when {
                    jsonElement.isJsonNull -> nullIndex.addFirst(index)
                    jsonElement.isJsonObject -> searchInObject(jsonElement.asJsonObject)
                    jsonElement.isJsonArray -> searchInArray(jsonElement.asJsonArray)
                }
            }
            nullIndex.forEach { arr.remove(it) }
        }
    }
}

