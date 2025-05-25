package io.github.bitlinker.kradiobrowser.api.models.request

/**
 * The paging request model
 */
public data class PagingRequest(
    val offset: Int = 0,
    val limit: Int = 100,
)