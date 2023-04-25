package com.example.cryptoanalytic.presentation.news

import androidx.lifecycle.viewModelScope
import com.cryptoanalytic.domain.entity.News
import com.cryptoanalytic.domain.usecases.news.GetNewsUseCase
import com.cryptoanalytic.domain.wrapper.Result
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.di.DispatcherIOScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    @DispatcherIOScope private val ioDispatcher: CoroutineDispatcher
    ) : BaseViewModel() {

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            getNewsUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        result.data?.let {
                            _newsList.value = it
                        }

                    }
                    is Result.Finish -> {

                    }
                    else -> {}
                }
            }
        }
    }

}