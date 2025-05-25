package io.github.bitlinker.kradiobrowser.api.models.response

import io.github.bitlinker.kradiobrowser.internal.serializers.SimpleDateSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The model for station click response
 */
@Serializable
public data class StationClickResponse(
    /** A globally unique identifier for the station */
    @SerialName("stationuuid")
    val stationUuid: String,

    @SerialName("clickuuid")
    val clickUuid: String,

    /** The time of the last click recorded for this stream */
    @SerialName("clicktimestamp")
    @Serializable(with = SimpleDateSerializer::class)
    val clickTimestamp: Instant,

    /** The time of the last click recorded for this stream */
    @SerialName("clicktimestamp_iso8601")
    val clickTimestampIso8601: Instant,
)