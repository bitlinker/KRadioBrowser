package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This struct is used in a tree structure to represent all codepaths that were necessary to check an address of a single stream. Steps can cause multiple other steps for example playlists.
 */
@Serializable
public data class StreamingServerResponse(
    /** An unique id for this StreamingServer */
    @SerialName("uuid")
    val uuid: String,

    /** The url that this streaming server */
    @SerialName("url")
    val url: String,

    /** The url for fetching extended meta information from this streaming server */
    @SerialName("statusurl")
    val statusUrl: String? = null,

    /** If this field exists, the server either does not have extended information or the information was not parsable */
    @SerialName("error")
    val error: String? = null,

    /** Administrative contact of the streaming server */
    @SerialName("admin")
    val admin: String,

    /** Physical location of the streaming server */
    @SerialName("location")
    val location: String,

    /** Server software name and version */
    @SerialName("software")
    val software: String
)
