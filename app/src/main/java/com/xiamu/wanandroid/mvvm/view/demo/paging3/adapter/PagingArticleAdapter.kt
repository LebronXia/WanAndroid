package com.xiamu.wanandroid.mvvm.view.demo.paging3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.model.entry.Article

/**
 * Created by zxb in 2021/8/16
 */
class PagingArticleAdapter: PagingDataAdapter<Article, PagingArticleAdapter.ViewHolder>(COMPARATOR) {

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_article_author)
        val title: TextView = itemView.findViewById(R.id.tv_article_title)
        val chapterName: TextView = itemView.findViewById(R.id.tv_article_chapterName)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        if (repo != null) {
            holder.name.text = repo.shareUser
            holder.title.text = repo.title
            holder.chapterName.text = repo.superChapterName + repo.chapterName
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paging_homeliist, parent, false)
        return ViewHolder(view)
    }

}