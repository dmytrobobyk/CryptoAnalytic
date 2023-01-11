package com.example.cryptoanalytic.utils.listeners

interface OnFavoriteClickListener<T> {
    fun onFavoriteClicked(itemId: T, state: Boolean)
}