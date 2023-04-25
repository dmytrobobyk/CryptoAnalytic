package com.example.cryptoanalytic.presentation.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cryptoanalytic.domain.entity.Notification
import com.example.cryptoanalytic.R
import com.example.cryptoanalytic.databinding.FragmentNotificationsBinding
import com.example.cryptoanalytic.utils.listeners.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment(), OnItemClickListener<Notification> {

    val viewModel: NotificationsViewModel by viewModels()

    //TODO: Add pull to refresh
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentNotificationsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.listener = this
        return binding.root
    }

    override fun onItemClicked(item: Notification) {

    }

    fun onCreateNotificationClicked() {
        val createItemId = 0L
        val action = NotificationFragmentDirections.actionNotificationsFragmentToNotificationDetailsFragment(createItemId)
        this.findNavController().navigate(action)
    }
}