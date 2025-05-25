package io.github.bitlinker.kradiobrowser.api

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.plus
import io.ktor.http.protocolWithAuthority
import kotlin.test.assertEquals

fun MockEngine.assertRequestContentTypeJson() {
    assertEquals(
        ContentType.Application.Json.toString(),
        lastRequestHeaders[HttpHeaders.Accept]
    )
}

fun MockEngine.assertRequestUserAgent() {
    assertEquals(
        RadioBrowserConfig.DEFAULT_USER_AGENT,
        lastRequestHeaders[HttpHeaders.UserAgent]
    )
}

fun MockEngine.assertIsGet() {
    assertEquals(HttpMethod.Get, lastRequest.method)
}

fun MockEngine.assertIsPost() {
    assertEquals(HttpMethod.Post, lastRequest.method)
}

fun MockEngine.assertRequestHostAndPath(path: String) {
    assertEquals(RadioBrowserConfig.DEFAULT_API_URL, lastRequest.url.protocolWithAuthority)
    assertEquals(path, lastRequest.url.encodedPath)
}

fun MockEngine.assertRequestParameters(parameters: Parameters) {
    assertEquals(parameters, lastRequestFormParameters.plus(lastRequestUrlParameters))
}

private val MockEngine.lastRequest: HttpRequestData
    get() = requestHistory.last()

private val MockEngine.lastRequestHeaders: Headers
    get() = lastRequest.headers

private val MockEngine.lastRequestFormParameters: Parameters
    get() = (lastRequest.body as? FormDataContent)?.formData ?: Parameters.Empty

private val MockEngine.lastRequestUrlParameters: Parameters
    get() = lastRequest.url.parameters
