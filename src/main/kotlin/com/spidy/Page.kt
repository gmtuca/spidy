package com.spidy

data class Page(val links : Map<String, Page> = emptyMap())