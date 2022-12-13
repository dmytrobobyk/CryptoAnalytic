package com.example.cryptoanalytic.screens.cryptocurrencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.databinding.CryptocurrencyListFragmentBinding
import com.example.cryptoanalytic.screens.cryptocurrencies.api.response.CryptocurrencyResponseItem
import com.example.cryptoanalytic.screens.cryptocurrencies.viewmodel.CryptocurrenciesViewModel
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CryptocurrenciesFragment : Fragment(), OnItemClickListener<CryptocurrencyResponseItem> {

    @Inject
    lateinit var viewModel: CryptocurrenciesViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: CryptocurrencyListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.cryptocurrency_list_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.clickListener = this
        return binding.root
    }
    
    override fun onItemClicked(item: CryptocurrencyResponseItem) {
        val action = CryptocurrenciesFragmentDirections.actionCryptocurrenciesFragmentToCryptocurrencyDetails(item.id.lowercase())
        this.findNavController().navigate(action)
    }
}