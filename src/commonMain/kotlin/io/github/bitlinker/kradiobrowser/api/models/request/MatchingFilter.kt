package io.github.bitlinker.kradiobrowser.api.models.request

public data class MatchingFilter(
    val query: String,
    val isExact: Boolean = false,
)