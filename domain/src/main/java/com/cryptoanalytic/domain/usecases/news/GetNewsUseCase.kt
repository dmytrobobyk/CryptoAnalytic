package com.cryptoanalytic.domain.usecases.news

import com.cryptoanalytic.domain.repository.NewsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend operator fun invoke() =
        flow {
            repository.getNews().collect { emit(it) }
        }
}