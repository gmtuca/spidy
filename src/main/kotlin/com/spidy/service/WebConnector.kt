package com.spidy.service

interface WebConnector {
    fun get(url : String) : String
}

class WebConnectorImpl : WebConnector {
    override fun get(url: String) = ""
}