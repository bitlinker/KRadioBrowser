package io.github.bitlinker.kradiobrowser.internal

import io.github.bitlinker.kradiobrowser.api.models.response.RadioBrowserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

internal suspend inline fun <reified T : Any> HttpClient.safeRequest(block: HttpRequestBuilder.() -> Unit): RadioBrowserResponse<T> {
    val builder = HttpRequestBuilder()
    return try {
        val response = request(builder.apply(block))
        if (response.status.isSuccess()) {
            val body = response.body<T>()
            RadioBrowserResponse.Success(body)
        } else {
            RadioBrowserResponse.Error.Network.Http(
                code = response.status.value,
                isServerError = response.status.value >= 500,
                body = response.bodyAsText(),
            )
        }
    } catch (e: ContentConvertException) {
        RadioBrowserResponse.Error.Serialization(e)
    } catch (e: SerializationException) {
        RadioBrowserResponse.Error.Serialization(e)
    } catch (e: SocketTimeoutException) {
        RadioBrowserResponse.Error.Network.Timeout(e)
    } catch (e: HttpRequestTimeoutException) {
        RadioBrowserResponse.Error.Network.Timeout(e)
    } catch (e: IOException) {
        RadioBrowserResponse.Error.Network.Generic(e)
    } catch (e: Exception) {
        RadioBrowserResponse.Error.Generic(e)
    }
}