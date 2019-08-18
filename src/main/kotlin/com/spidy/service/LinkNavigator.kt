package com.spidy.service

import org.jsoup.Jsoup

interface LinkNavigator {
    fun links(html : String) : List<String>
}

/**
 * Class in charge of parsing html input and returning the list of {code}<a href="...">{code} links contained in the
 * given page.
 * If the input is non-html, an empty list is returned
 */
class LinkNavigatorImpl: LinkNavigator {
    override fun links(html: String): List<String> =
        try {
            Jsoup.parse(html)
                .select("a[href]")
                .map { it.attr("href") }
        } catch (e: Exception) {
            emptyList()
        }
}