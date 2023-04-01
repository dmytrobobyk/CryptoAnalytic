package com.example.cryptoanalytic.data.remote.rss

import java.lang.reflect.Type

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

class RssXmlConverterFactory
/**
 * Constructor
 */
private constructor() : Converter.Factory() {

    override fun responseBodyConverter(type: Type?,
                                       annotations: Array<Annotation>?,
                                       retrofit: Retrofit?):
            Converter<ResponseBody, *> = RssXmlResponseBodyConverter()

    companion object {

        /**
         * Creates an instance
         *
         * @return instance
         */
        fun create(): RssXmlConverterFactory {
            return RssXmlConverterFactory()
        }
    }
}