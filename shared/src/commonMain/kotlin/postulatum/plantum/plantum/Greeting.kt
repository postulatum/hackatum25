package postulatum.plantum.plantum

/**
 * Simple greeting used by both the Compose app and the Ktor server.
 * Keeps logic in shared commonMain to be available on all targets.
 */
class Greeting {
    fun greet(): String = "Hello from ${getPlatform().name}"
}
