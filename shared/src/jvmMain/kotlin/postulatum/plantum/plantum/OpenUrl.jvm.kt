package postulatum.plantum.plantum

import java.awt.Desktop
import java.net.URI

actual fun openUrl(url: String) {
    try {
        val uri = URI(url)
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(uri)
                return
            }
        }
        // Fallback: versuche mit Runtime
        val os = System.getProperty("os.name").lowercase()
        when {
            os.contains("mac") -> Runtime.getRuntime().exec(arrayOf("open", url))
            os.contains("nix") || os.contains("nux") -> Runtime.getRuntime().exec(arrayOf("xdg-open", url))
            os.contains("win") -> Runtime.getRuntime().exec(arrayOf("rundll32", "url.dll,FileProtocolHandler", url))
            else -> {}
        }
    } catch (_: Throwable) {
        // Ignoriere still, kein harter Crash wenn Browser nicht geöffnet werden kann
    }
}
