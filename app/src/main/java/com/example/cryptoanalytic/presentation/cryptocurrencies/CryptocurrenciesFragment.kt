package com.example.cryptoanalytic.presentation.cryptocurrencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.databinding.CryptocurrencyListFragmentBinding
import com.example.cryptoanalytic.utils.listeners.OnFavoriteClickListener
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import com.example.database.embeeded.Cryptocurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptocurrenciesFragment : Fragment(), OnItemClickListener<Cryptocurrency>, OnFavoriteClickListener<String> {

//    @Inject
//    lateinit var viewModel: CryptocurrenciesViewModel
    val viewModel: CryptocurrenciesViewModel by viewModels()


    //TODO: Add pull to refresh
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: CryptocurrencyListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.cryptocurrency_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.listener = this
        return binding.root
    }

    override fun onItemClicked(item: Cryptocurrency) {
        val action = CryptocurrenciesFragmentDirections.actionCryptocurrenciesFragmentToCryptocurrencyDetails(item.dbCryptocurrency.id.lowercase())
        this.findNavController().navigate(action)
    }

    override fun onFavoriteClicked(itemId: String, state: Boolean) {
        viewModel.saveFavoriteCryptocurrencyState(itemId, state)
    }
}