package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The tag response model
 */
@Serializable
public data class TagResponse(
    @SerialName("name")
    val name: String,

    @SerialName("stationcount")
    val stationCount: Int,
)