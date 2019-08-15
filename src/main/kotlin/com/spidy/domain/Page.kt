package com.spidy.domain

data class Page(
    val url : String,
    val links : List<Page> = emptyList()
)