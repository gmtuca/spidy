package com.spidy.service

typealias LinkNormalizer = (url: String) -> String

/**
 * Class in charge of normalizing a fully qualified URL into a relative path.
 * In addition, it normalizes the relative path itself, such that "test.html" is turned into "/test.html".
 */
class LinkNormalizerImpl : LinkNormalizer {

    private val absolutePathPattern =
        Regex("^(?:www\\.|(?:http)s?:\\/\\/).+\\.[^\\/?]+([\\/?]?.*)\$")

    override fun invoke(url: String): String {
        val absolutePathMatcher = absolutePathPattern.matchEntire(url)

        val relativePath = if (absolutePathMatcher == null) url else absolutePathMatcher.groups[1]!!.value

        return normalizeRelativePath(relativePath)
    }

    private fun normalizeRelativePath(url: String) =
        if (url.isEmpty()) {
            "/"
        } else {
            when (url[0]) {
                '#' -> "/"
                !in listOf('/', '?') -> "/$url"
                else -> url
            }
        }

}