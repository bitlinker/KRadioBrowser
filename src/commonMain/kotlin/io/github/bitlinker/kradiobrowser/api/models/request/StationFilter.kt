package io.github.bitlinker.kradiobrowser.api.models.request

/**
 * The station filter request
 */
public sealed interface StationFilter {

    /**
     * The simple request which is using single filtering parameter
     */
    public data class Simple(
        val matchingFilter: MatchingFilter,
        val field: Field = Field.Name,
    ) : StationFilter {
        public enum class Field {
            Uuid,
            Name,
            Codec,
            Country,
            CountryCode,
            State,
            Language,
            Tag,
        }
    }

    /**
     * The advanced request with multiple parameters
     */
    public data class Advanced(
        /** Name of the station. */
        val name: MatchingFilter? = null,

        /** Country of the station. */
        val country: MatchingFilter? = null,

        /** 2-digit countrycode of the station (see ISO 3166-1 alpha-2). */
        val countryCode: String? = null,

        /** State of the station. */
        val state: MatchingFilter? = null,

        /** Language of the station. */
        val language: MatchingFilter? = null,

        /** A tag of the station. */
        val tag: MatchingFilter? = null,

        /** A list of tag. All tags in list have to match. */
        val tagList: List<String> = emptyList(),

        /** Codec of the station. */
        val codec: String? = null,

        /** Minimum of kbps for bitrate field of stations in result. */
        val bitrateMin: Int? = null,

        /** Maximum of kbps for bitrate field of stations in result. */
        val bitrateMax: Int? = null,

        /** not set=display all, true=show only stations with geo_info, false=show only stations without geo_info */
        val hasGeoInfo: Boolean? = null,

        /** not set=display all, true=show only stations which do provide extended information, false=show only stations without extended information */
        val hasExtendedInfo: Boolean? = null,

        /** not set=display all, true=show only stations which have https url, false=show only stations that do stream unencrypted with http */
        val isHttps: Boolean? = null,

        val geoLat: Double? = null,

        val geoLon: Double? = null,

        /** does select only stations with distance from (geo_lat,geo_long) that is smaller than geo_distance in meters */
        val geoDistance: Double? = null,
    ) : StationFilter
}