package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This struct is used in a tree structure to represent all codepaths that were necessary to check an address of a single stream. Steps can cause multiple other steps for example playlists.
 */
@Serializable
public data class StationCheckStepResponse(
    /** An unique id for this StationCheckStep */
    @SerialName("stepuuid")
    val stepUuid: String,

    /** An unique id for referencing another StationCheckStep. Is set if this step has a parent. */
    @SerialName("parent_stepuuid")
    val parentStepUuid: String? = null,

    /** An unique id for referencing a StationCheck */
    @SerialName("checkuuid")
    val checkUuid: String,

    /** An unique id for referencing a Station */
    @SerialName("stationuuid")
    val stationUuid: String,

    /** The url that this step of the checking process handled */
    @SerialName("url")
    val url: String,

    /** Does represent which kind of url it is. One of the following: STREAM, REDIRECT, PLAYLIST. */
    @SerialName("urltype")
    val urlType: UrlType? = null,

    /** Error message if an error occurred during this check step. (Description from table seems mismatched, assuming actual error message here) */
    @SerialName("error")
    val error: String? = null,

    /** Date and time of step creation (ISO-8601 format) */
    @SerialName("creation_iso8601")
    val creationIso8601: Instant,
) {
    @Serializable
    public enum class UrlType {
        @SerialName("STREAM")
        Stream,

        @SerialName("REDIRECT")
        Redirect,

        @SerialName("PLAYLIST")
        Playlist,
    }
}