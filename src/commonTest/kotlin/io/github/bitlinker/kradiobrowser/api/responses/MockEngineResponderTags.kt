package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderTags : MockEngineResponder {
    override val body: String = """
        [
            {
                "name": "\"bob\"",
                "stationcount": 2
            },
            {
                "name": "\"bob\"dobbs",
                "stationcount": 1
            },
            {
                "name": "\"chicago's  greatest  hits \"",
                "stationcount": 1
            },
            {
                "name": "\"radio k\"",
                "stationcount": 1
            },
            {
                "name": "\"rockfords country q98.5\"",
                "stationcount": 1
            }
        ]
    """.trimIndent()

    override val headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}
