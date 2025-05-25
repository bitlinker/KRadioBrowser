package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The server configuration response model
 */
@Serializable
public data class ServerConfigResponse(
    @SerialName("check_enabled")
    val checkEnabled: Boolean? = null,

    @SerialName("prometheus_exporter_enabled")
    val prometheusExporterEnabled: Boolean? = null,

    @SerialName("pull_servers")
    val pullServers: List<String> = emptyList(),

    @SerialName("tcp_timeout_seconds")
    val tcpTimeoutSeconds: Long? = null,

    @SerialName("broken_stations_never_working_timeout_seconds")
    val brokenStationsNeverWorkingTimeoutSeconds: Long? = null,

    @SerialName("broken_stations_timeout_seconds")
    val brokenStationsTimeoutSeconds: Long? = null,

    @SerialName("checks_timeout_seconds")
    val checksTimeoutSeconds: Long? = null,

    @SerialName("click_valid_timeout_seconds")
    val clickValidTimeoutSeconds: Long? = null,

    @SerialName("clicks_timeout_seconds")
    val clicksTimeoutSeconds: Long? = null,

    @SerialName("mirror_pull_interval_seconds")
    val mirrorPullIntervalSeconds: Long? = null,

    @SerialName("update_caches_interval_seconds")
    val updateCachesIntervalSeconds: Long? = null,

    @SerialName("server_name")
    val serverName: String? = null,

    @SerialName("server_location")
    val serverLocation: String? = null,

    @SerialName("server_country_code")
    val serverCountryCode: String? = null,

    @SerialName("check_retries")
    val checkRetries: Int? = null,

    @SerialName("check_batchsize")
    val checkBatchsize: Int? = null,

    @SerialName("check_pause_seconds")
    val checkPauseSeconds: Int? = null,

    @SerialName("api_threads")
    val apiThreads: Int? = null,

    @SerialName("cache_type")
    val cacheType: String? = null,

    @SerialName("cache_ttl")
    val cacheTtl: Int? = null,

    @SerialName("language_replace_filepath")
    val languageReplaceFilepath: String? = null,

    @SerialName("language_to_code_filepath")
    val languageToCodeFilepath: String? = null,
)
