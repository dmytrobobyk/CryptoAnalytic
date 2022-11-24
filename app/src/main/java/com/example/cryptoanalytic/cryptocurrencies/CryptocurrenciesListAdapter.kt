package com.example.cryptoanalytic.cryptocurrencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoanalytic.common.responses.latest.LatestResponse
import com.example.cryptoanalytic.databinding.CryptocurrencyListItemBinding


class CryptocurrenciesListAdapter : ListAdapter<LatestResponse, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LatestResponse>() {
            override fun areItemsTheSame(oldItem: LatestResponse, newItem: LatestResponse): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: LatestResponse, newItem: LatestResponse): Boolean = oldItem == newItem
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
        fun bind(latestResponse: LatestResponse) {
            itemBinding.cryptoItem = latestResponse
            itemBinding.currencyItem = latestResponse.quote?.USD
        }
    }

}