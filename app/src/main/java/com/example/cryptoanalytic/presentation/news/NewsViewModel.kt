package com.example.cryptoanalytic.presentation.news

import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.data.di.qualifiers.DispatcherIOScope
import com.example.cryptoanalytic.domain.news.GetNewsUseCase
import com.example.database.entity.DbNews
import com.example.database.wrapper.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    @DispatcherIOScope private val ioDispatcher: CoroutineDispatcher
    ) : BaseViewModel() {

    private val _newsList = MutableStateFlow<List<DbNews>>(emptyList())
    val newsList: StateFlow<List<DbNews>> = _newsList.asStateFlow()

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
                }
            }
        }
    }

}