package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderStates : MockEngineResponder {
    override val body: String = """
        [
            {
                "name": "BC",
                "country": "Canada",
                "stationcount": 3
            },
            {
                "name": "British Columbia",
                "country": "Canada",
                "stationcount": 114
            },
            {
                "name": "Calgary",
                "country": "Canada",
                "stationcount": 2
            },
            {
                "name": "Canada",
                "country": "Canada",
                "stationcount": 6
            },
            {
                "name": "Cochrane",
                "country": "Canada",
                "stationcount": 1
            },
            {
                "name": "District of Columbia",
                "country": "Canada",
                "stationcount": 1
            },
            {
                "name": "Halifax, Nova Scotia",
                "country": "Canada",
                "stationcount": 2
            },
            {
                "name": "New Brunswick",
                "country": "Canada",
                "stationcount": 51
            },
            {
                "name": "Nova Scotia",
                "country": "Canada",
                "stationcount": 41
            },
            {
                "name": "Ontario, Richmond Hill",
                "country": "Canada",
                "stationcount": 1
            },
            {
                "name": "Parksville, Vancouver Island BC",
                "country": "Canada",
                "stationcount": 2
            },
            {
                "name": "Prince Edward Island",
                "country": "Canada",
                "stationcount": 9
            },
            {
                "name": "qubece",
                "country": "Canada",
                "stationcount": 1
            },
            {
                "name": "Quebec",
                "country": "Canada",
                "stationcount": 153
            },
            {
                "name": "Saskatchewan",
                "country": "Canada",
                "stationcount": 57
            }
        ]
    """.trimIndent()

    override val headers: Headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}