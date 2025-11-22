package postulatum.plantum.plantum.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import postulatum.plantum.plantum.BASE_ENPOINT
import postulatum.plantum.plantum.SERVER_IP
import postulatum.plantum.plantum.SERVER_PORT
import postulatum.plantum.plantum.model.LoginData
import postulatum.plantum.plantum.model.RegisterData
import postulatum.plantum.plantum.model.User


class BackendService {
    companion object {

        const val BASE_URL = "http://$SERVER_IP:$SERVER_PORT$BASE_ENPOINT"

        val client = HttpClient(CIO){
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }

        suspend fun login(request: LoginData): User {
            return client.post("$BASE_URL/login") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(request)
            }.body()
        }

        suspend fun register(request: RegisterData): User {
            return client.post("$BASE_URL/register") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(request)
            }.body()
        }

    }
}