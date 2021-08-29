package com.example.kotlinmultiplatformspike

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorRemote : BeerService {
    private val client = HttpClient {
        defaultRequest {
            val endpointBuilder = URLBuilder("https://api.punkapi.com")
            url {
                protocol = endpointBuilder.protocol
                host = endpointBuilder.host
                // We need to do this because urlbuilder doesn't detect the /v2
                encodedPath = "/v2/$encodedPath"
            }
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getListOfBeers(page: Int): List<BeersApiResponseItem> {
        val response = client.get<List<BeersApiResponseItem>> {
            url {
                encodedPath = "beers"
            }
            parameter(
                "page", page
            )
            parameter(
                "per_page", 25
            )
        }
        return response
    }

}