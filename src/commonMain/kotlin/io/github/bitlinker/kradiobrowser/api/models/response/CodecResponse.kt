package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The codec info response model
 */
@Serializable
public data class CodecResponse(
    @SerialName("name")
    override val name: String,

    @SerialName("stationcount")
    override val stationCount: Int
) : NamedWithStationCountResponse