package com.xiamu.wanandroid.mvvm.view.demo

import android.content.Intent
import android.util.Log
import android.view.View
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.baselibs.util.startKtxActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvi.MVIHomeActivity
import com.xiamu.wanandroid.mvvm.view.demo.TencentClassLoading.CustomViewActivity
import com.xiamu.wanandroid.mvvm.view.demo.navigation.NavigationActivity
import com.xiamu.wanandroid.mvvm.view.demo.paging3.PagingDemoActivity
import com.xiamu.wanandroid.mvvm.view.demo.recycleviewCountDown.CountRecycActivity
import com.xiamu.wanandroid.mvvm.view.demo.viewpager2.ViewPager2DemoActivity
import com.xiamu.wanandroid.mvvm.view.demo.CustomViewGroup.TheActivity
import com.xiamu.wanandroid.mvvm.view.demo.layoutmanager.LayoutManagerActivity
import com.xiaoyu.rightone.utils.gson.AppGson
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Created by zxb in 2021/1/24
 */
class DemoActivity : BaseActivity(), View.OnClickListener{

    override fun getLayoutResId(): Int  = R.layout.activity_my_demo
    override fun useLoadSir(): Boolean = false

    override fun initView() {
        toolbar.apply {
            title = "Demo"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }

    }

    private val jsonDecoder = Json {
        coerceInputValues = true // 如果JSON字段是Null则使用默认值
       // ignoreUnknownKeys = true
       // ignoreUnknownKeys = true // JSON和数据模型字段可以不匹配

    }

    override fun initData() {

        val content = """
    {"title":null,"author":null,"price":56,"factory1":null,"factory2":{"name": null, enable":false}}
    """.trimIndent()

        var userContent = "{\n" +
                "                                                                                                                    \"id\": 101470,\n" +
                "                                                                                                                    \"sex\": 2,\n" +
                "                                                                                                                    \"nickname\": \"\uD83D\uDC20\",\n" +
                "                                                                                                                    \"avatar\": \"https:\\/\\/u-cdn.myrightone.com\\/dev\\/test-jenkins-cp-sandbox-316-php\\/avatar\\/101470\\/045186f8207ed1801d71a454bc332813.jpg\",\n" +
                "                                                                                                                    \"age\": 23,\n" +
                "                                                                                                                    \"is_identity_verified\": 0,\n" +
                "                                                                                                                    \"is_avatar_verified\": 0,\n" +
                "                                                                                                                    \"guard_key\": null\n" +
                "                                                                                                         }".trimIndent()
        val book = AppGson.fromJson(userContent, Userdata::class.java)   //jsonDecoder.decodeFromString<Book>(content)  //
        Log.i("Test----zxb", AppGson.toJson(book) +  AppGson.fromJson(AppGson.toJson(book), Userdata::class.java)!!.id)
//        if (book!!.factory1 == null){
//            Log.i("Test----zxb", "factory1=为空")
//        } else{
//            Log.i("Test----zxb", "factory1=不为空")
//        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.tv_layoutmanager -> {
                startActivity(Intent(this, LayoutManagerActivity::class.java))
            }
            R.id.tv_viewGroup -> {
                startActivity(Intent(this, TheActivity::class.java))
            }
            R.id.tv_navigation -> {
                startActivity(Intent(this, NavigationActivity::class.java))
            }
            R.id.tv_flow -> {
                startKtxActivity<TwoViewActivity>()
                //startKtxActivity<ButtonTextActivity>()
            }
            R.id.tv_paging3 -> {
                startKtxActivity<PagingDemoActivity>()
            }

            R.id.tv_count_recycle -> {
                startKtxActivity<CountRecycActivity>()
            }

            R.id.tv_viewpager2 -> {
                startKtxActivity<ViewPager2DemoActivity>()
            }

            R.id.tecent_class_view -> {
                startKtxActivity<CustomViewActivity>()
            }

            R.id.mvi_home -> {
                startKtxActivity<MVIHomeActivity>()
            }

        }
    }

//    inline fun <reified T: Activity> Activity.startActivity(){
//        val intent = Intent(this, javaClass<T>())
//        startActivity(intent)
//    }
}