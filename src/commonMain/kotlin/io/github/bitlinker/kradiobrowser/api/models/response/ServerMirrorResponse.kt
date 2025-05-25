package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The server mirror response model
 */
@Serializable
public data class ServerMirrorResponse(
    @SerialName("ip")
    val ip: String,

    @SerialName("name")
    val name: String,
)