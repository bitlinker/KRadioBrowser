package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.Headers

interface MockEngineResponder {
    val body: String
    val headers: Headers
}