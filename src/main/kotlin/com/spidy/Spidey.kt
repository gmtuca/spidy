package com.spidy

import com.spidy.domain.LinkFilter
import com.spidy.domain.Page
import com.spidy.service.LinkNavigator
import com.spidy.service.LinkNavigatorImpl
import com.spidy.service.WebConnector
import com.spidy.service.WebConnectorImpl

fun main() {
    println(Spidey() crawl "http://www.example.com")
}

class Spidey(connector: WebConnector = WebConnectorImpl(),
             private val linkFilter: LinkFilter = { true }) {

    private val linkNavigator : LinkNavigator = LinkNavigatorImpl(connector)

    infix fun crawl(url: String): Page {
        return Page(url,
            linkNavigator.links(url)
                .filter { linkFilter(it) }
                .map { crawl(it) }

        )
    }

}