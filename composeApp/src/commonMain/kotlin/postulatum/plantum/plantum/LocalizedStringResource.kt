package postulatum.plantum.plantum

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

/**
 * Get a localized string resource based on the current app language.
 * This function respects the LocalAppLanguage composition local and ensures
 * the string is reloaded when the language changes.
 * 
 * Usage: localizedStringResource(Res.string.my_string)
 */
@Composable
fun localizedStringResource(resource: StringResource): String {
    val currentLanguage = LocalAppLanguage.current
    
    // produceState will re-execute when currentLanguage changes
    val state = produceState(initialValue = "", currentLanguage, resource) {
        // Ensure the correct locale is set
        setSystemLocale(currentLanguage)
        // Load the string with the updated locale
        value = getString(resource)
    }
    
    return state.value
}

/**
 * Get a localized string resource with format arguments.
 */
@Composable
fun localizedStringResource(resource: StringResource, vararg formatArgs: Any): String {
    val currentLanguage = LocalAppLanguage.current
    
    val state = produceState(initialValue = "", currentLanguage, resource, formatArgs) {
        setSystemLocale(currentLanguage)
        value = getString(resource, *formatArgs)
    }
    
    return state.value
}

