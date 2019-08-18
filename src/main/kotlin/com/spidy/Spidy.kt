package com.spidy

import com.spidy.service.ConcurrentLinksNavigator
import com.spidy.service.SequentialLinksNavigator
import com.spidy.domain.Page
import com.spidy.service.*
import java.lang.IllegalArgumentException
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

fun main(args : Array<String>) {
    if(args.isEmpty()) {
        throw IllegalArgumentException("Please provide website top-level domain as first argument")
    }

    val domain = args[0]
    val concurrent = if(args.size >= 2) args[1].toBoolean() else false

    println("Crawling $domain ${if (concurrent) "concurrently" else "sequentially"}")

    val timeTaken = measureTimeMillis {
        println(domain.crawl(concurrent))
    }

    println("Took ${timeTaken}ms to complete")
}

/**
 * Recursively crawls the given domain, following any link under HTML <a href="..."> tag withing the domain.
 * The same link will not be visited more than once to avoid cyclic dependency.
 *
 * @return Root page outlining the tree-structure path taken by the crawler and each HTTP status code.
 */
fun String.crawl(concurrent : Boolean = true,
                 connector: WebConnector = WebConnectorImpl(this)) : Page {

    val linkParser = LinkParserImpl()
    val linkFilter = SubdomainFilter(this)
    val linkNormalizer = LinkNormalizerImpl()
    val linkNavigator = if(concurrent) ConcurrentLinksNavigator() else SequentialLinksNavigator()

    val linksVisited = ConcurrentHashMap.newKeySet<String>()

    fun crawl(url: String) : Page =
        if(linksVisited.add(url)) {
            //link has not been visited, so visit it!

            val (status : Int, body : String) = connector.get(url)

            val links : List<String> = linkParser.parse(body)
                .filter { linkFilter(it) }
                .map { linkNormalizer(it) }

            val subpages = linkNavigator.navigate(links, ::crawl)

            Page(
                url = url,
                status = status,
                links = subpages
            )
        } else {
            // link was already visited - cyclic dependency
            Page(url = url, status = 0, cyclic = true)
        }

    //start by visiting root of the domain
    return crawl("/")
}