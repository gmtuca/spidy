package com.spidy.domain

/**
 * Data class representing a single HTTP page and a recursive links of its sub-pages.
 */
data class Page(
    val status : Int,
    val url : String,
    val links : List<Page> = emptyList(),
    val cyclic : Boolean = false) {

    companion object {

        /**
         * @return Simple page without any follow-up links.
         */
        @JvmStatic
        fun leaf(status: Int, url: String) = Page(status = status, url = url)
    }
}