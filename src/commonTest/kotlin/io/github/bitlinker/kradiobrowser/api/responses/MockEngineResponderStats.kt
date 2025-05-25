package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderStats : MockEngineResponder {
    override val body = """
        {
            "supported_version": 1,
            "software_version": "0.7.31",
            "status": "OK",
            "stations": 54767,
            "stations_broken": 624,
            "tags": 11111,
            "clicks_last_hour": 5984,
            "clicks_last_day": 156943,
            "languages": 626,
            "countries": 238
        }
    """.trimIndent()

    override val headers: Headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}