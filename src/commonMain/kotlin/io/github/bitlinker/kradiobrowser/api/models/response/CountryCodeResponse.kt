package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The country code response model
 */
@Serializable
public data class CountryCodeResponse(
    @SerialName("name")
    override val name: String,

    @SerialName("stationcount")
    override val stationCount: Int,
) : NamedWithStationCountResponse