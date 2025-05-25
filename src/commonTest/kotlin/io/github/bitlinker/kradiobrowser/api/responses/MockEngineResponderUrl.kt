package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderUrl : MockEngineResponder {
    override val body: String = """
        {
            "ok": true,
            "message": "retrieved station url",
            "stationuuid": "4d381732-d8f9-4649-9e54-7ae35fc8c568",
            "name": "\r\nМихаил Круг Радио ",
            "url": "https://stream04.pcradio.ru/Michail_Krug-hi"
        }
    """.trimIndent()

    override val headers: Headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}