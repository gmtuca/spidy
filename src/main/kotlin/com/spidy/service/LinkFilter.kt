package com.spidy.service

typealias LinkFilter = (String) -> Boolean

/**
 * Class in charge of making the decision as to whether a link should be visited.
 *
 * SubdomainFilter will filter out any absolute path outside the given domain.
 * Relative paths are always visited.
 */
class SubdomainFilter(val domain: String) : LinkFilter {

    private val urlPrefixPattern = Regex("^((?:https?:\\/\\/)?(?:www\\.)?).*\$")

    override fun invoke(url: String): Boolean {
        val matcher = urlPrefixPattern.matchEntire(url)

        val prefix: String? = matcher?.groups?.get(1)?.value

        if (prefix == null || prefix.isEmpty()) {
            return true
        }

        return url.removePrefix(prefix)
                  .startsWith(domain)
    }
}