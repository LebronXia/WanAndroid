package com.xiamu.wanandroid.mvvm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xiamu.wanandroid.R

/**
 * Created by zhengxiaobo in 2019-10-30
 */
class KnowledgeTreeFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_knowledgetree, null)
    }
}