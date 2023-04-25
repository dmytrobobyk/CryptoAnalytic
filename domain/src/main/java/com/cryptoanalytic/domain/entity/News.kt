package com.cryptoanalytic.domain.entity

data class News(
    val newsId: Long,
    val title: String?,
    val link: String?,
    val imageUrl: String?,
    val publicationDate: String?,
    val description: String?
)
