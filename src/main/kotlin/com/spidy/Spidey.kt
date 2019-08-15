package com.spidy

fun main() {
    println(Spidey() crawl "http://www.example.com")
}

class Spidey(private val connector: WebConnector = WebConnectorImpl()) {

    infix fun crawl(url: String): Page {
        return Page()
    }

}