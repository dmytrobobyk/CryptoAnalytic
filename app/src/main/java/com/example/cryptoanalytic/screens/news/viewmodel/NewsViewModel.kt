package com.example.cryptoanalytic.screens.news.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.cryptoanalytic.common.BaseViewModel
import com.example.cryptoanalytic.common.di.DispatcherIOScope
import com.example.cryptoanalytic.screens.news.repository.NewsRepository
import com.example.database.entity.DbNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.database.wrapper.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect


@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository, @DispatcherIOScope private val ioDispatcher: CoroutineDispatcher) : BaseViewModel() {

    private val _newsList = MutableStateFlow<List<DbNews>>(emptyList())
    val newsList: StateFlow<List<DbNews>> = _newsList.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            repository.getNews().collect { result ->
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