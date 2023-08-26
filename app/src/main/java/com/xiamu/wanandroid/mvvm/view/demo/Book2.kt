package com.xiamu.wanandroid.mvvm.view.demo


import com.google.gson.annotations.SerializedName

data class Book2(
    @SerializedName("name")
    var name: String,
    @SerializedName("age")
    var age: Int,
    @SerializedName("author")
    var author: List<String>,
    @SerializedName("factory1")
    var factory1: Factory2 = Factory2(),
    @SerializedName("factory2")
    var factory2: Factory2 = Factory2(),
    @SerializedName("price")
    var price: String = "",
    @SerializedName("title")
    var title: String = ""
)

data class Factory2(
    @SerializedName("enable")
        var enable: Boolean = false,
    @SerializedName("name")
    var name: String = ""
)


data class Userdata(
    @SerializedName("age")
    var age: Int = 0,
    @SerializedName("avatar")
    var avatar: String = "",
    @SerializedName("guard_key")
    var guardKey: Any = Any(),
    @SerializedName("id")
    var id: String = "",
    @SerializedName("is_avatar_verified")
    var isAvatarVerified: Int = 0,
    @SerializedName("is_identity_verified")
    var isIdentityVerified: Int = 0,
    @SerializedName("nickname")
    var nickname: String = "",
    @SerializedName("sex")
    var sex: Int = 0
)