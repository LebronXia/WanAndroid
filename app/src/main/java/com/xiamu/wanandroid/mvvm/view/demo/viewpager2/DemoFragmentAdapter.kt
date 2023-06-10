package com.xiamu.wanandroid.mvvm.view.demo.viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DemoFragmentAdapter(fragmentActivity: FragmentActivity, val data: ArrayList<Fragment>): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        return data[position]
    }

    fun addFragment(fragment: Fragment){
        data.add(fragment)
        notifyDataSetChanged()
    }

    fun removeFragment(){
        if (data.size > 0){
            data.removeAt(data.size - 1)
            notifyDataSetChanged()
        }

    }
}