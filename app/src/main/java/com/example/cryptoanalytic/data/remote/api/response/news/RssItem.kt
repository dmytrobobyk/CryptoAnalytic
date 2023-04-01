package com.example.cryptoanalytic.data.remote.api.response.news

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
class RssItem {

    @Element
    var title: String? = null

    @Element
    var link: String? = null

    @Element(name = "content", required = true, type = String::class)
    var content: String? = null

    @Element
    var enclosure: String? = null

    @Element
    var pubDate: String? = null

    @Element
    var description: String? = null

    var imageUrl: String? = null

    @Element
    var categories: String? = null




    override fun toString(): String {
        return ("RssItem [title=" + title + ", link=" + link + ", content=" + content + ", enclosure=" + enclosure +", pubDate=" + pubDate
                + ", description=" + description + "content=" + content + ", categories=" + categories +"]")
    }
}