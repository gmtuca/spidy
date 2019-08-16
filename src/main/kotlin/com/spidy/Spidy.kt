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
    println(WebConnectorImpl().get("monzo.com"))
    //println(Spidy() crawl "https://en.wikipedia.org")
}

class Spidy(private val connector: WebConnector = WebConnectorImpl(),
            private val withinDomain: Boolean = true) {

    private val linkNavigator : LinkNavigator = LinkNavigatorImpl()

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
            Page(url = url, status = 0, cyclic = true)
        } else {
            val (status : Int, body : String) = connector.get(url)

            val links = linkNavigator.links(body)
                .filter { linkFilter(it) }
                .map { crawl(it, linkFilter, linksVisited) }

            Page(
                url = url,
                status = status,
                links = links
            )
        }
}