package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderCountries : MockEngineResponder {
    override val body = """
        [
            {
                "name": "Andorra",
                "iso_3166_1": "AD",
                "stationcount": 10
            },
            {
                "name": "The United Arab Emirates",
                "iso_3166_1": "AE",
                "stationcount": 762
            },
            {
                "name": "Afghanistan",
                "iso_3166_1": "AF",
                "stationcount": 144
            },
            {
                "name": "Antigua And Barbuda",
                "iso_3166_1": "AG",
                "stationcount": 8
            },
            {
                "name": "Anguilla",
                "iso_3166_1": "AI",
                "stationcount": 6
            },
            {
                "name": "Albania",
                "iso_3166_1": "AL",
                "stationcount": 49
            },
            {
                "name": "Armenia",
                "iso_3166_1": "AM",
                "stationcount": 14
            },
            {
                "name": "Angola",
                "iso_3166_1": "AO",
                "stationcount": 8
            },
            {
                "name": "Antarctica",
                "iso_3166_1": "AQ",
                "stationcount": 12
            },
            {
                "name": "Argentina",
                "iso_3166_1": "AR",
                "stationcount": 854
            },
            {
                "name": "American Samoa",
                "iso_3166_1": "AS",
                "stationcount": 13
            },
            {
                "name": "Austria",
                "iso_3166_1": "AT",
                "stationcount": 334
            },
            {
                "name": "Australia",
                "iso_3166_1": "AU",
                "stationcount": 1884
            },
            {
                "name": "Aruba",
                "iso_3166_1": "AW",
                "stationcount": 10
            },
            {
                "name": "Aland Islands",
                "iso_3166_1": "AX",
                "stationcount": 2
            },
            {
                "name": "Azerbaijan",
                "iso_3166_1": "AZ",
                "stationcount": 41
            }
        ]
    """.trimIndent()

    override val headers: Headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}