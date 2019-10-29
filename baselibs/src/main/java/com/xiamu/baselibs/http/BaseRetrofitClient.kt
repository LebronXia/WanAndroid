package com.xiamu.baselibs.http

import com.xiamu.baselibs.BuildConfig
import com.xiamu.baselibs.constant.HttpConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by zhengxiaobo in 2019-10-28
 */
abstract class BaseRetrofitClient {

    private val client: OkHttpClient
    get() {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
//
//        if (BuildConfig.DEBUG)
//            var printHttpLogLevel :


        builder.addNetworkInterceptor(logging)
            .connectTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)

        handleBuilder(builder)
        return builder.build()
    }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S{
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build().create(serviceClass)

    }
}