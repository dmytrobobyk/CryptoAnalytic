package com.example.cryptoanalytic.data.remote.rss

import com.example.cryptoanalytic.data.remote.api.response.news.RssChannel
import com.example.cryptoanalytic.data.remote.api.response.news.RssFeed
import org.xml.sax.InputSource
import javax.xml.parsers.SAXParserFactory
import okhttp3.ResponseBody
import retrofit2.Converter


internal class RssXmlResponseBodyConverter : Converter<ResponseBody, RssFeed> {

    override fun convert(value: ResponseBody): RssFeed {
        val rssFeed = RssFeed()
        try {
            val parser = XMLParser()
            val parserFactory = SAXParserFactory.newInstance()
            val saxParser = parserFactory.newSAXParser()
            val xmlReader = saxParser.xmlReader
            xmlReader.contentHandler = parser
            val inputSource = InputSource(value.charStream())
            xmlReader.parse(inputSource)
            val items = parser.items
            rssFeed.channel = RssChannel().apply { this.items = items }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return rssFeed
    }

}