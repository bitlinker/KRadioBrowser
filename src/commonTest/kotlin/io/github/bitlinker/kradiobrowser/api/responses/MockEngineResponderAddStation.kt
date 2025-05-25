package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderAddStation : MockEngineResponder {

    override val body: String = """
        {
            "ok": true,
            "message": "added station successfully",
            "uuid": "919e829b-8a24-43b8-ac69-ba10f2b5dd0b"
        }
    """.trimIndent()

    override val headers: Headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}