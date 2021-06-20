package com.xiamu.wanandroid.mvvm.view.widget.view.dimPle

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.xiamu.wanandroid.R
import kotlinx.android.synthetic.main.activity_dimple_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by zhengxiaobo in 2020/9/21
 */
class DimPleActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dimple_view)
        lifecycleScope.launch {
            loadImage()
        }
    }

    private suspend fun loadImage(){
        withContext(Dispatchers.IO){
            Glide.with(this@DimPleActivity)
                .load(R.drawable.ic_mycat)
                .circleCrop()
                .into(object : ImageViewTarget<Drawable>(music_avatar){
                    override fun setResource(resource: Drawable?) {
                        music_avatar.setImageDrawable(resource)
                    }
                });
        }

    }

}