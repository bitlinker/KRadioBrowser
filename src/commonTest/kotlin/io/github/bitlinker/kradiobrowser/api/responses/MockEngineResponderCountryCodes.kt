package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderCountryCodes : MockEngineResponder {
    override val body: String = """
        [
            {
                "name": "AD",
                "stationcount": 10
            },
            {
                "name": "AE",
                "stationcount": 762
            },
            {
                "name": "AF",
                "stationcount": 143
            },
            {
                "name": "AG",
                "stationcount": 8
            },
            {
                "name": "AI",
                "stationcount": 6
            }
        ]
    """.trimIndent()

    override val headers: Headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}