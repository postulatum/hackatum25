package postulatum.plantum.plantum

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.munic
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.BorderStroke

@Composable
fun StarterHeader(
    userName: String?,
    logo: Painter? = null,
    onLogout: () -> Unit,
    onImprintClick: () -> Unit = {},
    onLanguageChange: (String) -> Unit = {},
    // Neue optionale Callbacks für das Profil-Menü
    onProfileSettingsClick: () -> Unit = {},
    onStudyProgramsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    // Externe Links (TUMonline, Moodle, etc.)
    onExternalLinkClick: (String) -> Unit = ::openUrl
) {
    // Markenfarben (ohne Verlauf): Brand-Blau und Dark-Blau
    val tumBlue = Color(0xFF0065bd)   // Brand
    val tumBlueDark = Color(0xFF005293) // Dark

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(tumBlue) // Fester Hintergrund, kein Verlauf
    ) {

        // 🔥 Große, sichtbare Skyline als echter Hintergrund-Layer
        Image(
            painter = painterResource(Res.drawable.munic),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(180.dp)   // größer als Header → wirkt wie echter BG
                .align(Alignment.Center)
                .alpha(0.68f),    // gut sichtbar
            contentScale = ContentScale.FillWidth
        )

        // === Content Layer ===
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // LEFT SIDE
            Row(verticalAlignment = Alignment.CenterVertically) {

                if (logo != null) {
                    Image(
                        painter = logo,
                        contentDescription = "Logo",
                        modifier = Modifier.size(120.dp) // etwas kleiner & cleaner
                    )
                }

                Column {
                    Text(
                        text = "planTUM",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Study Planner for TUM Students",
                        color = Color.White.copy(alpha = 0.92f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "From Students",
                        color = Color.White.copy(alpha = 0.85f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            // Rechts: Username links NEBEN dem Avatar, dann Sprach-, Links- und Menü-Buttons + Abmelden
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Username links neben dem Avatar
                if (!userName.isNullOrBlank()) {
                    Text(
                        text = userName!!,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.width(10.dp))
                }
                // Profil-Button mit Dropdown-Menü (Avatar)
                ProfileMenuButton(
                    tumBlueDark = tumBlueDark,
                    onProfileSettingsClick = onProfileSettingsClick,
                    onStudyProgramsClick = onStudyProgramsClick,
                    onProfileClick = onProfileClick
                )
                Spacer(Modifier.width(12.dp))

                // Sprach-Umschalter (mit Flaggen) zwischen Profil-Icon und Menü-Button
                LanguageToggleButton(
                    tumBlueDark = tumBlueDark,
                    onLanguageChange = onLanguageChange
                )

                Spacer(Modifier.width(12.dp))
                // Schneller Link-Button zu TUMonline, Moodle, TUM Live, TUM Startseite
                LinksQuickAccessButton(
                    tumBlueDark = tumBlueDark,
                    onExternalLinkClick = onExternalLinkClick
                )
                Spacer(Modifier.width(12.dp))
                // Menü-Button mit Textzeichen "≡"
                MenuButtonWithoutIcons(
                    tumBlueDark = tumBlueDark,
                    onLogout = onLogout,
                    onImprintClick = onImprintClick
                )
                Spacer(Modifier.width(12.dp))
                OutlinedButton(
                    onClick = onLogout,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.9f))
                ) {
                    Text("Abmelden", fontWeight = FontWeight.SemiBold)
                }
            }
        }

    }
}

// Neue Komponente für den Menü-Button mit Dropdown, nur Textzeichen verwendet
@Composable
fun MenuButtonWithoutIcons(
    tumBlueDark: Color,
    onLogout: () -> Unit,
    onImprintClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        // Menü-Icon als Textzeichen (≡)
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable { expanded = true } // <--- Klick öffnet Menü
                .background(Color.White.copy(alpha = 0.95f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "≡", // <--- Menü-Symbol als Textzeichen
                color = tumBlueDark,
                // Größere Schrift, um wie ein Icon auszusehen
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp) // Leicht nach unten korrigiert
            )
        }

        // Dropdown-Menü
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // Impressum
            DropdownMenuItem(
                text = { Text("Impressum") },
                onClick = {
                    expanded = false
                    onImprintClick()
                }
            )

            // Statischer Kalendereintrag (noch ohne Funktion)
            DropdownMenuItem(
                text = { Text("Kalender (bald)") },
                enabled = false,
                onClick = { /* no-op */ }
            )
        }
    }
}

// Kleiner runder Button, der zwischen DE/EN umschaltet und Flaggen anzeigt
@Composable
fun LanguageToggleButton(
    tumBlueDark: Color,
    onLanguageChange: (String) -> Unit = {}
) {
    var lang by remember { mutableStateOf("de") }
    var expanded by remember { mutableStateOf(false) }

    val flag = when (lang) {
        "de" -> "🇩🇪"
        "en" -> "🇬🇧"
        else -> "🏳️"
    }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        // Round button showing current language flag
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White.copy(alpha = 0.95f), shape = CircleShape)
                .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = flag,
                color = tumBlueDark,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        // Dropdown with language choices
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("🇩🇪  Deutsch") },
                onClick = {
                    lang = "de"
                    expanded = false
                    onLanguageChange(lang)
                }
            )
            DropdownMenuItem(
                text = { Text("🇬🇧  English") },
                onClick = {
                    lang = "en"
                    expanded = false
                    onLanguageChange(lang)
                }
            )
        }
    }
}

// Runder Button mit Globus, der ein Dropdown mit externen Links öffnet
@Composable
fun LinksQuickAccessButton(
    tumBlueDark: Color,
    onExternalLinkClick: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White.copy(alpha = 0.95f), shape = CircleShape)
                .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🌐",
                color = tumBlueDark,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("TUMonline") },
                onClick = {
                    expanded = false
                    onExternalLinkClick("https://campus.tum.de/tumonline/ee/ui/ca2/app/desktop/#/login?")
                }
            )
            DropdownMenuItem(
                text = { Text("Moodle") },
                onClick = {
                    expanded = false
                    onExternalLinkClick("https://www.moodle.tum.de/")
                }
            )
            DropdownMenuItem(
                text = { Text("TUM Live") },
                onClick = {
                    expanded = false
                    onExternalLinkClick("https://live.rbg.tum.de/")
                }
            )
            DropdownMenuItem(
                text = { Text("TUM Startseite") },
                onClick = {
                    expanded = false
                    onExternalLinkClick("https://www.tum.de/")
                }
            )
        }
    }
}

// Runder Profil-Button, der ein Dropdown-Menü mit mehreren Aktionen öffnet
@Composable
fun ProfileMenuButton(
    tumBlueDark: Color,
    onProfileSettingsClick: () -> Unit = {},
    onStudyProgramsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        // Round profile button
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.White.copy(alpha = 0.95f), shape = CircleShape)
                .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "👤",
                color = tumBlueDark,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Einstellungen") },
                onClick = {
                    expanded = false
                    onProfileSettingsClick()
                }
            )
            DropdownMenuItem(
                text = { Text("Studiengänge") },
                onClick = {
                    expanded = false
                    onStudyProgramsClick()
                }
            )
            DropdownMenuItem(
                text = { Text("Profil anzeigen") },
                onClick = {
                    expanded = false
                    onProfileClick()
                }
            )
        }
    }
}