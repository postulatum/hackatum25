package postulatum.plantum.plantum

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import postulatum.plantum.plantum.model.LoginRequest
import postulatum.plantum.plantum.model.RegisterRequest
import postulatum.plantum.plantum.model.User

actual object AuthApi {
    private fun baseUrl(): String = BASE_API_URL

    actual suspend fun login(request: LoginRequest): User {
        val body = Json.encodeToString(request)
        val resp = window.fetch(
            "${'$'}{baseUrl()}/login",
            RequestInit(
                method = "POST",
                headers = Headers().apply { set("Content-Type", "application/json") },
                body = body,
            )
        ).await()

        val text = resp.text().await()
        if (!resp.ok) {
            throw RuntimeException(text.ifBlank { "Login fehlgeschlagen (${resp.status})" })
        }
        return Json.decodeFromString<User>(text)
    }

    actual suspend fun register(request: RegisterRequest): User {
        val body = Json.encodeToString(request)
        val resp = window.fetch(
            "${'$'}{baseUrl()}/register",
            RequestInit(
                method = "POST",
                headers = Headers().apply { set("Content-Type", "application/json") },
                body = body,
            )
        ).await()

        val text = resp.text().await()
        if (!resp.ok) {
            throw RuntimeException(text.ifBlank { "Registrierung fehlgeschlagen (${resp.status})" })
        }
        return Json.decodeFromString<User>(text)
    }
}
