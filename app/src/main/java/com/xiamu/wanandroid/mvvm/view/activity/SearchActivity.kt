package com.xiamu.wanandroid.mvvm.view.activity

import android.content.Intent
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cxz.wanandroid.widget.RecyclerViewItemDecoration
import com.xiamu.baselibs.base.BaseVmActivity
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.constant.AppConstant
import com.xiamu.wanandroid.databinding.SearchViewModelBinding
import com.xiamu.wanandroid.mvvm.model.entry.HotKey
import com.xiamu.wanandroid.mvvm.model.entry.SearchHistoryBean
import com.xiamu.wanandroid.mvvm.view.adapter.SearchHistoryAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.LoginViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.SearchViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*

/**
 * Created by zhengxiaobo in 2019-11-14
 */
class SearchActivity : BaseVmActivity<SearchViewModelBinding, SearchViewModel>(){

    private val datas = mutableListOf<SearchHistoryBean>()

    private var mHotKeys = mutableListOf<HotKey>()

    private val searchHistoryAdapter by lazy { SearchHistoryAdapter(datas) }

    private val recyclerViewItemDecoration by lazy {
        RecyclerViewItemDecoration(this)
    }

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java

    override fun getLayoutResId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {

        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }

        rv_history_search.run {
            adapter = searchHistoryAdapter
            itemAnimator = DefaultItemAnimator()
        }

        searchHistoryAdapter.run {
            onItemClickListener = this@SearchActivity.OnItemClickListener
            onItemChildClickListener = onChildClickListener
        }

        tv_clear_search.setOnClickListener {
            datas.clear()
            searchHistoryAdapter.replaceData(datas)
            mViewModel.clearAllHistory()
        }

        fl_hot_search.setOnTagClickListener{ view: View, position: Int, flowLayout: FlowLayout ->

            if (mHotKeys.size > 0){
                val mHotKey = mHotKeys[position]
                gotoSearchList(mHotKey.name)
                true
            }
            false
        }

    }

    override fun initData() {
        mViewModel.getSearchHotkey()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getAllHistory()
    }

    override fun startObserve() {
        super.startObserve()

        mViewModel.mHotKeyState.observe(this, Observer {

            it?.let {

                mHotKeys.addAll(it)
                fl_hot_search.adapter = object : TagAdapter<HotKey>(it){
                    override fun getView(parent: FlowLayout?, position: Int, item: HotKey?): View? {
                        item ?: return null

                        var mTvTag: TextView =
                            layoutInflater.inflate(R.layout.item_flow_text, fl_hot_search, false) as TextView
                        mTvTag.setText(item.name)
                        return mTvTag
                    }
                }
            }
        })

        mViewModel.mSearchHistoryState.observe(this, Observer {
            it?.let {
                searchHistoryAdapter.replaceData(it)
            }
        })
    }

    fun gotoSearchList(query: String){
        mViewModel.saveSearchHistory(SearchHistoryBean(query))
        Intent(this@SearchActivity, SearchResultActivity::class.java).run {
            putExtra(AppConstant.EXTRA_SEARCH_KEY, query)
            startActivity(this)
        }
        toast("保存" + query)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.onActionViewExpanded()
        searchView.queryHint = getString(R.string.search_tint)
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.isSubmitButtonEnabled = true
        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.isAccessible = true
            val mGoButton = field.get(searchView) as ImageView
            mGoButton.setImageResource(R.drawable.ic_search_white_24dp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onCreateOptionsMenu(menu)
    }

    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {

            gotoSearchList(query.toString())
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

    }

    private val OnItemClickListener = BaseQuickAdapter.OnItemClickListener{ _ ,view: View, i: Int ->

        toast("哈哈哈哈")

    }

    private val onChildClickListener = BaseQuickAdapter.OnItemChildClickListener{ _, view: View, position: Int ->

        if (searchHistoryAdapter.data.size != 0){
            val item = searchHistoryAdapter.getItem(position)

            when(view.id){
                R.id.iv_delete -> {
                    item?.let { mViewModel.removeHistory(it) }
                    searchHistoryAdapter.remove(position)
                }
            }
        }
    }

}