package com.spidy.service

import org.jsoup.Jsoup

interface LinkNavigator {
    fun links(url : String) : List<String>
}

class LinkNavigatorImpl(private val connector: WebConnector) : LinkNavigator {
    override fun links(url: String): List<String> = Jsoup.parse(connector.get(url))
            .select("a[href]")
            .map { it.attr("href") }
}