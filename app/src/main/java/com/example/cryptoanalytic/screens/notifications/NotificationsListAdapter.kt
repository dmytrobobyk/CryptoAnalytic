package com.example.cryptoanalytic.screens.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoanalytic.databinding.ListItemNotificationBinding
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import com.example.database.entity.DbNotification

class NotificationsListAdapter(private val clickListener: OnItemClickListener<DbNotification>) : ListAdapter<DbNotification, RecyclerView.ViewHolder>(DIFF_CALLBACK)  {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DbNotification>() {
            override fun areItemsTheSame(oldItem: DbNotification, newItem: DbNotification): Boolean = oldItem.notificationId == newItem.notificationId
            override fun areContentsTheSame(oldItem: DbNotification, newItem: DbNotification): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ListItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is NotificationItemViewHolder) {
            holder.bind(getItem(position))
        }
    }

    inner class NotificationItemViewHolder(private val itemBinding: ListItemNotificationBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: DbNotification) {
            itemBinding.notification = item
            Glide.with(itemBinding.root.context).load(item.cryptocurrencyImageUrl).into(itemBinding.notificationCryptoImage)
            itemBinding.root.setOnClickListener {
                clickListener.onItemClicked(item)
            }
        }

    }
}