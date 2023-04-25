package com.cryptoanalytic.data.remote.api.response.news

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class RssFeed {
    @Element
    var channel: RssChannel? = null
    override fun toString(): String {
        return "RssFeed [channel=$channel]"
    }
}