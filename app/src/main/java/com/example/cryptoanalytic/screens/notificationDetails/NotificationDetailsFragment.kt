package com.example.cryptoanalytic.screens.notificationDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cryptoanalytic.databinding.FragmentNotificationDetailsBinding
import com.example.cryptoanalytic.screens.notificationDetails.viewmodel.NotificationDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationDetailsFragment : Fragment() {

    private val args: NotificationDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var notificationDetailsViewModelFactory: NotificationDetailsViewModel.NotificationDetailsViewModelFactory
    private val viewModel: NotificationDetailsViewModel by viewModels {
        NotificationDetailsViewModel.providesFactory(
            assistedFactory = notificationDetailsViewModelFactory, notificationId = args.notificationId
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentNotificationDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.listener = this
        binding.lifecycleOwner = this
        Toast.makeText(requireContext(), "${args.notificationId}", Toast.LENGTH_LONG).show()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cryptocurrencySpinnerEntries.let {
            if(it.value.isNotEmpty()) {
                Toast.makeText(requireContext(), it.value[0], Toast.LENGTH_LONG).show()
            }
        }

    }

    fun onSaveNotificationClicked() {

    }

    fun onDeleteNotificationClicked() {

    }
}