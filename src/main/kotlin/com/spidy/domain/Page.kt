package com.spidy.domain

data class Page(
    val url : String,
    val status : Int,
    val links : List<Page> = emptyList(),
    val cyclic : Boolean = false) {

    companion object {
        @JvmStatic
        fun leaf(url : String, status: Int) = Page(url = url, status = status)
    }
}