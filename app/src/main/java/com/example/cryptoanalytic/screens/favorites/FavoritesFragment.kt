package com.example.cryptoanalytic.screens.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.databinding.FragmentFavoritesBinding
import com.example.cryptoanalytic.screens.favorites.viewmodel.FavoritesViewModel
import com.example.cryptoanalytic.utils.listeners.OnFavoriteClickListener
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import com.example.database.embeeded.Cryptocurrency
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment(), OnItemClickListener<Cryptocurrency>,
    OnFavoriteClickListener<Cryptocurrency> {

    @Inject
    lateinit var viewModel: FavoritesViewModel


    //TODO: Add pull to refresh
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentFavoritesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.listener = this
        return binding.root
    }

    override fun onItemClicked(item: Cryptocurrency) {

    }

    override fun onFavoriteClicked(item: Cryptocurrency) {
        viewModel.removeCryptocurrencyFromFavorite(item)
    }
}