package io.github.bitlinker.kradiobrowser.api

import io.github.bitlinker.kradiobrowser.api.models.response.RadioBrowserResponse
import io.github.bitlinker.kradiobrowser.api.models.response.successOrNull
import io.github.bitlinker.kradiobrowser.internal.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.engine.mock.respondError
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HttpClientExtensionsTest {
    private val mockEngine = MockEngine.Companion { request ->
        val path = request.url.encodedPath
        when {
            path.startsWith(URL_SUCCESS) -> respondOk(BODY_SUCCESS)
            path.startsWith(URL_SUCCESS_JSON) -> respondJsonOk(BODY_GOOD_JSON)
            path.startsWith(URL_ERROR_NOT_FOUND) -> respondError(HttpStatusCode.Companion.NotFound)
            path.startsWith(URL_ERROR_BAD_GATEWAY) -> respondError(HttpStatusCode.Companion.BadGateway)
            path.startsWith(URL_ERROR_EXCEPTION) -> throw IllegalStateException(TEST_EXCEPTION)
            path.startsWith(URL_ERROR_JSON_SERIALIZATION) -> respondJsonOk(BODY_BAD_JSON)
            path.startsWith(URL_ERROR_REQUEST_TIMEOUT) -> {
                delay(1000)
                respondOk()
            }

            path.startsWith(URL_ERROR_SOCKET_TIMEOUT) -> {
                throw SocketTimeoutException("")
            }

            else -> respondBadRequest()
        }
    }

    private fun MockRequestHandleScope.respondJsonOk(content: String): HttpResponseData = respond(
        content = content,
        status = HttpStatusCode.Companion.OK,
        headers = headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }
    )

    private val client = HttpClient(mockEngine) {
        install(DefaultRequest.Plugin) {
            url("https://test.com")
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 500
        }
    }

    @Test
    fun `Respond successfully`() {
        runTest {
            val response = client.safeRequest<String> {
                method = HttpMethod.Companion.Get
                url.appendPathSegments(URL_SUCCESS)
            }.successOrNull()
            assertEquals(BODY_SUCCESS, response)
        }
    }

    @Test
    fun `Respond successfully with Json`() {
        runTest {
            val response = client.safeRequest<TestJson> {
                method = HttpMethod.Companion.Get
                url.appendPathSegments(URL_SUCCESS_JSON)
            }.successOrNull()
            assertEquals(1234, response?.id)
        }
    }

    @Test
    fun `Respond with HTTP client error`() {
        runTest {
            val response = client.safeRequest<String> {
                method = HttpMethod.Companion.Get
                url.appendPathSegments(URL_ERROR_NOT_FOUND)
            }
            assertTrue(response is RadioBrowserResponse.Error.Network.Http)
            assertEquals(HttpStatusCode.Companion.NotFound.value, response.code)
            assertEquals(HttpStatusCode.Companion.NotFound.description, response.body)
            assertFalse(response.isServerError)
        }
    }

    @Test
    fun `Respond with HTTP server error`() {
        runTest {
            val response = client.safeRequest<String> {
                method = HttpMethod.Companion.Get
                url.appendPathSegments(URL_ERROR_BAD_GATEWAY)
            }
            assertTrue(response is RadioBrowserResponse.Error.Network.Http)
            assertEquals(HttpStatusCode.Companion.BadGateway.value, response.code)
            assertEquals(HttpStatusCode.Companion.BadGateway.description, response.body)
            assertTrue(response.isServerError)
        }
    }

    @Test
    fun `Generic exception during request`() {
        runTest {
            val response = client.safeRequest<String> {
                method = HttpMethod.Companion.Get
                url.appendPathSegments(URL_ERROR_EXCEPTION)
            }
            assertTrue(response is RadioBrowserResponse.Error.Generic)
            assertEquals(TEST_EXCEPTION, response.exception.message)
        }
    }

    @Test
    fun `Serialization exception during request`() {
        runTest {
            val response = client.safeRequest<TestJson> {
                method = HttpMethod.Companion.Get
                url.appendPathSegments(URL_ERROR_JSON_SERIALIZATION)
            }
            assertTrue(response is RadioBrowserResponse.Error.Serialization)
        }
    }

    @Test
    fun `Network request timeout`() {
        runTest {
            val response = client.safeRequest<String> {
                method = HttpMethod.Companion.Get
                url.appendPathSegments(URL_ERROR_REQUEST_TIMEOUT)
            }
            assertTrue(response is RadioBrowserResponse.Error.Network.Timeout)
        }
    }

    @Test
    fun `Network scoket timeout`() {
        runTest {
            val response = client.safeRequest<String> {
                method = HttpMethod.Companion.Get
                url.appendPathSegments(URL_ERROR_SOCKET_TIMEOUT)
            }
            assertTrue(response is RadioBrowserResponse.Error.Network.Timeout)
        }
    }
}

@Serializable
private data class TestJson(
    val id: Int,
)

private const val URL_SUCCESS_JSON = "/successJson"
private const val URL_SUCCESS = "/successString"
private const val URL_ERROR_NOT_FOUND = "/errorNotFound"
private const val URL_ERROR_BAD_GATEWAY = "/errorBadGateway"
private const val URL_ERROR_EXCEPTION = "/errorException"
private const val URL_ERROR_REQUEST_TIMEOUT = "/errorRequestTimeout"
private const val URL_ERROR_SOCKET_TIMEOUT = "/errorSocketTimeout"
private const val URL_ERROR_JSON_SERIALIZATION = "/errorJsonSerialization"
private const val BODY_SUCCESS = "success"
private const val BODY_BAD_JSON = "Bad json"
private val BODY_GOOD_JSON = """
    {
        "id" : 1234
    }
    """.trimIndent()
private const val TEST_EXCEPTION = "Test exception"

