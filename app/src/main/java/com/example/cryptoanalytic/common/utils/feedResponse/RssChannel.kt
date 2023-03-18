package com.example.cryptoanalytic.common.utils.feedResponse

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
class RssChannel {
    @Element
    private val title: String? = null

    @Element
    private val image: RssImage? = null

    @ElementList(inline = true, required = false)
    var items: List<RssItem>? = null
    override fun toString(): String {
        return "Channel [image=$image, item=$items]"
    }
}