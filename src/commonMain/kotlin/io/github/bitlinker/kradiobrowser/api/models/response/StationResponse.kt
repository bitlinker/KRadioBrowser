package io.github.bitlinker.kradiobrowser.api.models.response

import io.github.bitlinker.kradiobrowser.internal.serializers.SimpleDateNullableSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Representation of a Radio Station.
 */
@Serializable
public data class StationResponse(
    /** A globally unique identifier for the change of the station information */
    @SerialName("changeuuid")
    val changeUuid: String,

    /** A globally unique identifier for the station */
    @SerialName("stationuuid")
    val stationUuid: String,

    @SerialName("serveruuid")
    val serverUuid: String? = null,

    /** The name of the station */
    @SerialName("name")
    val name: String,

    /** The stream URL provided by the user */
    @SerialName("url")
    val url: String,

    /** An automatically "resolved" stream URL. Things resolved are playlists (M3U/PLS/ASX...),
     *  HTTP redirects (Code 301/302). This link is especially usefull if you use this API
     *  from a platform that is not able to do a resolve on its own (e.g. JavaScript in browser)
     *  or you just don't want to invest the time in decoding playlists yourself.
     *  */
    @SerialName("url_resolved")
    val urlResolved: String,

    /** URL to the homepage of the stream */
    @SerialName("homepage")
    val homepage: String,

    /** URL to an icon or picture that represents the stream. (PNG, JPG) */
    @SerialName("favicon")
    val favicon: String,

    /** Tags of the stream with more information about it */
    @SerialName("tags")
    val tags: String,

    /** Full name of the country. */
    @Deprecated("Use countrycode instead")
    @SerialName("country")
    val country: String,

    /** Official countrycodes as in ISO 3166-1 alpha-2 */
    @SerialName("countrycode")
    val countryCode: String,

    /** Full name of the entity where the station is located inside the country */
    @SerialName("state")
    val state: String,

    /** The ISO-3166-2 code of the entity where the station is located inside the country */
    @SerialName("iso_3166_2")
    val iso31662: String? = null,

    /** Languages that are spoken in this stream. String, multivalue, split by comma */
    @SerialName("language")
    val language: String,

    /** Languages that are spoken in this stream by code ISO 639-2/B, string, multivalue, split by comma */
    @SerialName("languagecodes")
    val languageCodes: String,

    /** Number of votes for this station. This number is by server and only ever increases. It will never be reset to 0.*/
    @SerialName("votes")
    val votes: Int,

    /** Last time when the stream information was changed in the database */
    @SerialName("lastchangetime")
    @Serializable(with = SimpleDateNullableSerializer::class)
    val lastchangetime: Instant? = null,

    /** Last time when the stream information was changed in the database */
    @SerialName("lastchangetime_iso8601")
    val lastchangetimeIso8601: Instant? = null,

    /** The codec of this stream recorded at the last check. */
    @SerialName("codec")
    val codec: String,

    /** The bitrate of this stream recorded at the last check. */
    @SerialName("bitrate")
    val bitrate: Int,

    /** Mark if this stream is using HLS distribution or non-HLS. 0 or 1 */
    @SerialName("hls")
    val hls: Int,

    /** The current online/offline state of this stream. 0 or 1. This is a value calculated from
     *  multiple measure points in the internet. The test servers are located in different
     *  countries. It is a majority vote */
    @SerialName("lastcheckok")
    val lastCheckOk: Int,

    /** The last time when any radio-browser server checked the online state of this stream */
    @SerialName("lastchecktime")
    @Serializable(with = SimpleDateNullableSerializer::class)
    val lastCheckTime: Instant? = null,

    /** The last time when any radio-browser server checked the online state of this stream */
    @SerialName("lastchecktime_iso8601")
    val lastCheckTimeIso8601: Instant? = null,

    /** The last time when the stream was checked for the online status with a positive result */
    @SerialName("lastcheckoktime")
    @Serializable(with = SimpleDateNullableSerializer::class)
    val lastCheckOkTime: Instant? = null,

    /** The last time when the stream was checked for the online status with a positive result */
    @SerialName("lastcheckoktime_iso8601")
    val lastCheckOkTimeIso8601: Instant? = null,

    /** The last time when this server checked the online state and the metadata of this stream */
    @SerialName("lastlocalchecktime")
    @Serializable(with = SimpleDateNullableSerializer::class)
    val lastLocalCheckTime: Instant? = null,

    /** The last time when this server checked the online state and the metadata of this stream */
    @SerialName("lastlocalchecktime_iso8601")
    val lastLocalCheckTimeIso8601: Instant? = null,

    /** The time of the last click recorded for this stream */
    @SerialName("clicktimestamp")
    @Serializable(with = SimpleDateNullableSerializer::class)
    val clickTimestamp: Instant? = null,

    /** The time of the last click recorded for this stream */
    @SerialName("clicktimestamp_iso8601")
    val clickTimestampIso8601: Instant? = null,

    /** Clicks within the last 24 hours */
    @SerialName("clickcount")
    val clickCount: Int,

    /** The difference of the clickcounts within the last 2 days. */
    @SerialName("clicktrend")
    val clickTrend: Int,

    /** 0 means no error, 1 means that there was an ssl error while connecting to the stream url. */
    @SerialName("ssl_error")
    val sslError: Int,

    /** Latitude on earth where the stream is located. */
    @SerialName("geo_lat")
    val geoLat: Double? = null,

    /** Longitude on earth where the stream is located. */
    @SerialName("geo_long")
    val geoLon: Double? = null,

    /** Distance of the station in meters. ONLY set if geo_lat AND geo_long are set on the station AND on the request. */
    @SerialName("geo_distance")
    val geoDistance: Double? = null,

    /** Is true, if the stream owner does provide extended information as HTTP headers which override the information in the database. */
    @SerialName("has_extended_info")
    val hasExtendedInfo: Boolean? = false
)