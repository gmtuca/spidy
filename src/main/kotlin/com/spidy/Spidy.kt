package com.spidy

import com.spidy.domain.LinkFilter
import com.spidy.domain.Page
import com.spidy.domain.SubdomainFilter
import com.spidy.domain.VisitAllFilter
import com.spidy.service.LinkNavigator
import com.spidy.service.LinkNavigatorImpl
import com.spidy.service.WebConnector
import com.spidy.service.WebConnectorImpl

fun main() {
    println(
        Spidy() crawl "monzo.com")
}

class Spidy(connector: WebConnector = WebConnectorImpl(),
            private val withinDomain: Boolean = true) {

    private val linkNavigator : LinkNavigator = LinkNavigatorImpl(connector)

    infix fun crawl(url: String) : Page {
        val linkFilter : LinkFilter =
            if (withinDomain) SubdomainFilter(url)
            else VisitAllFilter()

        return crawl(url, linkFilter, mutableSetOf())
    }

    private fun crawl(url: String,
                      linkFilter: LinkFilter,
                      linksVisited : MutableSet<String>) : Page =
        if(!linksVisited.add(url)) {
            Page(url = url, cyclic = true)
        } else {
            Page(url, linkNavigator.links(url)
                                   .filter { linkFilter(it) }
                                   .map { crawl(it, linkFilter, linksVisited) }

        )
    }

}