package com.example.cryptoanalytic.screens.cryptocurrencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoanalytic.databinding.CryptocurrencyListItemBinding
import com.example.cryptoanalytic.utils.listeners.OnFavoriteClickListener
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import com.example.database.embeeded.Cryptocurrency

class CryptocurrenciesListAdapter(
    private val clickListener: OnItemClickListener<Cryptocurrency>,
    private val favoriteClickListener: OnFavoriteClickListener<Cryptocurrency>) : ListAdapter<Cryptocurrency, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cryptocurrency>() {
            override fun areItemsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean = oldItem.dbCryptocurrency.id == newItem.dbCryptocurrency.id
            override fun areContentsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean = oldItem == newItem
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
        fun bind(item: Cryptocurrency) {
            itemBinding.cryptoItem = item.dbCryptocurrency
            Glide.with(itemBinding.root.context).load(item.dbCryptocurrency.image).into(itemBinding.cryptocurrencyItemImageView)
            itemBinding.root.setOnClickListener {
                clickListener.onItemClicked(item)
            }
            itemBinding.addFavoriteImageView.setOnClickListener {
                favoriteClickListener.onFavoriteClicked(item)
            }
        }
    }
}