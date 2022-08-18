package com.example.cryptoanalytic.utils.extensions

import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * Collect items from the specified [Flow] only when fragment is at least in STARTED state.
 */
fun <T> Fragment.collectFlow(flow: Flow<T>, onCollect: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        // this coroutine is cancelled in onDestroyView
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            // this coroutine is launched every time when onStart is called;
            // collecting is cancelled in onStop
            flow.collect {
                onCollect(it)
            }
        }
    }
}
