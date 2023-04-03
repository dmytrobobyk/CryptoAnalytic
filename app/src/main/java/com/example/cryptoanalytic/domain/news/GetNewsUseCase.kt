package com.example.cryptoanalytic.domain.news

import com.example.cryptoanalytic.data.repository.abs.NewsRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend operator fun invoke() =
        flow {
            repository.getNews().collect { emit(it) }
        }
}