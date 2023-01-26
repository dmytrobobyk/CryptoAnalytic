package com.example.cryptoanalytic.screens.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.databinding.FragmentNotificationsBinding
import com.example.cryptoanalytic.screens.cryptocurrencies.CryptocurrenciesFragmentDirections
import com.example.cryptoanalytic.screens.notifications.viewmodel.NotificationsViewModel
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import com.example.database.entity.DbNotification
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment : Fragment(), OnItemClickListener<DbNotification> {

    @Inject
    lateinit var viewModel: NotificationsViewModel

    //TODO: Add pull to refresh
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentNotificationsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.listener = this
        return binding.root
    }

    override fun onItemClicked(item: DbNotification) {

    }

    fun onCreateNotificationClicked() {
        val createItemId = 0L
        val action = NotificationFragmentDirections.actionNotificationsFragmentToNotificationDetailsFragment(createItemId)
        this.findNavController().navigate(action)
    }
}