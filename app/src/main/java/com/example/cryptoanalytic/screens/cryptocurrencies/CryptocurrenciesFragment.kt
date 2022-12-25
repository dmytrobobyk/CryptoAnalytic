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
import com.example.cryptoanalytic.screens.cryptocurrencies.viewmodel.CryptocurrenciesViewModel
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import com.example.database.embeeded.Cryptocurrency
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CryptocurrenciesFragment : Fragment(), OnItemClickListener<Cryptocurrency> {

    @Inject
    lateinit var viewModel: CryptocurrenciesViewModel


    //TODO: Add pull to refresh
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: CryptocurrencyListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.cryptocurrency_list_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.clickListener = this
        return binding.root
    }

    override fun onItemClicked(item: Cryptocurrency) {
        val action = CryptocurrenciesFragmentDirections.actionCryptocurrenciesFragmentToCryptocurrencyDetails(item.dbCryptocurrency.id.lowercase())
        this.findNavController().navigate(action)
    }
}