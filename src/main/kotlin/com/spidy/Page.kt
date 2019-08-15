package com.spidy

data class Page(
    val url : String,
    val links : List<Page> = emptyList()
)