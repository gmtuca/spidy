package com.spidy.service

import org.jsoup.Jsoup

interface LinkNavigator {
    fun links(html : String) : List<String>
}

class LinkNavigatorImpl: LinkNavigator {
    override fun links(html: String): List<String> =
        Jsoup.parse(html)
            .select("a[href]")
            .map { it.attr("href") }
}