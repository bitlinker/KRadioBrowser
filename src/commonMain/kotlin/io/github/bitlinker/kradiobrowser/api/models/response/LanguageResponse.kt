package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The language response model
 */
@Serializable
public data class LanguageResponse(
    @SerialName("name")
    override val name: String,

    @SerialName("stationcount")
    override val stationCount: Int,

    @SerialName("iso_639")
    val code: String? = null,
) : NamedWithStationCountResponse