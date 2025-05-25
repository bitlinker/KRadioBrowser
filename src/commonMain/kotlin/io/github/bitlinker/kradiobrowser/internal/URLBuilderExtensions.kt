package io.github.bitlinker.kradiobrowser.internal

import io.github.bitlinker.kradiobrowser.api.models.request.AddStationRequest
import io.github.bitlinker.kradiobrowser.api.models.request.LimitedOrderField
import io.github.bitlinker.kradiobrowser.api.models.request.MatchingFilter
import io.github.bitlinker.kradiobrowser.api.models.request.OrderField
import io.github.bitlinker.kradiobrowser.api.models.request.PagingRequest
import io.github.bitlinker.kradiobrowser.api.models.request.StationFilter
import io.github.bitlinker.kradiobrowser.api.models.request.StationFilter.Simple
import io.github.bitlinker.kradiobrowser.api.models.request.StationsByField
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.setBody
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments

internal fun ParametersBuilder.appendPagingParameters(pagingRequest: PagingRequest?) {
    if (pagingRequest != null) {
        append("offset", pagingRequest.offset.toString())
        append("limit", pagingRequest.limit.toString())
    }
}

internal fun ParametersBuilder.appendPagingLimit(limit: Int?) {
    if (limit != null) {
        append("limit", limit.toString())
    }
}

internal fun ParametersBuilder.appendReverseParameter(reverse: Boolean) {
    append("reverse", reverse.toString())
}

internal fun ParametersBuilder.appendHideBrokenParameter(hideBroken: Boolean) {
    append("hidebroken", hideBroken.toString())
}

internal fun ParametersBuilder.appendSecondsParameter(seconds: Int? = null) {
    if (seconds != null && seconds > 0) {
        append("seconds", seconds.toString())
    }
}

internal fun ParametersBuilder.appendLastCheckUuidParameter(uuid: String?) {
    if (uuid != null) {
        append("lastcheckuuid", uuid)
    }
}

internal fun ParametersBuilder.appendLastClickUuidParameter(uuid: String?) {
    if (uuid != null) {
        append("lastclickuuid", uuid)
    }
}

internal fun ParametersBuilder.appendLastChangeUuidParameter(uuid: String?) {
    if (uuid != null) {
        append("lastchangeuuid", uuid)
    }
}

internal fun ParametersBuilder.appendOrderParameter(orderField: OrderField) {
    append(
        "order", when (orderField) {
            OrderField.Name -> "name"
            OrderField.Url -> "url"
            OrderField.Homepage -> "homepage"
            OrderField.FavIcon -> "favicon"
            OrderField.Tags -> "tags"
            OrderField.Country -> "country"
            OrderField.State -> "state"
            OrderField.Language -> "language"
            OrderField.Votes -> "votes"
            OrderField.Codec -> "codec"
            OrderField.Bitrate -> "bitrate"
            OrderField.LastCheck -> "lastcheckok"
            OrderField.LastCheckTime -> "lastchecktime"
            OrderField.ClickTimestamp -> "clicktimestamp"
            OrderField.ClickCount -> "clickcount"
            OrderField.ClickTrend -> "clicktrend"
            OrderField.ChangeTimestamp -> "changetimestamp"
            OrderField.Random -> "random"
            OrderField.StationCount -> "stationcount"
        }
    )
}

internal fun ParametersBuilder.appendOrderParameter(limitedOrderField: LimitedOrderField) {
    append(
        "order", when (limitedOrderField) {
            LimitedOrderField.Name -> "name"
            LimitedOrderField.StationCount -> "stationcount"
        }
    )
}

internal fun URLBuilder.appendSimpleStationFilterPathSegments(filter: Simple) {
    val bySegment = when (filter.field) {
        Simple.Field.Uuid -> "byuuid"
        Simple.Field.Name -> "byname"
        Simple.Field.Codec -> "bycodec"
        Simple.Field.Country -> "bycountry"
        Simple.Field.CountryCode -> "bycountrycode"
        Simple.Field.State -> "bystate"
        Simple.Field.Language -> "bylanguage"
        Simple.Field.Tag -> "bytag"
    } + if (filter.matchingFilter.isExact && filter.field != Simple.Field.Uuid) "exact" else ""
    appendPathSegments(bySegment, filter.matchingFilter.query)
}

internal fun URLBuilder.appendStationsBySegments(request: StationsByField) {
    val bySegment = when (request) {
        StationsByField.TopVotes -> "topvote"
        StationsByField.TopClicks -> "topclick"
        StationsByField.LastClick -> "lastclick"
        StationsByField.LastChange -> "lastchange"
    }
    appendPathSegments(bySegment)
}

internal fun ParametersBuilder.appendAdvancedStationFilterParameters(filter: StationFilter.Advanced) {
    with(filter) {
        name?.let { appendMatchingFilterParameter(it, "name") }
        country?.let { appendMatchingFilterParameter(it, "country") }
        countryCode?.let { append("countrycode", it) }
        state?.let { appendMatchingFilterParameter(it, "state") }
        language?.let { appendMatchingFilterParameter(it, "language") }
        tag?.let { appendMatchingFilterParameter(it, "tag") }
        tagList.let { tagList ->
            if (tagList.isNotEmpty()) {
                append("tagList", tagList.joinToString(separator = ","))
            }
        }
        codec?.let { append("codec", it) }
        bitrateMin?.let { append("bitrateMin", it.toString()) }
        bitrateMax?.let { append("bitrateMax", it.toString()) }
        hasGeoInfo?.let { append("has_geo_info", it.toString()) }
        hasExtendedInfo?.let { append("has_extended_info", it.toString()) }
        isHttps?.let { append("is_https", it.toString()) }
        geoLat?.let { append("geo_lat", it.toString()) }
        geoLon?.let { append("geo_long", it.toString()) }
        geoDistance?.let { append("geo_distance", it.toString()) }
    }
}

private fun ParametersBuilder.appendMatchingFilterParameter(filter: MatchingFilter, name: String) {
    append(name + if (filter.isExact) "Exact" else "", filter.query)
}

internal fun ParametersBuilder.appendUuidsParameters(uuids: List<String>) {
    append("uuids", uuids.joinToString(separator = ","))
}

internal fun ParametersBuilder.appendUrlParameter(url: String) {
    append("url", url)
}

internal fun ParametersBuilder.appendAddStationParameters(request: AddStationRequest) {
    append("name", request.name)
    append("url", request.url)
    request.homepage?.let { append("homepage", it) }
    request.favicon?.let { append("favicon", it) }
    request.countryCode?.let { append("countrycode", it) }
    @Suppress("DEPRECATION")
    request.state?.let { append("state", it) }
    request.iso_3166_2?.let { append("iso_3166_2", it) }
    request.language?.let { append("language", it) }
    request.tags.let { tags ->
        if (tags.isNotEmpty()) {
            append("tags", tags.joinToString(separator = ","))
        }
    }
    request.geoLat?.let { append("geo_lat", it.toString()) }
    request.geoLon?.let { append("geo_long", it.toString()) }
}

internal fun HttpRequestBuilder.buildUrlParameters(block: ParametersBuilder.() -> Unit) {
    url.parameters.block()
}

internal fun HttpRequestBuilder.buildFormParameters(block: ParametersBuilder.() -> Unit) {
    setBody(
        FormDataContent(
            ParametersBuilder().apply {
                block()
            }.build()
        )
    )
}

internal fun HttpRequestBuilder.buildApiPathSegments(
    vararg components: String?,
    block: (URLBuilder.() -> Unit)? = null,
) {
    url {
        appendPathSegments("json")
        appendPathSegments(components.toList().filterNotNull())
        block?.invoke(this)
    }
}