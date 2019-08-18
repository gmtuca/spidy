package com.spidy

import com.spidy.service.ConcurrentLinksNavigator
import com.spidy.service.SequentialLinksNavigator
import com.spidy.domain.Page
import com.spidy.service.*
import java.lang.IllegalArgumentException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * args[0] (mandatory) - top-level domain to crawl
 * args[1] (optional) - boolean representing whether to crawl concurrently (true) or sequentially (false)
 */
fun main(args : Array<String>) {
    if(args.isEmpty()) {
        throw IllegalArgumentException("Please provide website top-level domain as first argument")
    }

    val domain = args[0]
    val concurrent = if(args.size >= 2) args[1].toBoolean() else false

    println("Crawling $domain ${if (concurrent) "concurrently" else "sequentially"}")

    val timeTaken = measureTimeMillis {
        println(domain.crawl(concurrent = concurrent))
    }

    println("Took ${timeTaken}ms to complete")
}

/**
 * Recursively crawls the given domain, following any link under HTML <a href="..."> tag withing the domain.
 * The same link will not be visited more than once to avoid cyclic dependency.
 *
 * @return Root page outlining the tree-structure path taken by the crawler and each HTTP status code.
 */
fun String.crawl(path : String = "/",
                 concurrent : Boolean = true,
                 connector: WebConnector = WebConnectorImpl(this)) : Page {

    val linkParser = LinkParserImpl()
    val linkFilter = SubdomainFilter(this)
    val linkNormalizer = LinkNormalizerImpl()
    val linkNavigator = if(concurrent) ConcurrentLinksNavigator() else SequentialLinksNavigator()

    val linksVisited = ConcurrentHashMap.newKeySet<String>()
    val totalVisited = AtomicInteger()

    fun crawl(depth : Int = 0, path: String) : Page =
        if(linksVisited.add(path)) {
            //link has not been visited, so visit it!

            val (status : Int, body : String) = connector.get(path)

            println("${totalVisited.getAndIncrement()}: ${" ".repeat(depth)} $status $path")

            val links : List<String> = linkParser.parse(body)
                .filter { linkFilter(it) }
                .map { linkNormalizer(it) }

            val subpages = linkNavigator.navigate(links) { crawl(depth+1, it) }

            Page(
                url = path,
                status = status,
                subpages = subpages
            )
        } else {
            // link was already visited - cyclic dependency
            Page(url = path, status = 0, cyclic = true)
        }

    //start by visiting root of the domain
    return crawl(0, path)
}