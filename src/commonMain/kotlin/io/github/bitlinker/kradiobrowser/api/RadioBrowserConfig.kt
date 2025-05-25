package io.github.bitlinker.kradiobrowser.api

import io.github.bitlinker.kradiobrowser.BuildKonfig

/**
 * The API configuration object
 * @param apiUri the base radio-browser API endpoint URL
 * @param userAgent the client application user-agent to send with every request
 */
public data class RadioBrowserConfig(
    val apiUri: String = DEFAULT_API_URL,
    val userAgent: String = DEFAULT_USER_AGENT,
) {
    public companion object {
        public const val DEFAULT_API_URL: String = "https://all.api.radio-browser.info"
        public val DEFAULT_USER_AGENT: String =
            "${BuildKonfig.projectName}/${BuildKonfig.projectVersion}"
    }
}