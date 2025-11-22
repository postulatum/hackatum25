package postulatum.plantum.plantum

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.plantum_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    var showCreateUser by remember { mutableStateOf(false) }
    var newUserName by remember { mutableStateOf("") }
    var newFullName by remember { mutableStateOf("") }
    var newUserEmail by remember { mutableStateOf("") }
    var newUserPassword by remember { mutableStateOf("") }
    var newUserPasswordRepeat by remember { mutableStateOf("") }
    var newUserRegion by remember { mutableStateOf("Deutschland (DE)") }
    var regionMenuExpanded by remember { mutableStateOf(false) }
    var createInfo by remember { mutableStateOf<String?>(null) }
    var createError by remember { mutableStateOf<String?>(null) }

    // Focus management for Tab traversal
    val usernameRequester = remember { FocusRequester() }
    val passwordRequester = remember { FocusRequester() }
    val loginButtonRequester = remember { FocusRequester() }

    // Focus for create-user fields
    val cuUsernameReq = remember { FocusRequester() }
    val cuFullNameReq = remember { FocusRequester() }
    val cuEmailReq = remember { FocusRequester() }
    val cuPasswordReq = remember { FocusRequester() }
    val cuPasswordRepeatReq = remember { FocusRequester() }
    val cuRegionReq = remember { FocusRequester() }
    val cuCreateBtnReq = remember { FocusRequester() }

    fun tryLogin() {
        if (username == "admin" && password == "1234") {
            onLoginSuccess(username)
        } else {
            error = "Ungültige Anmeldedaten!"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x89cff0)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 420.dp)
                .background(Color(0xFF111827), shape = MaterialTheme.shapes.large)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(Res.drawable.plantum_logo),
                    contentDescription = "planTum Logo",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        text = "planTum",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "TUM Masterplaner",
                        color = Color(0xFF9CA3AF),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Bitte melde dich mit deiner Kennung an.",
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // Username
            TextField(
                value = username,
                onValueChange = { username = it; error = null },
                label = { Text("Nutzername") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(usernameRequester)
                    .focusProperties {
                        next = passwordRequester
                    },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { passwordRequester.requestFocus() }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF0F172A),
                    unfocusedContainerColor = Color(0xFF0F172A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFF93C5FD),
                    unfocusedLabelColor = Color(0xFF9CA3AF)
                )
            )

            Spacer(Modifier.height(12.dp))

            // Password
            TextField(
                value = password,
                onValueChange = { password = it; error = null },
                label = { Text("Passwort") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordRequester)
                    .focusProperties {
                        previous = usernameRequester
                        next = loginButtonRequester
                    },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { tryLogin() }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF0F172A),
                    unfocusedContainerColor = Color(0xFF0F172A),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFF93C5FD),
                    unfocusedLabelColor = Color(0xFF9CA3AF)
                )
            )

            if (error != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = error!!,
                    color = Color(0xFFFCA5A5),
                    fontSize = 12.sp
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { tryLogin() },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(loginButtonRequester)
                    .focusProperties {
                        previous = passwordRequester
                        // Optional: loop focus back to username if user tabs after button
                        next = usernameRequester
                    },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0EA5E9))
            ) {
                Text(
                    text = "Anmelden",
                    color = Color(0xFF0B1120),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Neuen Benutzer anlegen",
                color = Color(0xFF93C5FD),
                fontSize = 14.sp,
                modifier = Modifier.clickable { showCreateUser = true; createInfo = null },
            )

            // Inline panel removed; modal overlay is added outside the card below.
        }

        // Modal overlay for Create User (placed as a sibling to the card so it can cover the whole screen)
        if (showCreateUser) {
            // Full-screen scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.55f))
            ) {
                // Centered scrollable card/dialog
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        // High-contrast dark surface for the modal
                        color = Color(0xFF0B1220),
                        shape = MaterialTheme.shapes.large,
                        tonalElevation = 8.dp,
                        shadowElevation = 12.dp,
                        modifier = Modifier
                            .widthIn(max = 520.dp)
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                // Ensure the scroll container takes the available height of the modal
                                .fillMaxHeight()
                                .verticalScroll(rememberScrollState()),
                        ) {
                            // Header row with title and close
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Neuen Benutzer anlegen",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "✖",
                                        color = Color(0xFF93C5FD),
                                        modifier = Modifier
                                            .clickable { showCreateUser = false },
                                        fontSize = 18.sp
                                    )
                                }
                                Spacer(Modifier.height(12.dp))

                                // Note: improved contrast via OutlinedTextFieldDefaults.colors
                                fun isValidEmail(email: String): Boolean =
                                    email.contains("@") && email.contains(".") && email.length >= 6

                                fun validateCreate(): String? {
                                    if (newUserName.isBlank()) return "Benutzername ist erforderlich."
                        if (newFullName.isBlank()) return "Vollständiger Name ist erforderlich."
                        if (newUserEmail.isBlank()) return "E-Mail ist erforderlich."
                        if (!isValidEmail(newUserEmail)) return "Bitte eine gültige E-Mail-Adresse angeben."
                        if (newUserPassword.length < 6) return "Passwort muss mindestens 6 Zeichen haben."
                        if (newUserPassword != newUserPasswordRepeat) return "Passwörter stimmen nicht überein."
                        return null
                    }

                    val regions = listOf(
                        "Deutschland (DE)",
                        "Österreich (AT)",
                        "Schweiz (CH)",
                        "USA (EN-US)",
                        "UK (EN-GB)",
                        "Frankreich (FR)",
                        "Spanien (ES)",
                        "Italien (IT)"
                    )

                    val tfColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF334155),
                        disabledBorderColor = Color(0xFF1F2937),
                        focusedLabelColor = Color(0xFF93C5FD),
                        unfocusedLabelColor = Color(0xFFCBD5E1),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color(0xFF93C5FD)
                    )

                    OutlinedTextField(
                        value = newUserName,
                        onValueChange = { newUserName = it; createError = null; createInfo = null },
                        label = { Text("Benutzername") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(cuUsernameReq)
                            .focusProperties { next = cuFullNameReq },
                        colors = tfColors,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { cuFullNameReq.requestFocus() })
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newFullName,
                        onValueChange = { newFullName = it; createError = null; createInfo = null },
                        label = { Text("Vollständiger Name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(cuFullNameReq)
                            .focusProperties { previous = cuUsernameReq; next = cuEmailReq },
                        colors = tfColors,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { cuEmailReq.requestFocus() })
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newUserEmail,
                        onValueChange = { newUserEmail = it; createError = null; createInfo = null },
                        label = { Text("E-Mail") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(cuEmailReq)
                            .focusProperties { previous = cuFullNameReq; next = cuPasswordReq },
                        colors = tfColors,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { cuPasswordReq.requestFocus() })
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newUserPassword,
                        onValueChange = { newUserPassword = it; createError = null; createInfo = null },
                        label = { Text("Passwort") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(cuPasswordReq)
                            .focusProperties { previous = cuEmailReq; next = cuPasswordRepeatReq },
                        colors = tfColors,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { cuPasswordRepeatReq.requestFocus() })
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newUserPasswordRepeat,
                        onValueChange = { newUserPasswordRepeat = it; createError = null; createInfo = null },
                        label = { Text("Passwort wiederholen") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(cuPasswordRepeatReq)
                            .focusProperties { previous = cuPasswordReq; next = cuRegionReq },
                        colors = tfColors,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { cuRegionReq.requestFocus() })
                    )

                    Spacer(Modifier.height(8.dp))
                    // Region selector
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(cuRegionReq)
                            .focusProperties { previous = cuPasswordRepeatReq; next = cuCreateBtnReq }
                    ) {
                        OutlinedTextField(
                            value = newUserRegion,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Region") },
                            trailingIcon = {
                                // Use simple unicode chevrons to avoid extra icon dependencies
                                Text(if (regionMenuExpanded) "▲" else "▼", color = Color(0xFF93C5FD))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { regionMenuExpanded = true },
                            colors = tfColors
                        )
                        DropdownMenu(
                            expanded = regionMenuExpanded,
                            onDismissRequest = { regionMenuExpanded = false }
                        ) {
                            regions.forEach { region ->
                                DropdownMenuItem(
                                    text = { Text(region) },
                                    onClick = {
                                        newUserRegion = region
                                        regionMenuExpanded = false
                                    },
                                    colors = MenuDefaults.itemColors(
                                        // Assume light surface for the dropdown; use dark text for contrast
                                        textColor = Color(0xFF111827)
                                    )
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    val createDisabled = validateCreate() != null

                    Button(
                        onClick = {
                            val err = validateCreate()
                            if (err != null) {
                                createError = err
                                createInfo = null
                            } else {
                                createError = null
                                createInfo = "Demo: Benutzer $newUserName ($newFullName) mit E-Mail $newUserEmail und Region $newUserRegion würde erstellt werden. (Keine Speicherung)"
                            }
                        },
                        enabled = !createDisabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(cuCreateBtnReq)
                            .focusProperties { previous = cuRegionReq },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF22C55E),
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFF14532D),
                            disabledContentColor = Color(0xFF9CA3AF)
                        )
                    ) {
                        Text("Erstellen (Demo)")
                    }
                    if (createError != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(createError!!, color = Color(0xFFFCA5A5), fontSize = 12.sp)
                    }
                    if (createInfo != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(createInfo!!, color = Color(0xFF86EFAC), fontSize = 12.sp)
                    }
                    Spacer(Modifier.height(16.dp))
                    // Cancel button
                    OutlinedButton(
                        onClick = { showCreateUser = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF93C5FD)
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            width = 1.dp,
                            brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF334155))
                        )
                    ) {
                        Text("Abbrechen")
                    }
                }
                    }
                }
            }
        }
    }
}
