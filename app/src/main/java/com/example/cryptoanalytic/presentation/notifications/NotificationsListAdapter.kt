package com.example.cryptoanalytic.presentation.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoanalytic.databinding.ListItemNotificationBinding
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import com.cryptoanalytic.domain.entity.Notification

class NotificationsListAdapter(private val clickListener: OnItemClickListener<Notification>) : ListAdapter<Notification, RecyclerView.ViewHolder>(DIFF_CALLBACK)  {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean = oldItem.notificationId == newItem.notificationId
            override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean = oldItem == newItem
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
        fun bind(item: Notification) {
            itemBinding.notification = item
            Glide.with(itemBinding.root.context).load(item.cryptocurrencyImageUrl).into(itemBinding.notificationCryptoImage)
            itemBinding.root.setOnClickListener {
                clickListener.onItemClicked(item)
            }
        }

    }
}