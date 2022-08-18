package com.example.cryptoanalytic.cryptocurrencies.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoanalytic.common.responses.latest.LatestResponse
import com.example.cryptoanalytic.cryptocurrencies.CryptocurrenciesListAdapter
import kotlinx.coroutines.flow.StateFlow


object BindingAdapters {

    @BindingAdapter("cryptocurrencyItems")
    @JvmStatic
//    fun bindCryptocurrencyItems(recyclerView: RecyclerView, cryptocurrencyItems: StateFlow<List<LatestResponse>>?, onClick: (LatestResponse) -> Unit) {
    fun bindCryptocurrencyItems(recyclerView: RecyclerView, cryptocurrencyItems: StateFlow<List<LatestResponse>>?) {
        recyclerView.adapter ?: run {
            recyclerView.adapter = CryptocurrenciesListAdapter()
        }
        (recyclerView.adapter as CryptocurrenciesListAdapter).submitList(cryptocurrencyItems?.value?.toMutableList())
    }
}