package postulatum.plantum.plantum

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.serialization.kotlinx.json.json
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import postulatum.plantum.plantum.model.LoginRequest
import postulatum.plantum.plantum.model.RegisterRequest
import postulatum.plantum.plantum.model.User

actual object AuthApi {

    private val client = HttpClient(Js) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = false
            })
        }
    }

    actual suspend fun login(request: LoginRequest): User {
        return client.post("$BASE_API_URL/login") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(request)
        }.body()
    }

    actual suspend fun register(request: RegisterRequest): User {
        return client.post("$BASE_API_URL/register") {
            header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(request)
        }.body()
    }
}
