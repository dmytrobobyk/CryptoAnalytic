package com.example.cryptoanalytic.di

import javax.inject.Qualifier


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherDefaultScope

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherIOScope

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherMainScope
