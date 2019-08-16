package com.spidy.service

import io.github.rybalkinsd.kohttp.dsl.httpGet
import com.spidy.domain.HttpResponse

interface WebConnector {
    fun get(url : String) : HttpResponse
}

class WebConnectorImpl : WebConnector {
    override fun get(url: String) : HttpResponse {
        val res = httpGet {
            host = url

            header {
                "USER_AGENT" to "Mozilla/5.0"
            }
        }

        return HttpResponse(res.code(), res.body()!!.string())
    }
}