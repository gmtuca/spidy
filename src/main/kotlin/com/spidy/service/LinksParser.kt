package com.spidy.service

import org.jsoup.Jsoup

interface LinkParser {
    fun parse(html : String) : List<String>
}

/**
 * Class in charge of parsing html input and returning the list of {code}<a href="...">{code} parse contained in the
 * given page.
 * If the input is non-html, an empty list is returned
 */
class LinkParserImpl: LinkParser {
    override fun parse(html: String): List<String> =
        try {
            Jsoup.parse(html)
                .select("a[href]")
                .map { it.attr("href") }
        } catch (e: Exception) {
            emptyList()
        }
}