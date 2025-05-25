package io.github.bitlinker.kradiobrowser.api.models.response

import io.github.bitlinker.kradiobrowser.internal.serializers.SimpleDateSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This struct is used in a represent an online check of a stream. Most of the information got extracted by checking the http headers of the stream.
 */
@Serializable
public data class StationCheckResponse(
    /** An unique id for this StationCheck */
    @SerialName("checkuuid")
    val checkUuid: String,

    /** An unique id for referencing a Station */
    @SerialName("stationuuid")
    val stationUuid: String,

    /** DNS Name of the server that did the stream check. */
    @SerialName("source")
    val source: String,

    /** High level name of the used codec of the stream. May have the format AUDIO or AUDIO/VIDEO. */
    @SerialName("codec")
    val codec: String,

    /** Bitrate 1000 bits per second (kBit/s) of the stream. (Audio + Video) */
    @SerialName("bitrate")
    val bitrate: Int,

    /** 1 means this is an HLS stream, otherwise 0. */
    @SerialName("hls")
    val hls: Int,

    /** 1 means this stream is reachable, otherwise 0. */
    @SerialName("ok")
    val ok: Int,

    /** Date and time of check creation (ISO-8601 format) */
    @SerialName("timestamp_iso8601")
    val timestampIso8601: Instant,

    /** Date and time of check creation (YYYY-MM-DD HH:mm:ss format) */
    @SerialName("timestamp")
    @Serializable(with = SimpleDateSerializer::class)
    val timestamp: Instant,

    /** Direct stream url that has been resolved from the main stream url. HTTP redirects and playlists have been decoded. If hls==1 then this is still a HLS-playlist. */
    @SerialName("urlcache")
    val urlCache: String,

    /** 1 means this stream has provided extended information and it should be used to override the local database, otherwise 0. */
    @SerialName("metainfo_overrides_database")
    val metainfoOverridesDatabase: Int,

    /** 1 that this stream appears in the public shoutcast/icecast directory, otherwise 0. */
    @SerialName("public")
    val public: Int? = null,

    /** The name extracted from the stream header. */
    @SerialName("name")
    val name: String? = null,

    /** The description extracted from the stream header. */
    @SerialName("description")
    val description: String? = null,

    /** Komma separated list of tags. (genres of this stream) */
    @SerialName("tags")
    val tags: String? = null,

    /** Official countrycodes as in ISO 3166-1 alpha-2 */
    @SerialName("countrycode")
    val countryCode: String? = null,

    /** Official country subdivision codes as in ISO 3166-2 */
    @SerialName("countrysubdivisioncode")
    val countrySubdivisionCode: String? = null,

    /** The homepage extracted from the stream header. */
    @SerialName("homepage")
    val homepage: String? = null,

    /** The favicon extracted from the stream header. */
    @SerialName("favicon")
    val favicon: String? = null,

    /** The loadbalancer extracted from the stream header. */
    @SerialName("loadbalancer")
    val loadBalancer: String? = null,

    /** The name of the server software used. */
    @SerialName("server_software")
    val serverSoftware: String? = null,

    /** Audio sampling frequency in Hz */
    @SerialName("sampling")
    val sampling: Int? = null,

    /** Timespan in miliseconds this check needed to be finished. */
    @SerialName("timing_ms")
    val timingMs: Int,

    /** The description extracted from the stream header. (Note: Description for this field seems duplicated in the table, assuming it means language codes here based on name) */
    @SerialName("languagecodes")
    val languageCodes: String? = null,

    /** 1 means that a ssl error occured while connecting to the stream, 0 otherwise. */
    @SerialName("ssl_error")
    val sslError: Int,

    /** Latitude on earth where the stream is located. */
    @SerialName("geo_lat")
    val geoLat: Double? = null,

    /** Longitude on earth where the stream is located. */
    @SerialName("geo_long")
    val geoLon: Double? = null
)
