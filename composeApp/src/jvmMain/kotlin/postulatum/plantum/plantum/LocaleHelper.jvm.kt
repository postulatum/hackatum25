package postulatum.plantum.plantum

import java.util.*

/**
 * JVM-specific implementation to set the system locale.
 */
actual fun setSystemLocale(language: String) {
    when (language) {
        "de" -> Locale.setDefault(Locale.GERMAN)
        "en" -> Locale.setDefault(Locale.ENGLISH)
        else -> Locale.setDefault(Locale.ENGLISH)
    }
}

