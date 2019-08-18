package com.spidy.service

import io.github.rybalkinsd.kohttp.dsl.httpGet

typealias HttpResponse = Pair<Int, String>

interface WebConnector {
    fun get(url : String) : HttpResponse
}

/**
 * Wrapper class around any HTTP client.
 * Allows performing GET requests against given path withing domain. Other HTTP methods are not supported.
 * This allows easy mocking for unit testing
 */
class WebConnectorImpl(private val domain : String) : WebConnector {

    var i = 0

    /**
     * @return pair containing HTTP response status code and body in string format, respectively
     */
    override fun get(url: String) : HttpResponse {
        val res = httpGet {
            host = domain
            path = url

            header {
                "USER_AGENT" to "Mozilla/5.0"
            }
        }

        println("${i++}: ${res.code()} $url")

        return HttpResponse(res.code(), res.body()!!.string())
    }
}