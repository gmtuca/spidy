package com.spidy.domain

import java.util.regex.Pattern

typealias LinkFilter = (String) -> Boolean

class SubdomainFilter(domain : String) : LinkFilter {

    private val pattern = Pattern.compile("^((https?:\\/\\/)?(www\\.)?($domain)|\\/).*\$")

    override fun invoke(url: String): Boolean = pattern.matcher(url).matches()

}

class VisitAllFilter : LinkFilter {
    override fun invoke(url: String) = true
}