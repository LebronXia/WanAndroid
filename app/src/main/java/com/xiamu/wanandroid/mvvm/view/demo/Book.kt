package com.xiamu.wanandroid.mvvm.view.demo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    @SerialName("author")
    var author: List<String> = listOf(),
    @SerialName("factory1")
    var factory1: Factory1 = Factory1(),
    @SerialName("factory2")
    var factory2: Factory1 = Factory1(),
    @SerialName("name")
    var name: String = "哈哈哈哈",
//    @SerialName("title")
//    var title: String = "",
    @SerialName("price")
    var price: Double = 0.0
)

@Serializable
data class Factory1(
    @SerialName("enable")
    var enable: Boolean = false,
    @SerialName("location")
    var location: String = "",
    @SerialName("name")
    var name: String = ""
)