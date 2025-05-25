package io.github.bitlinker.kradiobrowser.api.models.request

/**
 * The model to add new station
 */
public data class AddStationRequest(
    /**  MANDATORY, the name of the radio station. Max 400 chars. */
    val name: String,

    /** MANDATORY, the URL of the station */
    val url: String,

    /** the homepage URL of the station */
    val homepage: String? = null,

    /** the URL of an image file (jpg or png) */
    val favicon: String? = null,

    /** The 2 letter countrycode of the country where the radio station is located */
    val countryCode: String? = null,

    /** The name of the part of the country where the station is located **/
    @Deprecated("Use iso_3166_2 instead.")
    val state: String? = null,

    /** The iso code of the part of the country where the station is located */
    @Suppress("PropertyName")
    val iso_3166_2: String? = null,

    /** The main language used in spoken text parts of the radio station */
    val language: String? = null,

    /** A list of tags separated by commas to describe the station */
    val tags: List<String> = emptyList(),

    /** The latitude of the stream location. */
    val geoLat: Double? = null,

    /** The longitude of the stream location. Nullable. */
    val geoLon: Double? = null,
)