package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockResponderStationClicks : MockEngineResponder {
    override val body: String = """
        [
            {
                "stationuuid": "c5c326ed-f26d-4295-a55a-4652ed7d276e",
                "clickuuid": "b59ff491-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:00:45Z",
                "clicktimestamp": "2025-05-25 22:00:45"
            },
            {
                "stationuuid": "69bc2427-259f-44d3-8593-6bae1e3fbc16",
                "clickuuid": "b6185d17-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:00:46Z",
                "clicktimestamp": "2025-05-25 22:00:46"
            },
            {
                "stationuuid": "96063f25-0601-11e8-ae97-52543be04c81",
                "clickuuid": "b6661a15-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:00:47Z",
                "clicktimestamp": "2025-05-25 22:00:47"
            },
            {
                "stationuuid": "177cc601-a536-41cc-bd67-7d10b1a5dee1",
                "clickuuid": "b6932e94-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:00:47Z",
                "clicktimestamp": "2025-05-25 22:00:47"
            },
            {
                "stationuuid": "98b22cd2-0ec8-4bb1-a5f6-f168de7548b5",
                "clickuuid": "b6df02e6-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:00:47Z",
                "clicktimestamp": "2025-05-25 22:00:47"
            },
            {
                "stationuuid": "9618f5ba-0601-11e8-ae97-52543be04c81",
                "clickuuid": "bb083875-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:00:54Z",
                "clicktimestamp": "2025-05-25 22:00:54"
            },
            {
                "stationuuid": "c73a2011-563e-45b6-a15b-d4491c2c739e",
                "clickuuid": "bc4ef8bf-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:00:56Z",
                "clicktimestamp": "2025-05-25 22:00:56"
            },
            {
                "stationuuid": "ad563392-497b-4fe0-a3e6-4a6b30d57bbe",
                "clickuuid": "bd63c45c-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:00:58Z",
                "clicktimestamp": "2025-05-25 22:00:58"
            },
            {
                "stationuuid": "81b88161-c3f7-4958-8165-7d408d7689db",
                "clickuuid": "bee0cf85-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:01:01Z",
                "clicktimestamp": "2025-05-25 22:01:01"
            },
            {
                "stationuuid": "fecc54d0-12a7-4db1-8569-ed644ae27f5d",
                "clickuuid": "bf2b875b-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:01:01Z",
                "clicktimestamp": "2025-05-25 22:01:01"
            },
            {
                "stationuuid": "59e32ce5-1f43-4062-b244-efea57ba40cf",
                "clickuuid": "bf9f94a7-39b3-11f0-989f-9600042a9e08",
                "clicktimestamp_iso8601": "2025-05-25T22:01:02Z",
                "clicktimestamp": "2025-05-25 22:01:02"
            }
        ]
    """.trimIndent()

    override val headers: Headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}