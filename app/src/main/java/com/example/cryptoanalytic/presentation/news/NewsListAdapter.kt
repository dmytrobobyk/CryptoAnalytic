package com.example.cryptoanalytic.presentation.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cryptoanalytic.domain.entity.News
import com.example.cryptoanalytic.databinding.ListItemNewsBinding


class NewsListAdapter : ListAdapter<News, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem.newsId == newItem.newsId
            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ListItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return NewsItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsItemViewHolder) {
            holder.bind(getItem(position))
        }
    }

    inner class NewsItemViewHolder(private val itemBinding: ListItemNewsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: News) {
            itemBinding.news = item
            itemBinding.root.setOnClickListener {
                itemBinding.root.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))
            }
        }

    }
}