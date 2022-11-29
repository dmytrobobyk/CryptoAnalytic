package com.example.cryptoanalytic.screens.cryptocurrencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.databinding.CryptocurrencyListItemBinding
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CoinMarketResponseItem


//CoinMarketCap API
//class CryptocurrenciesListAdapter : ListAdapter<LatestResponse, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
//    companion object {
//        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LatestResponse>() {
//            override fun areItemsTheSame(oldItem: LatestResponse, newItem: LatestResponse): Boolean = oldItem.id == newItem.id
//            override fun areContentsTheSame(oldItem: LatestResponse, newItem: LatestResponse): Boolean = oldItem == newItem
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val itemBinding = CryptocurrencyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return CryptocurrencyItemViewHolder(itemBinding)
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if(holder is CryptocurrencyItemViewHolder) {
//            holder.bind(getItem(position))
//        }
//    }
//
//
//    inner class CryptocurrencyItemViewHolder(private val itemBinding: CryptocurrencyListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
//        fun bind(latestResponse: LatestResponse) {
//            itemBinding.cryptoItem = latestResponse
//            itemBinding.currencyItem = latestResponse.quote?.USD
//        }
//    }
//}

//CoinGecko API
class CryptocurrenciesListAdapter : ListAdapter<CoinMarketResponseItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CoinMarketResponseItem>() {
            override fun areItemsTheSame(oldItem: CoinMarketResponseItem, newItem: CoinMarketResponseItem): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: CoinMarketResponseItem, newItem: CoinMarketResponseItem): Boolean = oldItem == newItem
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
        fun bind(item: CoinMarketResponseItem) {
            itemBinding.cryptoItem = item
            Glide.with(itemBinding.root.context).load(item.image).into(itemBinding.cryptocurrencyItemImageView)
            itemBinding.root.setOnClickListener {
                val action = CryptocurrenciesFragmentDirections.actionCryptocurrenciesFragmentToCryptocurrencyDetails(item.id)
                it.findNavController().navigate(action)
            }
        }
    }
}