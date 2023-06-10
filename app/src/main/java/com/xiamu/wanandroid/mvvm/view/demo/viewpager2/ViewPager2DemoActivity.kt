package com.xiamu.wanandroid.mvvm.view.demo.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.xiamu.baselibs.base.BaseModelActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.view.demo.flow.DemoViewModel
import kotlinx.android.synthetic.main.activity_viewpager2.*

class ViewPager2DemoActivity : BaseModelActivity<DemoViewModel>(){

    private lateinit var backgrounds: ArrayList<Int>
    private var fragments = ArrayList<Fragment>()
    override fun providerVMClass(): Class<DemoViewModel>? = DemoViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.activity_viewpager2

    override fun useLoadSir(): Boolean = false

    override fun initView() {

    }

    override fun initData() {
        if (!::backgrounds.isInitialized) {
            backgrounds = ArrayList<Int>()
            backgrounds.add(android.R.color.holo_blue_bright);
            backgrounds.add(android.R.color.holo_red_dark);
            backgrounds.add(android.R.color.holo_green_dark);
            backgrounds.add(android.R.color.holo_orange_light);
            backgrounds.add(android.R.color.holo_purple);
        }

        var fragments = ArrayList<Fragment>()

        for(position in 0 ..2){
            var fragment = DemoObjectFragment()
            fragment.arguments = Bundle().apply {
                putInt(ARG_OBJECT, position)
            }
            fragments.add(fragment)
        }

        var fragmentAdapter : DemoFragmentAdapter = DemoFragmentAdapter(this, fragments)

        vp2Demo.adapter = fragmentAdapter
    }


    //inner DemoAdapter

}

class LineAdapter(val data: List<Int>) : RecyclerView.Adapter<LineAdapter.LineViewHolder>() {
    inner class LineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val linerLayout = view.findViewById<LinearLayout>(R.id.line1)
        val textView = view.findViewById<TextView>(R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        return LineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_line, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        holder.linerLayout.setBackgroundColor(data[position])
        holder.textView.text = "这是第${position}个View"
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
