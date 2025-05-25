package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Representation of an api endpoint statistic.
 */
@Serializable
public data class ServerStatsResponse(
    /** The supported version. */
    @SerialName("supported_version")
    val supportedVersion: Int? = null,

    /** The version of the API. */
    @SerialName("software_version")
    val softwareVersion: String? = null,

    /** The status, should be "OK". */
    @SerialName("status")
    val status: String? = null,

    /** The number of stations stored on the server. */
    @SerialName("stations")
    val stations: Int? = null,

    /** The number of broken stations. */
    @SerialName("stations_broken")
    val stationsBroken: Int? = null,

    /** The number of tags. */
    @SerialName("tags")
    val tags: Int? = null,

    /** The number of clicks in the last hour. */
    @SerialName("clicks_last_hour")
    val clicksLastHour: Int? = null,

    /** The number of clicks in the last 24 hours. */
    @SerialName("clicks_last_day")
    val clicksLastDay: Int? = null,

    /** The number of languages. */
    @SerialName("languages")
    val languages: Int? = null,

    /** The number of countries. */
    @SerialName("countries")
    val countries: Int? = null,
)