package com.spidy.service

import io.github.rybalkinsd.kohttp.dsl.httpGet
import java.util.concurrent.atomic.AtomicInteger

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

    val i = AtomicInteger()

    /**
     * @return pair containing HTTP response status code and body in string format, respectively
     */
    override fun get(url: String) : HttpResponse {
        val httpResp = try {
            val res = httpGet {
                host = domain
                path = url

                header {
                    "USER_AGENT" to "Mozilla/5.0"
                }
            }

            HttpResponse(res.code(), res.body()!!.string())
        } catch (e: Exception) {
            HttpResponse(-1, e.message ?: "")
        }

        println("${i.getAndIncrement()}: ${httpResp.first} $url")

        return httpResp
    }
}