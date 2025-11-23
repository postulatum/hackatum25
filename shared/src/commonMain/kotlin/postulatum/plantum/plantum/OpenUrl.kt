package postulatum.plantum.plantum

/**
 * Öffnet eine externe URL im Standard-Browser (Plattform-spezifisch umgesetzt).
 * Auf Web-Zielen öffnet sich ein neuer Tab/Window; auf JVM wird das System-Default
 * genutzt, sofern verfügbar.
 */
expect fun openUrl(url: String)
