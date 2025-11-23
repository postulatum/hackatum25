package postulatum.plantum.plantum

import androidx.compose.runtime.*

/**
 * Platform-specific function to set the system locale.
 */
expect fun setSystemLocale(language: String)

/**
 * CompositionLocal to provide the current language throughout the app.
 */
val LocalAppLanguage = compositionLocalOf { "de" }

/**
 * Wrapper composable that provides language context to all children.
 * Forces recomposition when language changes by using key() and sets system locale.
 */
@Composable
fun LanguageProvider(
    language: String,
    content: @Composable () -> Unit
) {
    // Set system locale when language changes - this is crucial for stringResource to work
    LaunchedEffect(language) {
        setSystemLocale(language)
    }

    // Also set it immediately on first composition
    SideEffect {
        setSystemLocale(language)
    }

    CompositionLocalProvider(
        LocalAppLanguage provides language
    ) {
        // Force recomposition when language changes
        key(language) {
            content()
        }
    }
}


