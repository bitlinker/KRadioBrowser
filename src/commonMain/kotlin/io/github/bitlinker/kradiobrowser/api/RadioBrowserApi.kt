package io.github.bitlinker.kradiobrowser.api

import io.github.bitlinker.kradiobrowser.api.models.request.AddStationRequest
import io.github.bitlinker.kradiobrowser.api.models.request.LimitedOrderField
import io.github.bitlinker.kradiobrowser.api.models.request.OrderField
import io.github.bitlinker.kradiobrowser.api.models.request.PagingRequest
import io.github.bitlinker.kradiobrowser.api.models.request.StationFilter
import io.github.bitlinker.kradiobrowser.api.models.request.StationsByField
import io.github.bitlinker.kradiobrowser.api.models.response.CodecResponse
import io.github.bitlinker.kradiobrowser.api.models.response.CountryCodeResponse
import io.github.bitlinker.kradiobrowser.api.models.response.CountryResponse
import io.github.bitlinker.kradiobrowser.api.models.response.LanguageResponse
import io.github.bitlinker.kradiobrowser.api.models.response.RadioBrowserResponse
import io.github.bitlinker.kradiobrowser.api.models.response.ServerConfigResponse
import io.github.bitlinker.kradiobrowser.api.models.response.ServerMirrorResponse
import io.github.bitlinker.kradiobrowser.api.models.response.ServerStatsResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StateResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationAddResultResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationCheckResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationCheckStepResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationClickResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationUrlResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationVoteResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StreamingServerResponse
import io.github.bitlinker.kradiobrowser.api.models.response.TagResponse
import io.github.bitlinker.kradiobrowser.internal.appendAddStationParameters
import io.github.bitlinker.kradiobrowser.internal.appendAdvancedStationFilterParameters
import io.github.bitlinker.kradiobrowser.internal.appendHideBrokenParameter
import io.github.bitlinker.kradiobrowser.internal.appendLastChangeUuidParameter
import io.github.bitlinker.kradiobrowser.internal.appendLastCheckUuidParameter
import io.github.bitlinker.kradiobrowser.internal.appendLastClickUuidParameter
import io.github.bitlinker.kradiobrowser.internal.appendOrderParameter
import io.github.bitlinker.kradiobrowser.internal.appendPagingLimit
import io.github.bitlinker.kradiobrowser.internal.appendPagingParameters
import io.github.bitlinker.kradiobrowser.internal.appendReverseParameter
import io.github.bitlinker.kradiobrowser.internal.appendSecondsParameter
import io.github.bitlinker.kradiobrowser.internal.appendSimpleStationFilterPathSegments
import io.github.bitlinker.kradiobrowser.internal.appendStationsBySegments
import io.github.bitlinker.kradiobrowser.internal.appendUrlParameter
import io.github.bitlinker.kradiobrowser.internal.appendUuidsParameters
import io.github.bitlinker.kradiobrowser.internal.buildApiPathSegments
import io.github.bitlinker.kradiobrowser.internal.buildFormParameters
import io.github.bitlinker.kradiobrowser.internal.buildUrlParameters
import io.github.bitlinker.kradiobrowser.internal.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * This main entry point class for the radio-browser API.
 *
 * Based on server protocol version: 0.7.31 at https://all.api.radio-browser.info
 *
 * @param baseClient the base Ktor client instance. The library will copy it install all required
 * plugins itself
 *
 * @param config the configuration object
 */
public class RadioBrowserApi(
    private val baseClient: HttpClient,
    public val config: RadioBrowserConfig,
) {
    private val client = baseClient.config {
        install(UserAgent) {
            agent = config.userAgent
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
        install(DefaultRequest) {
            url(config.apiUri)
        }
    }


    /** A JSON-encoded list of all countries in the database. If a filter is given, it will
     *  only return the ones containing the filter as substring
     *
     * @return a network response with countries
     * @see <a href="https://all.api.radio-browser.info/#List_of_countries">
     * */
    public suspend fun getCountries(
        filter: String? = null,
        orderBy: LimitedOrderField = LimitedOrderField.Name,
        reverse: Boolean = false,
        hideBroken: Boolean = false,
        pagingRequest: PagingRequest? = null,
    ): RadioBrowserResponse<List<CountryResponse>> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("countries", filter)
            buildUrlParameters {
                appendReverseParameter(reverse)
                appendHideBrokenParameter(hideBroken)
                appendOrderParameter(orderBy)
                appendPagingParameters(pagingRequest)
            }
        }
    }

    /** A JSON-encoded list of all countries in the database. If a filter is given, it will only
     *  return the ones containing the filter as substring
     *
     * @return a network response with country codes
     * @see <a href="https://all.api.radio-browser.info/#List_of_countrycodes">
     * */
    @Deprecated("DEPRECATED: Please use {@link #getCountries()} endpoint instead. It has name and countrycode information.")
    public suspend fun getCountryCodes(
        filter: String? = null,
        orderBy: LimitedOrderField = LimitedOrderField.Name,
        reverse: Boolean = false,
        hideBroken: Boolean = false,
        pagingRequest: PagingRequest? = null,
    ): RadioBrowserResponse<List<CountryCodeResponse>> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("countrycodes", filter)
            buildUrlParameters {
                appendReverseParameter(reverse)
                appendHideBrokenParameter(hideBroken)
                appendOrderParameter(orderBy)
                appendPagingParameters(pagingRequest)
            }
        }
    }


    /** A JSON-encoded list of all codecs in the database. If a filter is given, it will only
     *  return the ones containing the filter as substring
     *
     * @return a network response with codecs
     * @see <a href="https://all.api.radio-browser.info/#List_of_codecs">
     * */
    public suspend fun getCodecs(
        filter: String? = null,
        orderBy: LimitedOrderField = LimitedOrderField.Name,
        reverse: Boolean = false,
        hideBroken: Boolean = false,
        pagingRequest: PagingRequest? = null,
    ): RadioBrowserResponse<List<CodecResponse>> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("codecs", filter)
            buildUrlParameters {
                appendReverseParameter(reverse)
                appendHideBrokenParameter(hideBroken)
                appendOrderParameter(orderBy)
                appendPagingParameters(pagingRequest)
            }
        }
    }

    /** A JSON-encoded list of all states in the database. Countries are divided into states.
     *  If a filter is given, it will only return the ones containing the filter as substring.
     *  If a country is given, it will only display states in this country
     *
     * @return a network response with states
     * @see <a href="https://all.api.radio-browser.info/#List_of_states">
     * */
    public suspend fun getStates(
        filter: String? = null,
        country: String? = null,
        orderBy: LimitedOrderField = LimitedOrderField.Name,
        reverse: Boolean = false,
        hideBroken: Boolean = false,
        pagingRequest: PagingRequest? = null,
    ): RadioBrowserResponse<List<StateResponse>> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("states", country, filter)
            buildUrlParameters {
                appendReverseParameter(reverse)
                appendHideBrokenParameter(hideBroken)
                appendOrderParameter(orderBy)
                appendPagingParameters(pagingRequest)
            }
        }
    }

    /** A JSON-encoded list of all languages in the database. If a filter is given, it will only
     * return the ones containing the filter as substring
     *
     * @return a network response with languages
     * @see <a href="https://all.api.radio-browser.info/#List_of_languages">
     * */
    public suspend fun getLanguages(
        filter: String? = null,
        orderBy: LimitedOrderField = LimitedOrderField.Name,
        reverse: Boolean = false,
        hideBroken: Boolean = false,
        pagingRequest: PagingRequest? = null,
    ): RadioBrowserResponse<List<LanguageResponse>> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("languages", filter)
            buildUrlParameters {
                appendReverseParameter(reverse)
                appendHideBrokenParameter(hideBroken)
                appendOrderParameter(orderBy)
                appendPagingParameters(pagingRequest)
            }
        }
    }

    /** A JSON-encoded list of all tags in the database. If a filter is given, it will only return
     * the ones containing the filter as substring
     *
     * @return a network response with tags
     * @see <a href="https://all.api.radio-browser.info/#List_of_tags">
     * */
    public suspend fun getTags(
        filter: String? = null,
        orderBy: LimitedOrderField = LimitedOrderField.Name,
        reverse: Boolean = false,
        hideBroken: Boolean = false,
        pagingRequest: PagingRequest? = null,
    ): RadioBrowserResponse<List<TagResponse>> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("tags", filter)
            buildUrlParameters {
                appendReverseParameter(reverse)
                appendHideBrokenParameter(hideBroken)
                appendOrderParameter(orderBy)
                appendPagingParameters(pagingRequest)
            }
        }
    }

    /** A list of radio stations that match the search. The variants with "exact" will only search
     *  for perfect matches, and others will search for the station whose attribute contains the search term.
     *
     * Please use Count station click API call to let the click be counted
     *
     * @return a network response with radio stations
     * @see <a href="https://all.api.radio-browser.info/#List_of_radio_stations">
     * */
    public suspend fun getStations(
        filter: StationFilter? = null,
        orderBy: OrderField = OrderField.Name,
        reverse: Boolean = false,
        hideBroken: Boolean = false,
        pagingRequest: PagingRequest? = null,
    ): RadioBrowserResponse<List<StationResponse>> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("stations") {
                when (filter) {
                    is StationFilter.Simple -> appendSimpleStationFilterPathSegments(filter)
                    is StationFilter.Advanced -> appendPathSegments("search")
                    null -> Unit
                }
            }
            buildFormParameters {
                appendReverseParameter(reverse)
                appendHideBrokenParameter(hideBroken)
                appendOrderParameter(orderBy)
                appendPagingParameters(pagingRequest)
                if (filter is StationFilter.Advanced) {
                    appendAdvancedStationFilterParameters(filter)
                }
            }
        }
    }

    /**
     * A list of radio stations that have an exact UUID match
     *
     * @return a network response with radio stations
     * @see <a href="https://all.api.radio-browser.info/#Search_radio_stations_by_uuid">
     */
    public suspend fun getStationsByUuids(
        stationUuids: List<String>
    ): RadioBrowserResponse<List<StationResponse>> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("stations", "byuuid")
            buildFormParameters {
                appendUuidsParameters(stationUuids)
            }
        }
    }

    /**
     * A list of radio stations that have an exact URL match
     *
     * @return a network response with radio stations
     * @see <a href="https://all.api.radio-browser.info/#Search_radio_stations_by_url">
     */
    public suspend fun getStationsByUrl(
        stationUrl: String,
    ): RadioBrowserResponse<List<StationResponse>> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("stations", "byurl")
            buildFormParameters {
                appendUrlParameter(stationUrl)
            }
        }
    }

    /**
     * A list of the stations that did not pass the connection test
     *
     * @return a network response with radio stations
     * @see <a href="https://all.api.radio-browser.info/#Broken_stations">
     */
    public suspend fun getBrokenStations(
        pagingRequest: PagingRequest? = null,
    ): RadioBrowserResponse<List<StationResponse>> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("stations", "broken")
            buildFormParameters {
                appendPagingParameters(pagingRequest)
            }
        }
    }

    /**
     * A list of old versions of stations from the last 30 days, and you can also retrieve
     * the history of a single station by its ID. They are not visible through any other API calls.
     * Station ID can be an ID or a station UUID
     *
     * @return a network response with radio stations
     * @see <a href="https://all.api.radio-browser.info/#Old_versions_of_stations">
     */
    public suspend fun getStationChanges(
        stationUuid: String? = null,
        lastChangeUuid: String? = null,
        limit: Int? = null,
    ): RadioBrowserResponse<List<StationResponse>> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("stations", "changed", stationUuid)
            buildFormParameters {
                appendLastChangeUuidParameter(lastChangeUuid)
                appendPagingLimit(limit)
            }
        }
    }

    /**
     * A list of the stations that are clicked the most/highest-voted/clicked recently/changed recently.
     * You can add a parameter with the number of wanted stations
     *
     * @return a network response with radio stations
     * @see <a href="https://all.api.radio-browser.info/#Stations_by_clicks">
     * @see <a href="https://all.api.radio-browser.info/#Stations_by_votes">
     * @see <a href="https://all.api.radio-browser.info/#Stations_by_recent_click">
     * @see <a href="https://all.api.radio-browser.info/#Stations_by_recently_changed">
     */
    public suspend fun getStationsBy(
        request: StationsByField,
        hideBroken: Boolean = false,
        pagingRequest: PagingRequest? = null,
    ): RadioBrowserResponse<List<StationResponse>> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("stations") {
                appendStationsBySegments(request)
            }
            buildFormParameters {
                appendHideBrokenParameter(hideBroken)
                appendPagingParameters(pagingRequest)
            }
        }
    }

    /** A list of station check results. If a station UUID is provided, the whole history will be returned.
     * If a station ID is not provided, a list of all last checks of all stations will be sent (without older check results)
     *
     * @return a network response with check results
     * @see <a href="https://all.api.radio-browser.info/#List_of_station_check_results">
     * */
    public suspend fun getStationChecks(
        stationUuid: String? = null,
        lastCheckUuid: String? = null,
        seconds: Int? = null,
        limit: Int? = null,
    ): RadioBrowserResponse<List<StationCheckResponse>> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("checks", stationUuid?.toString())
            buildFormParameters {
                appendLastCheckUuidParameter(lastCheckUuid)
                appendSecondsParameter(seconds)
                appendPagingLimit(limit)
            }
        }
    }

    /** A list of station clicks. If a station UUID is provided, only clicks of the station will be returned.
     * If a station UUID is not provided, a list of all clicks of all stations will be sent (chunksize 10000)
     *
     * @return a network response with station clicks
     * @see <a href="https://all.api.radio-browser.info/#List_of_station_clicks">
     * */
    public suspend fun getStationClicks(
        stationUuid: String? = null,
        lastClickUuid: String? = null,
        seconds: Int? = null,
    ): RadioBrowserResponse<List<StationClickResponse>> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("clicks", stationUuid?.toString())
            buildFormParameters {
                appendLastClickUuidParameter(lastClickUuid)
                appendSecondsParameter(seconds)
            }
        }
    }

    /** A list of steps that needed to be done to finish a station check.
     *
     * @return a network response with check step results
     * @see <a href="https://all.api.radio-browser.info/#List_of_station_check_steps">
     * */
    public suspend fun getStationCheckSteps(
        uuids: List<String>,
    ): RadioBrowserResponse<List<StationCheckStepResponse>> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("checksteps")
            buildFormParameters {
                appendUuidsParameters(uuids)
            }
        }
    }

    /** A list of streaming servers that support more detailed information. (e.g.: IceCast)
     *
     * @return a network response with streaming servers results
     * @see <a href="https://all.api.radio-browser.info/#List_of_streaming_servers">
     * */
    public suspend fun getStreamingServers(
        hideBroken: Boolean = false,
    ): RadioBrowserResponse<List<StreamingServerResponse>> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("streamingservers")
            buildUrlParameters {
                appendHideBrokenParameter(hideBroken)
            }
        }
    }

    /** Web service stats
     *
     * @return a network response with server stats
     * @see <a href="https://all.api.radio-browser.info/#Server_stats">
     * */
    public suspend fun getServerStats(): RadioBrowserResponse<ServerStatsResponse> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("stats")
        }
    }

    /** A list of server mirrors. A DNS look-up of all.api.radio-browser.info is performed followed
     * by a reverse one for every result getting from the first request. This should be done on
     * the client. ONLY USE THIS if your client is not able to do DNS look-ups
     *
     * @return a network response with servers
     * @see <a href="https://all.api.radio-browser.info/#Server_mirrors">
     * */
    public suspend fun getServerMirrors(): RadioBrowserResponse<List<ServerMirrorResponse>> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("servers")
        }
    }

    /** The current active server config
     *
     * @return a network response with server config
     * @see <a href="https://all.api.radio-browser.info/#Server_config">
     * */
    public suspend fun getServerConfig(): RadioBrowserResponse<ServerConfigResponse> {
        return client.safeRequest {
            method = HttpMethod.Get
            buildApiPathSegments("config")
        }
    }

    /**
     * Increase the click count of a station by one. This should be called everytime when a user
     * starts playing a stream to mark the stream more popular than others.
     * Every call to this endpoint from the same IP address and for the same station only gets counted once per day.
     * The call will return detailed information about the stream
     *
     * @return the station url and metadata
     * @see <a href="https://all.api.radio-browser.info/#Count_station_click">
     */
    public suspend fun stationUrl(
        stationUuid: String,
    ): RadioBrowserResponse<StationUrlResponse> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("url", stationUuid.toString())
        }
    }

    /**
     * Increase the vote count for the station by one. Can only be done by the same IP address
     * for one station every 10 minutes. If it works, the changed station will be returned as result
     *
     * @return the station vote result
     * @see <a href="https://all.api.radio-browser.info/#Vote_for_station">
     */
    public suspend fun stationVote(
        stationUuid: String,
    ): RadioBrowserResponse<StationVoteResponse> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("vote", stationUuid.toString())
        }
    }

    /** Add a radio station to the database
     *
     * @return the station add result
     * @see <a href="https://all.api.radio-browser.info/#Add_radio_station">
     * */
    public suspend fun addOrUpdateStation(
        request: AddStationRequest,
    ): RadioBrowserResponse<StationAddResultResponse> {
        return client.safeRequest {
            method = HttpMethod.Post
            buildApiPathSegments("add")
            buildFormParameters {
                appendAddStationParameters(request)
            }
        }
    }
}
