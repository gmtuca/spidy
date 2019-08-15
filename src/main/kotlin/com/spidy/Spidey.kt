package com.spidy

fun main() {
    println(Spidey() crawl "http://www.example.com")
}

class Spidey(private val connector: WebConnector = WebConnectorImpl()) {

    private val linkNavigator : LinkNavigator = LinkNavigatorImpl(connector)

    infix fun crawl(url: String): Page {
        return Page(url,
            linkNavigator.links(url)
                .map { crawl(it) }

        )
    }

}