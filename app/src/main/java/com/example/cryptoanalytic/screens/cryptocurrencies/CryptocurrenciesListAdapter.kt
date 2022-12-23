package com.example.cryptoanalytic.screens.cryptocurrencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoanalytic.databinding.CryptocurrencyListItemBinding
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import com.example.database.embeeded.Cryptocurrency
import com.example.database.entity.DbCryptocurrency

//class CryptocurrenciesListAdapter(private val listener: OnItemClickListener<CryptocurrencyResponseItem>) : ListAdapter<CryptocurrencyResponseItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
class CryptocurrenciesListAdapter(private val listener: OnItemClickListener<DbCryptocurrency>) : ListAdapter<DbCryptocurrency, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DbCryptocurrency>() {
            override fun areItemsTheSame(oldItem: DbCryptocurrency, newItem: DbCryptocurrency): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: DbCryptocurrency, newItem: DbCryptocurrency): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = CryptocurrencyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptocurrencyItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CryptocurrencyItemViewHolder) {
            holder.bind(getItem(position))
        }
    }


    inner class CryptocurrencyItemViewHolder(private val itemBinding: CryptocurrencyListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: DbCryptocurrency) {
            itemBinding.cryptoItem = item
            Glide.with(itemBinding.root.context).load(item.image).into(itemBinding.cryptocurrencyItemImageView)
            itemBinding.root.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}