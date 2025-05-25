package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderCodecs : MockEngineResponder {
    override val body: String = """
        [
            {
                "name": "AAC",
                "stationcount": 6786
            },
            {
                "name": "AAC+",
                "stationcount": 8426
            },
            {
                "name": "AAC+,H.264",
                "stationcount": 5
            },
            {
                "name": "AAC,H.264",
                "stationcount": 87
            },
            {
                "name": "FLV",
                "stationcount": 12
            }
        ]
    """.trimIndent()

    override val headers: Headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}