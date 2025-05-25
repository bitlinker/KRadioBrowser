package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderLanguages : MockEngineResponder  {
    override val body: String = """
        [
            {
                "name": "belarusian",
                "iso_639": "be",
                "stationcount": 31
            },
            {
                "name": "carpatho-rusyn",
                "iso_639": null,
                "stationcount": 4
            },
            {
                "name": "english russian",
                "iso_639": null,
                "stationcount": 2
            },
            {
                "name": "english/ españa/ russian /francia /italia",
                "iso_639": null,
                "stationcount": 9
            },
            {
                "name": "english/ russian /francia /españa",
                "iso_639": null,
                "stationcount": 4
            }
        ]
    """.trimIndent()

    override val headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}