package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The station voting response model
 */
@Serializable
public data class StationVoteResponse(
    @SerialName("ok")
    override val ok: Boolean,

    @SerialName("message")
    override val message: String? = null,
): StationResultResponse
