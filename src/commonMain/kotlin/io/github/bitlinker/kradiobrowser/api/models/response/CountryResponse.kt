package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The country response model
 */
@Serializable
public data class CountryResponse(
    @SerialName("name")
    override val name: String,

    @SerialName("iso_3166_1")
    val code: String,

    @SerialName("stationcount")
    override val stationCount: Int,
) : NamedWithStationCountResponse