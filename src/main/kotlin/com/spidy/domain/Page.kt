package com.spidy.domain

data class Page(
    val url : String,
    val links : List<Page> = emptyList(),
    val cyclic : Boolean = false) {

    companion object {
        @JvmStatic
        fun empty(url : String) = Page(url)
    }
}