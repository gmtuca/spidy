package com.spidy

import com.spidy.domain.Page
import com.spidy.service.*

fun main() {
    println("google.com".crawl())
}

/**
 * Recursively crawls the given domain, following any link under HTML <a href="..."> tag withing the domain.
 * The same link will not be visited more than once to avoid cyclic dependency.
 *
 * @return Root page outlining the tree-structure path taken by the crawler and each HTTP status code.
 */
fun String.crawl(connector: WebConnector = WebConnectorImpl(this)) : Page {

    val linkNavigator : LinkNavigator = LinkNavigatorImpl()
    val linkFilter : LinkFilter = SubdomainFilter(this)
    val linkNormalizer : LinkNormalizer = LinkNormalizerImpl()

    val linksVisited : MutableSet<String> = mutableSetOf()

    fun crawl(url: String) : Page =
        if(linksVisited.add(url)) {
            //link has not been visited, so visit it!

            val (status : Int, body : String) = connector.get(url)

            val links = linkNavigator.links(body)
                .filter { linkFilter(it) }
                .map { linkNormalizer(it) }
                .map { crawl(it) }

            Page(
                url = url,
                status = status,
                links = links
            )
        } else {
            // link was already visited - cyclic dependency
            Page(url = url, status = 0, cyclic = true)
        }

    //start by visiting root of the domain
    return crawl("/")
}