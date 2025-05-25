package io.github.bitlinker.kradiobrowser.api.responses

import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

object MockEngineResponderChecksteps : MockEngineResponder {
    override val body: String = """
        [
            {
                "stepuuid": "ee4b419e-a09d-4049-9bbf-a8e1b4245294",
                "parent_stepuuid": null,
                "checkuuid": "2d37423f-8d98-4660-bbf2-deb325a9a40d",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "https://dancewave.online/dance.mp3",
                "urltype": "REDIRECT",
                "error": null,
                "creation_iso8601": "2025-04-25T04:10:41Z"
            },
            {
                "stepuuid": "59b4f70b-594a-4c29-83b4-c8f6257edc65",
                "parent_stepuuid": "ee4b419e-a09d-4049-9bbf-a8e1b4245294",
                "checkuuid": "2d37423f-8d98-4660-bbf2-deb325a9a40d",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "http://onair.dancewave.online:8080/dance.mp3",
                "urltype": "STREAM",
                "error": null,
                "creation_iso8601": "2025-04-25T04:10:41Z"
            },
            {
                "stepuuid": "84966625-a7fd-4461-a980-0cad4ae07672",
                "parent_stepuuid": null,
                "checkuuid": "a8c2969f-e65f-47f4-b1d7-d5ff10a3b39f",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "https://dancewave.online/dance.mp3",
                "urltype": "REDIRECT",
                "error": null,
                "creation_iso8601": "2025-04-26T04:22:22Z"
            },
            {
                "stepuuid": "33657607-7ccb-4b5b-a228-e63eb9d9679e",
                "parent_stepuuid": "84966625-a7fd-4461-a980-0cad4ae07672",
                "checkuuid": "a8c2969f-e65f-47f4-b1d7-d5ff10a3b39f",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "http://onair.dancewave.online:8080/dance.mp3",
                "urltype": "STREAM",
                "error": null,
                "creation_iso8601": "2025-04-26T04:22:22Z"
            },
            {
                "stepuuid": "7402a552-2077-4d5d-8a52-431503edadce",
                "parent_stepuuid": null,
                "checkuuid": "797d7553-5b65-4453-943d-e5ae7c655a62",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "https://dancewave.online/dance.mp3",
                "urltype": "REDIRECT",
                "error": null,
                "creation_iso8601": "2025-04-27T04:23:20Z"
            },
            {
                "stepuuid": "634d0e60-e529-4709-aed6-fa535ef05abc",
                "parent_stepuuid": "7402a552-2077-4d5d-8a52-431503edadce",
                "checkuuid": "797d7553-5b65-4453-943d-e5ae7c655a62",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "http://onair.dancewave.online:8080/dance.mp3",
                "urltype": "STREAM",
                "error": null,
                "creation_iso8601": "2025-04-27T04:23:20Z"
            },
            {
                "stepuuid": "8fbe25da-ddf9-482b-ae6e-cbf16ed4107a",
                "parent_stepuuid": null,
                "checkuuid": "ab0468dc-4565-4369-b51d-c4361631ceb8",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "https://dancewave.online/dance.mp3",
                "urltype": "REDIRECT",
                "error": null,
                "creation_iso8601": "2025-05-23T15:01:47Z"
            },
            {
                "stepuuid": "e2bdd4c5-600b-46c6-859a-9df2a2654785",
                "parent_stepuuid": "8fbe25da-ddf9-482b-ae6e-cbf16ed4107a",
                "checkuuid": "ab0468dc-4565-4369-b51d-c4361631ceb8",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "http://stream.dancewave.online:8080/dance.mp3",
                "urltype": "STREAM",
                "error": null,
                "creation_iso8601": "2025-05-23T15:01:47Z"
            },
            {
                "stepuuid": "e08da017-5764-41ca-b6cd-2a525405cbd6",
                "parent_stepuuid": null,
                "checkuuid": "e6314e34-a956-4209-83eb-176d06835de3",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "https://dancewave.online/dance.mp3",
                "urltype": "REDIRECT",
                "error": null,
                "creation_iso8601": "2025-05-24T15:03:48Z"
            },
            {
                "stepuuid": "d4ddb789-7bdc-4b47-a84c-b9018e179daa",
                "parent_stepuuid": "e08da017-5764-41ca-b6cd-2a525405cbd6",
                "checkuuid": "e6314e34-a956-4209-83eb-176d06835de3",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "http://stream.dancewave.online:8080/dance.mp3",
                "urltype": "STREAM",
                "error": null,
                "creation_iso8601": "2025-05-24T15:03:48Z"
            },
            {
                "stepuuid": "dbc306d8-27a1-4c12-9612-9ac145ce46a4",
                "parent_stepuuid": null,
                "checkuuid": "67f8fbc8-53b4-48c7-a3c2-e2b769081031",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "https://dancewave.online/dance.mp3",
                "urltype": "REDIRECT",
                "error": null,
                "creation_iso8601": "2025-05-25T15:05:15Z"
            },
            {
                "stepuuid": "47e278f3-5f4c-44ed-ad4a-70da3fddb8bc",
                "parent_stepuuid": "dbc306d8-27a1-4c12-9612-9ac145ce46a4",
                "checkuuid": "67f8fbc8-53b4-48c7-a3c2-e2b769081031",
                "stationuuid": "43cf1869-6849-4fff-954d-fdbad6e412e2",
                "url": "http://stream.dancewave.online:8080/dance.mp3",
                "urltype": "STREAM",
                "error": null,
                "creation_iso8601": "2025-05-25T15:05:15Z"
            }
        ]
    """.trimIndent()

    override val headers = Headers.build {
        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    }
}