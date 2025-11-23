package postulatum.plantum.plantum

import kotlinx.browser.window

actual fun openUrl(url: String) {
    try {
        window.open(url, target = "_blank")
    } catch (t: Throwable) {
        // Fallback: gleiche Seite navigieren
        window.location.href = url
    }
}
