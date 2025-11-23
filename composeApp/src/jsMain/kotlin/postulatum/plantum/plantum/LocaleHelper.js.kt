package postulatum.plantum.plantum

import kotlinx.browser.window

/**
 * JS-specific implementation to set the system locale.
 * Changes the browser's language attribute to trigger resource reload.
 */
actual fun setSystemLocale(language: String) {
    // Set the HTML lang attribute - this can affect how Compose Resources loads strings
    try {
        window.document.documentElement?.setAttribute("lang", language)
    } catch (e: Exception) {
        println("Failed to set document language: $e")
    }
}

