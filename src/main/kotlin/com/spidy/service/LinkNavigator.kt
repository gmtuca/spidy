package com.spidy.service

import com.spidy.domain.Page
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Interface to be implemented by classes in charge of iterating through a list of subpages (as Strings), returning
 * a list of sub-pages by recursively following such subpages.
 */
interface LinksNavigator {
    fun navigate(links : List<String>, navigationFn : (String) -> Page) : List<Page>
}

/**
 * Sequentially iterate through subpages, on a single thread
 */
class SequentialLinksNavigator : LinksNavigator {
    override fun navigate(links: List<String>, navigationFn: (String) -> Page) = links.map(navigationFn)
}

/**
 * Concurrently iterate through subpages, assigning 'threadsPerLink' threads per link.
 * Sub-pages of the given subpages are joined at the end, to provide a complete Page object.
 */
class ConcurrentLinksNavigator : LinksNavigator {

    private val threadsPerLink = 0.5

    override fun navigate(links: List<String>, navigationFn : (String) -> Page): List<Page> {

        if(links.isEmpty()) {
            return emptyList()
        }

        val callableSubpages = links.map { Callable { navigationFn(it) } }

        val executorService = Executors.newFixedThreadPool((links.size * threadsPerLink).toInt() + 1)

        val subpages = executorService.invokeAll(callableSubpages)
                                      .map { it.get() }

        executorService.shutdown()
        executorService.awaitTermination(5, TimeUnit.SECONDS)

        return subpages
    }
}