package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderConfig : MockEngineResponder {
    override val body: String = """
        {
            "check_enabled": true,
            "prometheus_exporter_enabled": true,
            "pull_servers": [
                "http://de1.api.radio-browser.info",
                "http://de2.api.radio-browser.info"
            ],
            "tcp_timeout_seconds": 10,
            "broken_stations_never_working_timeout_seconds": 172800,
            "broken_stations_timeout_seconds": 432000,
            "checks_timeout_seconds": 259200,
            "click_valid_timeout_seconds": 86400,
            "clicks_timeout_seconds": 259200,
            "mirror_pull_interval_seconds": 300,
            "update_caches_interval_seconds": 300,
            "server_name": "fi1",
            "server_location": "",
            "server_country_code": "FI",
            "check_retries": 5,
            "check_batchsize": 100,
            "check_pause_seconds": 60,
            "api_threads": 40,
            "cache_type": "redis",
            "cache_ttl": 600,
            "language_replace_filepath": "https://db.radio-browser.info/language-replace.csv",
            "language_to_code_filepath": "https://db.radio-browser.info/language-to-code.csv"
        }
    """.trimIndent()

    override val headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}