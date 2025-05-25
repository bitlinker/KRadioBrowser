package io.github.bitlinker.kradiobrowser.api

import io.github.bitlinker.kradiobrowser.api.models.request.AddStationRequest
import io.github.bitlinker.kradiobrowser.api.models.request.LimitedOrderField
import io.github.bitlinker.kradiobrowser.api.models.request.MatchingFilter
import io.github.bitlinker.kradiobrowser.api.models.request.OrderField
import io.github.bitlinker.kradiobrowser.api.models.request.PagingRequest
import io.github.bitlinker.kradiobrowser.api.models.request.StationFilter
import io.github.bitlinker.kradiobrowser.api.models.request.StationsByField
import io.github.bitlinker.kradiobrowser.api.models.response.CodecResponse
import io.github.bitlinker.kradiobrowser.api.models.response.CountryCodeResponse
import io.github.bitlinker.kradiobrowser.api.models.response.CountryResponse
import io.github.bitlinker.kradiobrowser.api.models.response.LanguageResponse
import io.github.bitlinker.kradiobrowser.api.models.response.RadioBrowserResponse
import io.github.bitlinker.kradiobrowser.api.models.response.ServerConfigResponse
import io.github.bitlinker.kradiobrowser.api.models.response.ServerMirrorResponse
import io.github.bitlinker.kradiobrowser.api.models.response.ServerStatsResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StateResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationAddResultResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationCheckResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationCheckStepResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationClickResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationUrlResponse
import io.github.bitlinker.kradiobrowser.api.models.response.StationVoteResponse
import io.github.bitlinker.kradiobrowser.api.models.response.TagResponse
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponder
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderAddStation
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderChecks
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderChecksteps
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderCodecs
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderConfig
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderCountries
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderCountryCodes
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderLanguages
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderServers
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderStates
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderStations
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderStats
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderTags
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderUrl
import io.github.bitlinker.kradiobrowser.api.responses.MockEngineResponderVote
import io.github.bitlinker.kradiobrowser.api.responses.MockResponderStationClicks
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RadioBrowserApiTest {
    var mockEngineResponder: MockEngineResponder? = null

    val mockEngine = MockEngine.Companion { request ->
        mockEngineResponder?.let { responder ->
            respond(
                content = ByteReadChannel(responder.body),
                status = HttpStatusCode.Companion.OK,
                headers = responder.headers,
            )
        } ?: respondError(HttpStatusCode.Companion.NotFound)
    }

    private val api = RadioBrowserApi(
        config = RadioBrowserConfig(),
        baseClient = HttpClient(mockEngine)
    )

    @Test
    fun `Get countries without filter and parameters`() {
        runTest {
            mockEngineResponder = MockEngineResponderCountries

            val response = api.getCountries()

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/countries")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("order", "name")
                append("reverse", "false")
                append("hidebroken", "false")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<CountryResponse>>>(response)
            val countries = response.data
            assertEquals(16, countries.size)
            val firstCountry = countries.first()
            assertEquals("Andorra", firstCountry.name)
            assertEquals("AD", firstCountry.code)
            assertEquals(10, firstCountry.stationCount)
        }
    }

    @Test
    fun `Get countries with filter and parameters`() {
        runTest {
            mockEngineResponder = MockEngineResponderCountries

            val response = api.getCountries(
                filter = "myFilter",
                orderBy = LimitedOrderField.StationCount,
                reverse = true,
                hideBroken = true,
                pagingRequest = PagingRequest(1, 5),
            )

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/countries/myFilter")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("order", "stationcount")
                append("reverse", "true")
                append("hidebroken", "true")
                append("offset", 1.toString())
                append("limit", 5.toString())
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<CountryResponse>>>(response)
            val countries = response.data
            assertEquals(16, countries.size)
            val firstCountry = countries.first()
            assertEquals("Andorra", firstCountry.name)
            assertEquals("AD", firstCountry.code)
            assertEquals(10, firstCountry.stationCount)
        }
    }

    @Test
    fun `Get tags with filter and parameters`() {
        runTest {
            mockEngineResponder = MockEngineResponderTags

            val response = api.getTags(
                filter = "myFilter",
                orderBy = LimitedOrderField.Name,
                reverse = true,
                hideBroken = true,
                pagingRequest = PagingRequest(1, 5),
            )

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/tags/myFilter")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("order", "name")
                append("reverse", "true")
                append("hidebroken", "true")
                append("offset", 1.toString())
                append("limit", 5.toString())
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<TagResponse>>>(response)
            val tags = response.data
            assertEquals(5, tags.size)
            val firstTag = tags.first()
            assertEquals("\"bob\"", firstTag.name)
            assertEquals(2, firstTag.stationCount)
        }
    }

    @Test
    fun `Get languages with filter and parameters`() {
        runTest {
            mockEngineResponder = MockEngineResponderLanguages

            val response = api.getLanguages(
                filter = "myFilter",
                orderBy = LimitedOrderField.Name,
                reverse = true,
                hideBroken = true,
                pagingRequest = PagingRequest(1, 5),
            )

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/languages/myFilter")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("order", "name")
                append("reverse", "true")
                append("hidebroken", "true")
                append("offset", 1.toString())
                append("limit", 5.toString())
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<LanguageResponse>>>(response)
            val languages = response.data
            assertEquals(5, languages.size)
            val firstLanguage = languages.first()
            assertEquals("belarusian", firstLanguage.name)
            assertEquals("be", firstLanguage.code)
            assertEquals(31, firstLanguage.stationCount)
        }
    }

    @Suppress("DEPRECATION")
    @Test
    fun `Get countrycodes with filter and parameters`() {
        runTest {
            mockEngineResponder = MockEngineResponderCountryCodes

            val response = api.getCountryCodes(
                filter = "myFilter",
                orderBy = LimitedOrderField.Name,
                reverse = true,
                hideBroken = true,
                pagingRequest = PagingRequest(1, 5),
            )

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/countrycodes/myFilter")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("order", "name")
                append("reverse", "true")
                append("hidebroken", "true")
                append("offset", 1.toString())
                append("limit", 5.toString())
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<CountryCodeResponse>>>(response)
            val countrycodes = response.data
            assertEquals(5, countrycodes.size)
            val firstCountryCode = countrycodes.first()
            assertEquals("AD", firstCountryCode.name)
            assertEquals(10, firstCountryCode.stationCount)
        }
    }

    @Test
    fun `Get codecs with filter and parameters`() {
        runTest {
            mockEngineResponder = MockEngineResponderCodecs

            val response = api.getCodecs(
                filter = "myFilter",
                orderBy = LimitedOrderField.Name,
                reverse = true,
                hideBroken = true,
                pagingRequest = PagingRequest(1, 5),
            )

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/codecs/myFilter")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("order", "name")
                append("reverse", "true")
                append("hidebroken", "true")
                append("offset", 1.toString())
                append("limit", 5.toString())
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<CodecResponse>>>(response)
            val countrycodes = response.data
            assertEquals(5, countrycodes.size)
            val firstCodec = countrycodes.first()
            assertEquals("AAC", firstCodec.name)
            assertEquals(6786, firstCodec.stationCount)
        }
    }

    @Test
    fun `Get states with filter and parameters`() {
        runTest {
            mockEngineResponder = MockEngineResponderStates

            val response = api.getStates(
                filter = "myFilter",
                country = "Canada",
                orderBy = LimitedOrderField.Name,
                reverse = true,
                hideBroken = true,
                pagingRequest = PagingRequest(1, 5),
            )

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/states/Canada/myFilter")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("order", "name")
                append("reverse", "true")
                append("hidebroken", "true")
                append("offset", 1.toString())
                append("limit", 5.toString())
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StateResponse>>>(response)
            val states = response.data
            assertEquals(15, states.size)
            val firstState = states.first()
            assertEquals("BC", firstState.name)
            assertEquals("Canada", firstState.country)
            assertEquals(3, firstState.stationCount)
        }
    }

    @Test
    fun `Getting server stats`() {
        runTest {
            mockEngineResponder = MockEngineResponderStats

            val response = api.getServerStats()

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/stats")
            mockEngine.assertRequestParameters(Parameters.Companion.Empty)
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<ServerStatsResponse>>(response)
            val stats = response.data
            assertEquals(1, stats.supportedVersion)
            assertEquals("0.7.31", stats.softwareVersion)
            assertEquals("OK", stats.status)
            assertEquals(54767, stats.stations)
            assertEquals(624, stats.stationsBroken)
            assertEquals(11111, stats.tags)
            assertEquals(5984, stats.clicksLastHour)
            assertEquals(156943, stats.clicksLastDay)
            assertEquals(626, stats.languages)
            assertEquals(238, stats.countries)
        }
    }

    @Test
    fun `Getting server config`() {
        runTest {
            mockEngineResponder = MockEngineResponderConfig

            val response = api.getServerConfig()

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/config")
            mockEngine.assertRequestParameters(Parameters.Companion.Empty)
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<ServerConfigResponse>>(response)
            val config = response.data
            assertEquals(true, config.checkEnabled)
            assertEquals(true, config.prometheusExporterEnabled)
            assertEquals(
                listOf(
                    "http://de1.api.radio-browser.info",
                    "http://de2.api.radio-browser.info",
                ), config.pullServers
            )
            assertEquals(10, config.tcpTimeoutSeconds)
            assertEquals(172800, config.brokenStationsNeverWorkingTimeoutSeconds)
            assertEquals(432000, config.brokenStationsTimeoutSeconds)
            assertEquals(259200, config.checksTimeoutSeconds)
            assertEquals(86400, config.clickValidTimeoutSeconds)
            assertEquals(259200, config.clicksTimeoutSeconds)
            assertEquals(300, config.mirrorPullIntervalSeconds)
            assertEquals(300, config.updateCachesIntervalSeconds)
            assertEquals("fi1", config.serverName)
            assertEquals("", config.serverLocation)
            assertEquals("FI", config.serverCountryCode)
            assertEquals(5, config.checkRetries)
            assertEquals(100, config.checkBatchsize)
            assertEquals(60, config.checkPauseSeconds)
            assertEquals(40, config.apiThreads)
            assertEquals("redis", config.cacheType)
            assertEquals(600, config.cacheTtl)
            assertEquals(
                "https://db.radio-browser.info/language-replace.csv",
                config.languageReplaceFilepath
            )
            assertEquals(
                "https://db.radio-browser.info/language-to-code.csv",
                config.languageToCodeFilepath
            )
        }
    }

    @Test
    fun `Getting server mirrors list`() {
        runTest {
            mockEngineResponder = MockEngineResponderServers

            val response = api.getServerMirrors()

            mockEngine.assertIsGet()
            mockEngine.assertRequestHostAndPath("/json/servers")
            mockEngine.assertRequestParameters(Parameters.Companion.Empty)
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<ServerMirrorResponse>>>(response)
            val mirrors = response.data
            assertEquals(6, mirrors.size)
            val firstMirror = mirrors.first()
            assertEquals("de2.api.radio-browser.info", firstMirror.name)
            assertEquals("2a01:4f8:c2c:f004::1", firstMirror.ip)
        }
    }

    @Suppress("DEPRECATION")
    @Test
    fun `Get stations without filters`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStations()

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("order", "name")
                append("reverse", "false")
                append("hidebroken", "false")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
            val stations = response.data
            assertEquals(5, stations.size)
            val station = stations.first()
            assertEquals("bc59b6f8-ee6d-41f4-8662-7b7a46ed0347", station.changeUuid)
            assertEquals("ea8059be-d119-4de3-b27b-0d9bd6aedb17", station.stationUuid)
            assertEquals("1ec6ee09-5bd7-4657-85bc-ebcb35bd8dad", station.serverUuid)
            assertEquals("Adroit Jazz Underground", station.name)
            assertEquals("https://icecast.walmradio.com:8443/jazz", station.url)
            assertEquals("https://icecast.walmradio.com:8443/jazz", station.urlResolved)
            assertEquals("https://walmradio.com/jazz", station.homepage)
            assertEquals("https://icecast.walmradio.com:8443/jazz.jpg", station.favicon)
            assertEquals(
                "avant-garde,bebop,big band,bop,combos,contemporary,contemporary jazz,cool,cool jazz,free jazz,fusion,hard bop,hd,mainstream,mainstream jazz,modern,modern big band,post-bop,straight-ahead,walm,west coast",
                station.tags
            )
            assertEquals("The United States Of America", station.country)
            assertEquals("US", station.countryCode)
            assertEquals("Wisconsin", station.state)
            assertEquals("US-NY", station.iso31662)
            assertEquals("english", station.language)
            assertEquals("en", station.languageCodes)
            assertEquals(133271, station.votes)
            assertEquals(Instant.Companion.parse("2025-04-23T08:07:48Z"), station.lastchangetime)
            assertEquals(
                Instant.Companion.parse("2025-04-23T08:07:48Z"),
                station.lastchangetimeIso8601
            )
            assertEquals("MP3", station.codec)
            assertEquals(320, station.bitrate)
            assertEquals(0, station.hls)
            assertEquals(1, station.lastCheckOk)
            assertEquals(Instant.Companion.parse("2025-05-17T13:13:28Z"), station.lastCheckTime)
            assertEquals(
                Instant.Companion.parse("2025-05-17T13:13:28Z"),
                station.lastCheckTimeIso8601
            )
            assertEquals(Instant.Companion.parse("2025-05-17T13:13:28Z"), station.lastCheckOkTime)
            assertEquals(
                Instant.Companion.parse("2025-05-17T13:13:28Z"),
                station.lastCheckOkTimeIso8601
            )
            assertEquals(
                Instant.Companion.parse("2025-05-17T12:34:30Z"),
                station.lastLocalCheckTime
            )
            assertEquals(
                Instant.Companion.parse("2025-05-17T12:34:30Z"),
                station.lastLocalCheckTimeIso8601
            )
            assertEquals(Instant.Companion.parse("2025-05-17T15:22:32Z"), station.clickTimestamp)
            assertEquals(
                Instant.Companion.parse("2025-05-17T15:22:32Z"),
                station.clickTimestampIso8601
            )
            assertEquals(690, station.clickCount)
            assertEquals(-43, station.clickTrend)
            assertEquals(0, station.sslError)
            assertEquals(40.75166, station.geoLat)
            assertEquals(-73.97538, station.geoLon)
            assertEquals(null, station.geoDistance)
            assertEquals(true, station.hasExtendedInfo)
        }
    }

    @Test
    fun `Get stations with simple filter`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStations(
                filter = StationFilter.Simple(
                    matchingFilter = MatchingFilter("query", isExact = true),
                    field = StationFilter.Simple.Field.Tag
                )
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/bytagexact/query")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("order", "name")
                append("reverse", "false")
                append("hidebroken", "false")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Test
    fun `Get stations with advanced search`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val filter = StationFilter.Advanced(
                name = MatchingFilter("name", isExact = true),
                country = MatchingFilter("country", isExact = false),
                countryCode = "AA",
                state = MatchingFilter("state", isExact = true),
                language = MatchingFilter("language", isExact = true),
                tag = MatchingFilter("tag", isExact = true),
                tagList = listOf("123", "234"),
                codec = "mp3",
                bitrateMin = 0,
                bitrateMax = 100500,
                hasGeoInfo = true,
                hasExtendedInfo = false,
                isHttps = true,
                geoLat = 180.0,
                geoLon = 90.0,
                geoDistance = 50.0,
            )

            val response = api.getStations(
                filter = filter,
                orderBy = OrderField.Votes,
                pagingRequest = PagingRequest(1, 5),
                hideBroken = true,
                reverse = true,
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/search")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("reverse", "true")
                append("hidebroken", "true")
                append("order", "votes")
                append("offset", "1")
                append("limit", "5")
                append("nameExact", filter.name!!.query)
                append("country", filter.country!!.query)
                append("countrycode", filter.countryCode!!)
                append("stateExact", filter.state!!.query)
                append("languageExact", filter.language!!.query)
                append("tagExact", filter.tag!!.query)
                append("tagList", filter.tagList.joinToString(separator = ","))
                append("codec", filter.codec!!)
                append("bitrateMin", filter.bitrateMin!!.toString())
                append("bitrateMax", filter.bitrateMax!!.toString())
                append("has_geo_info", filter.hasGeoInfo!!.toString())
                append("has_extended_info", filter.hasExtendedInfo!!.toString())
                append("is_https", filter.isHttps!!.toString())
                append("geo_lat", filter.geoLat!!.toString())
                append("geo_long", filter.geoLon!!.toString())
                append("geo_distance", filter.geoDistance!!.toString())
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Test
    fun `Get station check steps`() {
        runTest {
            mockEngineResponder = MockEngineResponderChecksteps

            val response = api.getStationCheckSteps(
                uuids = listOf("123", "234")
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/checksteps")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("uuids", "123,234")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationCheckStepResponse>>>(response)
            val checkSteps = response.data
            assertEquals(12, checkSteps.size)
            val checkStep = checkSteps.first()
            assertEquals("ee4b419e-a09d-4049-9bbf-a8e1b4245294", checkStep.stepUuid)
            assertEquals(null, checkStep.parentStepUuid)
            assertEquals("2d37423f-8d98-4660-bbf2-deb325a9a40d", checkStep.checkUuid)
            assertEquals("43cf1869-6849-4fff-954d-fdbad6e412e2", checkStep.stationUuid)
            assertEquals("https://dancewave.online/dance.mp3", checkStep.url)
            assertEquals(StationCheckStepResponse.UrlType.Redirect, checkStep.urlType)
            assertEquals(null, checkStep.error)
            assertEquals(Instant.Companion.parse("2025-04-25T04:10:41Z"), checkStep.creationIso8601)
        }
    }

    @Test
    fun `Get station clicks`() {
        runTest {
            mockEngineResponder = MockResponderStationClicks

            val response = api.getStationClicks(
                stationUuid = "1234",
                lastClickUuid = "4355",
                seconds = 100,
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/clicks/1234")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("lastclickuuid", "4355")
                append("seconds", "100")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationClickResponse>>>(response)
            val clicks = response.data
            assertEquals(11, clicks.size)
            val click = clicks.first()
            assertEquals("c5c326ed-f26d-4295-a55a-4652ed7d276e", click.stationUuid)
            assertEquals("b59ff491-39b3-11f0-989f-9600042a9e08", click.clickUuid)
            assertEquals(Instant.Companion.parse("2025-05-25T22:00:45Z"), click.clickTimestamp)
            assertEquals(
                Instant.Companion.parse("2025-05-25T22:00:45Z"),
                click.clickTimestampIso8601
            )
        }
    }

    @Test
    fun `Get station checks`() {
        runTest {
            mockEngineResponder = MockEngineResponderChecks

            val response = api.getStationChecks(
                stationUuid = "1234",
                lastCheckUuid = "4355",
                seconds = 100,
                limit = 10,
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/checks/1234")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("lastcheckuuid", "4355")
                append("seconds", "100")
                append("limit", "10")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationCheckResponse>>>(response)
            val checks = response.data
            assertEquals(4, checks.size)
            val check = checks.first()
            assertEquals("43cf1869-6849-4fff-954d-fdbad6e412e2", check.stationUuid)
            assertEquals("e6314e34-a956-4209-83eb-176d06835de3", check.checkUuid)
            assertEquals("fi1", check.source)
            assertEquals("MP3", check.codec)
            assertEquals(128, check.bitrate)
            assertEquals(0, check.hls)
            assertEquals(1, check.ok)
            assertEquals(Instant.Companion.parse("2025-05-24T15:03:47Z"), check.timestamp)
            assertEquals(Instant.Companion.parse("2025-05-24T15:03:47Z"), check.timestampIso8601)
            assertEquals("http://stream.dancewave.online:8080/dance.mp3", check.urlCache)
            assertEquals(1, check.metainfoOverridesDatabase)
            assertEquals(0, check.public)
            assertEquals("Dance Wave!", check.name)
            assertEquals("All about Dance from 2000 till today!", check.description)
            assertEquals("Club Dance Electronic House Trance", check.tags)
            assertEquals(null, check.countryCode)
            assertEquals("https://dancewave.online", check.homepage)
            assertEquals("https://dancewave.online/dw_logo.png", check.favicon)
            assertEquals("https://dancewave.online/dance.mp3", check.loadBalancer)
            assertEquals(null, check.countrySubdivisionCode)
            assertEquals("cloudflare", check.serverSoftware)
            assertEquals(44100, check.sampling)
            assertEquals(413, check.timingMs)
            assertEquals("", check.languageCodes)
            assertEquals(0, check.sslError)
            assertEquals(null, check.geoLat)
            assertEquals(null, check.geoLon)
        }
    }

    @Test
    fun `Get stations by uuids`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStationsByUuids(
                stationUuids = listOf("123", "234")
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/byuuid")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("uuids", "123,234")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }


    @Test
    fun `Get stations by url`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStationsByUrl(
                stationUrl = "https://dancewave.online/dance.mp3"
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/byurl")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("url", "https://dancewave.online/dance.mp3")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Test
    fun `Get older versions of stations`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStationChanges(
                limit = 5,
                lastChangeUuid = "lastchange",
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/changed")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("limit", "5")
                append("lastchangeuuid", "lastchange")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Test
    fun `Get older versions of station`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStationChanges(
                stationUuid = "station",
                limit = 5,
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/changed/station")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("limit", "5")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Test
    fun `Get stations by recently changed or added`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStationsBy(
                request = StationsByField.LastChange,
                pagingRequest = PagingRequest(1, 5),
                hideBroken = true,
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/lastchange")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("offset", "1")
                append("limit", "5")
                append("hidebroken", "true")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Test
    fun `Get stations by last click`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStationsBy(
                request = StationsByField.LastClick,
                pagingRequest = PagingRequest(1, 5),
                hideBroken = true,
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/lastclick")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("offset", "1")
                append("limit", "5")
                append("hidebroken", "true")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Test
    fun `Get stations by top votes`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStationsBy(
                request = StationsByField.TopVotes,
                pagingRequest = PagingRequest(1, 5),
                hideBroken = true,
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/topvote")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("offset", "1")
                append("limit", "5")
                append("hidebroken", "true")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Test
    fun `Get stations by top clicks`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getStationsBy(
                request = StationsByField.TopClicks,
                pagingRequest = PagingRequest(1, 5),
                hideBroken = true,
            )

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/topclick")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("offset", "1")
                append("limit", "5")
                append("hidebroken", "true")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Test
    fun `Get broken stations`() {
        runTest {
            mockEngineResponder = MockEngineResponderStations

            val response = api.getBrokenStations(PagingRequest(1, 5))

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/stations/broken")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("offset", "1")
                append("limit", "5")
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<List<StationResponse>>>(response)
        }
    }

    @Suppress("DEPRECATION")
    @Test
    fun `Add stations`() {
        runTest {
            mockEngineResponder = MockEngineResponderAddStation

            val request = AddStationRequest(
                name = "Test station",
                url = "http://test.com",
                homepage = "http://testhomepage.com",
                favicon = "favicon",
                countryCode = "AA",
                state = "state",
                iso_3166_2 = "iso",
                language = "language",
                tags = listOf("tag1", "tag2"),
                geoLat = 0.1,
                geoLon = 0.2,
            )

            val result = api.addOrUpdateStation(request)

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/add")
            mockEngine.assertRequestParameters(Parameters.Companion.build {
                append("name", request.name)
                append("url", request.url)
                append("homepage", request.homepage!!)
                append("favicon", request.favicon!!)
                append("countrycode", request.countryCode!!)
                append("state", request.state!!)
                append("iso_3166_2", request.iso_3166_2!!)
                append("language", request.language!!)
                append("tags", request.tags.joinToString(separator = ","))
                append("geo_lat", request.geoLat!!.toString())
                append("geo_long", request.geoLon!!.toString())
            })
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<StationAddResultResponse>>(result)
            assertTrue(result.data.ok)
            assertEquals("added station successfully", result.data.message)
            assertEquals("919e829b-8a24-43b8-ac69-ba10f2b5dd0b", result.data.uuid)
        }
    }

    @Test
    fun `Vote for station`() {
        runTest {
            mockEngineResponder = MockEngineResponderVote

            val uuid = "1234"
            val response = api.stationVote(uuid)

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/vote/$uuid")
            mockEngine.assertRequestParameters(Parameters.Companion.Empty)
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<StationVoteResponse>>(response)
            assertTrue(response.data.ok)
            assertEquals("voted for station successfully", response.data.message)
        }
    }

    @Test
    fun `Get station url and click`() {
        runTest {
            mockEngineResponder = MockEngineResponderUrl

            val uuid = "1234"
            val response = api.stationUrl(uuid)

            mockEngine.assertIsPost()
            mockEngine.assertRequestHostAndPath("/json/url/$uuid")
            mockEngine.assertRequestParameters(Parameters.Companion.Empty)
            mockEngine.assertRequestUserAgent()
            mockEngine.assertRequestContentTypeJson()

            assertIs<RadioBrowserResponse.Success<StationUrlResponse>>(response)
            assertTrue(response.data.ok)
            assertEquals("retrieved station url", response.data.message)
            assertEquals(
                "4d381732-d8f9-4649-9e54-7ae35fc8c568",
                response.data.stationUuid
            )
            assertEquals("\r\nМихаил Круг Радио ", response.data.name)
            assertEquals("https://stream04.pcradio.ru/Michail_Krug-hi", response.data.url)
        }
    }
}