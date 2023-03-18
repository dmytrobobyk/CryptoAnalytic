package com.example.cryptoanalytic.common.utils.feedResponse

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
class RssImage {
    @Element
    private val url: String? = null

    @Element
    private val width: String? = null

    @Element
    private val height: String? = null
    override fun toString(): String {
        return "RssImage [url=$url, width=$width, height=$height]"
    }
}