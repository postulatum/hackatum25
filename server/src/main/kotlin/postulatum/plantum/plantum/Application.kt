package postulatum.plantum.plantum

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import postulatum.plantum.plantum.model.LoginData
import postulatum.plantum.plantum.model.RegisterData
import postulatum.plantum.plantum.model.Slot

// HINWEIS: SERVER_PORT kommt aus deiner Constants.kt
fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    // ---------------------------------------------------------
    // 1. JSON Serialisierung
    // ---------------------------------------------------------
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    // ---------------------------------------------------------
    // 2. CORS (WICHTIG für Web & WASM)
    // ---------------------------------------------------------
    install(CORS) {
        anyHost()   // DEV: erlaubt Web & Wasm
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.Accept)
    }

    // ---------------------------------------------------------
    // 3. Routing konfigurieren
    // ---------------------------------------------------------
    configureRouting()
}

fun Application.configureRouting() {

    val studyPlannerService = ServerStudyPlannerService()

    routing {
        route("/api/v1"){

            // --------------------------------------------------------
            // 1. HEALTH CHECK ENDPOINT
            // --------------------------------------------------------
            get("/db/ping") {
                try {
                    val ok = MongoService.ping()
                    if (ok) {
                        call.respondText("OK: MongoDB erreichbar")
                    } else {
                        call.respondText("Fehler: MongoDB nicht erreichbar", status = HttpStatusCode.InternalServerError)
                    }
                } catch (e: Exception) {
                    log.error("Fehler beim MongoDB-Ping", e)
                    call.respondText("Fehler: ${e.message}", status = HttpStatusCode.InternalServerError)
                }
            }

            // --------------------------------------------------------
            // 2. AUTHENTIFIZIERUNG ENDPUNKTE
            // --------------------------------------------------------

            // POST /api/v1/register
            post("/register") {
                try {
                    val request = call.receive<RegisterData>()

                    if (studyPlannerService.getUserByEmail(request.email) != null) {
                        call.respondText("E-Mail bereits registriert.", status = HttpStatusCode.Conflict)
                        return@post
                    }

                    val newUser = studyPlannerService.registerUser(
                        request.email,
                        request.password,
                        request.userName
                    )

                    call.respond(HttpStatusCode.Created, newUser.copy(passwordHash = ""))
                } catch (e: Exception) {
                    log.error("Registrierungsfehler", e)
                    call.respondText("Registrierungsfehler: ${e.message}", status = HttpStatusCode.InternalServerError)
                }
            }

            // POST /api/v1/login
            post("/login") {
                try {
                    val request = call.receive<LoginData>()

                    val user = studyPlannerService.getUserByEmail(request.email)

                    if (user == null) {
                        call.respondText("E-Mail oder Passwort falsch.", status = HttpStatusCode.Unauthorized)
                        return@post
                    }

                    val isPasswordValid = SecurityService.verifyPassword(request.password, user.passwordHash)

                    if (isPasswordValid) {
                        call.respond(user.copy(passwordHash = ""))
                    } else {
                        call.respondText("E-Mail oder Passwort falsch.", status = HttpStatusCode.Unauthorized)
                    }
                } catch (e: Exception) {
                    log.error("Login-Fehler", e)
                    call.respondText("Login-Fehler: ${e.message}", status = HttpStatusCode.InternalServerError)
                }
            }

            // --------------------------------------------------------
            // 3. SLOT MANAGEMENT
            // --------------------------------------------------------
            route("/slots") {

                get {
                    try {
                        val slots: List<Slot> = studyPlannerService.getAllSlots()
                        call.respond(slots)
                    } catch (e: Exception) {
                        log.error("Fehler beim Abrufen aller Slots", e)
                        call.respondText(
                            "Datenbankfehler: Konnte Slots nicht abrufen.",
                            status = HttpStatusCode.InternalServerError
                        )
                    }
                }

                post {
                    try {
                        val slot = call.receive<Slot>()
                        studyPlannerService.saveSlot(slot)
                        call.respond(HttpStatusCode.Created)
                    } catch (e: ContentTransformationException) {
                        call.respondText("Ungültiger Datenkörper.", status = HttpStatusCode.BadRequest)
                    } catch (e: Exception) {
                        log.error("Fehler beim Speichern eines Slots", e)
                        call.respondText(
                            "Datenbankfehler: Konnte Slot nicht speichern.",
                            status = HttpStatusCode.InternalServerError
                        )
                    }
                }
            }
            route("/modules") {
                get {
                    try {
                        val modules = MongoService.getAllModules() // returns mock while DB is down
                        call.respond(modules)
                    } catch (e: Exception) {
                        log.error("Fehler beim Abrufen der Module", e)
                        call.respondText(
                            "Datenbankfehler: Konnte Module nicht abrufen.",
                            status = HttpStatusCode.InternalServerError
                        )
                    }
                }
            }

        }
    }
}
