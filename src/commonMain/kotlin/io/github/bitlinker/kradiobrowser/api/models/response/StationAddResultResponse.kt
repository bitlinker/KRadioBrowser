package io.github.bitlinker.kradiobrowser.api.models.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The add station response model
 */
@Serializable
public data class StationAddResultResponse(
    @SerialName("ok")
    override val ok: Boolean,

    @SerialName("message")
    override val message: String? = null,

    @SerialName("uuid")
    val uuid: String?,
) : StationResultResponse