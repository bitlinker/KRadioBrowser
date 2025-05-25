package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The state response model
 */
@Serializable
public data class StateResponse(
    @SerialName("name")
    override val name: String,

    @SerialName("country")
    val country: String,

    @SerialName("stationcount")
    override val stationCount: Int,
) : NamedWithStationCountResponse