package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The station resolved URL response model
 */
@Serializable
public data class StationUrlResponse(
    @SerialName("ok")
    override val ok: Boolean,

    @SerialName("message")
    override val message: String? = null,

    @SerialName("stationuuid")
    val stationUuid: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("url")
    val url: String? = null,
) : StationResultResponse
