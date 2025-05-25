package io.github.bitlinker.kradiobrowser.api.models.response

/**
 * THe common network response wrapper
 */
public sealed interface RadioBrowserResponse<out T : Any> {
    public data class Success<T : Any>(val data: T) : RadioBrowserResponse<T>

    public sealed interface Error : RadioBrowserResponse<Nothing> {
        public val exception: Exception?

        public interface Network : Error {
            public data class Http(
                val code: Int,
                val body: String,
                val isServerError: Boolean,
                override val exception: Exception? = null
            ) : Network

            public data class Timeout(override val exception: Exception) : Network
            public data class Generic(override val exception: Exception) : Network
        }

        public data class Serialization(override val exception: Exception) : Error
        public data class Generic(override val exception: Exception) : Error
    }
}

public fun <T : Any> RadioBrowserResponse<T>.successOrNull(): T? {
    return (this as? RadioBrowserResponse.Success)?.data
}