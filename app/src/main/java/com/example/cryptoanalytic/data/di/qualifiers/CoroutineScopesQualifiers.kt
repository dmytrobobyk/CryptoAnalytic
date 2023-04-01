package com.example.cryptoanalytic.data.di.qualifiers

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

