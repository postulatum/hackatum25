package postulatum.plantum.plantum

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.plantum_logo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import postulatum.plantum.plantum.model.LoginData
import postulatum.plantum.plantum.model.RegisterData
import postulatum.plantum.plantum.model.User
import postulatum.plantum.plantum.services.BackendService

// --- Color Palette Definition (Clean Blue/Grey Theme) ---
private val AppBgColor = Color(0xFFF3F4F6)
private val CardBgColor = Color.White
private val PrimaryColor = Color(0xFF307FE2)
private val TextPrimary = Color(0xFF111827)
private val TextSecondary = Color(0xFF6B7280)
private val ErrorColor = Color(0xFFEF4444)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (User) -> Unit,
) {
    // States
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    var showCreateUser by remember { mutableStateOf(false) }

    val usernameRequester = remember { FocusRequester() }
    val passwordRequester = remember { FocusRequester() }
    val loginButtonRequester = remember { FocusRequester() }

    val isDark = isSystemInDarkTheme()
    val highContrastColor = if (!isDark) Color.White else Color.Black


    val scope = rememberCoroutineScope()

    fun tryLogin() {
        // Basic validation
        if (username.isBlank() || password.isBlank()) {
            error = "Bitte E-Mail und Passwort eingeben."
            return
        }
        error = null
        loading = true
        scope.launch {
            try {
                val user: User = BackendService.login(LoginData(email = username.trim(), password = password))
                val display = user.userName?.takeIf { it.isNotBlank() } ?: user.email
                onLoginSuccess(user)
            } catch (t: Throwable) {
                error = t.message ?: "Login fehlgeschlagen. Bitte erneut versuchen."
            } finally {
                loading = false
            }
        }
    }

    // Main Container
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBgColor)
    ) {
        val isWideScreen = maxWidth > 900.dp
        val scrollState = rememberScrollState()

        if (isWideScreen) {
            Row(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxHeight()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(48.dp)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.plantum_logo),
                            contentDescription = null,
                            modifier = Modifier.size(280.dp),
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(highContrastColor)
                        )
                        Spacer(Modifier.height(24.dp))
                        Text(
                            text = "planTUM",
                            color = Color.White,
                            fontSize = 64.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-1).sp
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Dein Studium. Dein Plan. Dein Erfolg.",
                            color = Color(0xFF94A3B8),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // RIGHT SIDE: Login Form
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .verticalScroll(scrollState),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = 500.dp)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LoginCardContent(
                            username = username,
                            onUsernameChange = { username = it; error = null },
                            password = password,
                            onPasswordChange = { password = it; error = null },
                            rememberMe = rememberMe,
                            onRememberMeChange = { rememberMe = it },
                            passwordVisible = passwordVisible,
                            onPasswordVisibleChange = { passwordVisible = !passwordVisible },
                            error = error,
                            onLoginClick = { tryLogin() },
                            onCreateUserClick = { showCreateUser = true },
                            usernameRequester = usernameRequester,
                            passwordRequester = passwordRequester,
                            loginButtonRequester = loginButtonRequester
                        )

                        Spacer(Modifier.height(32.dp))
                        FooterLinks()
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.height(24.dp))

                // Logo Top
                Image(
                    painter = painterResource(Res.drawable.plantum_logo),
                    contentDescription = "planTum Logo",
                    modifier = Modifier.size(120.dp)
                )
                Text(
                    text = "planTUM",
                    color = PrimaryColor,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(32.dp))

                // Login Card
                Surface(
                    modifier = Modifier.widthIn(max = 480.dp),
                    shadowElevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = CardBgColor
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        LoginCardContent(
                            username = username,
                            onUsernameChange = { username = it; error = null },
                            password = password,
                            onPasswordChange = { password = it; error = null },
                            rememberMe = rememberMe,
                            onRememberMeChange = { rememberMe = it },
                            passwordVisible = passwordVisible,
                            onPasswordVisibleChange = { passwordVisible = !passwordVisible },
                            error = error,
                            onLoginClick = { tryLogin() },
                            onCreateUserClick = { showCreateUser = true },
                            usernameRequester = usernameRequester,
                            passwordRequester = passwordRequester,
                            loginButtonRequester = loginButtonRequester
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))
                FooterLinks()
            }
        }

        // --- MODAL: CREATE USER ---
        if (showCreateUser) {
            CreateUserModal(onDismiss = { showCreateUser = false })
        }
    }
}

/**
 * Ausgelagerte UI-Komponente für das Login-Formular,
 * damit der Code oben sauberer bleibt.
 */
@Composable
fun LoginCardContent(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibleChange: () -> Unit,
    error: String?,
    onLoginClick: () -> Unit,
    onCreateUserClick: () -> Unit,
    usernameRequester: FocusRequester,
    passwordRequester: FocusRequester,
    loginButtonRequester: FocusRequester
) {
    Text(
        text = "Willkommen zurück",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
    )
    Text(
        text = "Bitte melde dich mit deinem Benutzernamen an.",
        fontSize = 14.sp,
        color = TextSecondary,
        modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
    )

    // Username
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        label = { Text("Benutzername oder E-Mail") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(usernameRequester)
            .focusProperties { next = passwordRequester },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { passwordRequester.requestFocus() }),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryColor,
            focusedLabelColor = PrimaryColor,
            cursorColor = PrimaryColor
        )
    )

    Spacer(Modifier.height(16.dp))

    // Password
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Passwort") },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            // Use simple text glyphs to avoid platform-specific icon dependencies
            IconButton(onClick = onPasswordVisibleChange) {
                Text(
                    text = if (passwordVisible) "🙈" else "👁",
                    fontSize = 18.sp,
                    color = TextSecondary
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(passwordRequester)
            .focusProperties { next = loginButtonRequester },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onLoginClick() }),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryColor,
            focusedLabelColor = PrimaryColor,
            cursorColor = PrimaryColor
        )
    )

    // Options Row: Remember Me & Forgot Password
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onRememberMeChange(!rememberMe) }
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = onRememberMeChange,
                colors = CheckboxDefaults.colors(checkedColor = PrimaryColor)
            )
            Text(
                text = "Merken",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        Text(
            text = "Passwort vergessen?",
            style = MaterialTheme.typography.bodySmall,
            color = PrimaryColor,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { /* TODO: Forgot PW Logic */ }
                .padding(4.dp)
        )
    }

    if (error != null) {
        Spacer(Modifier.height(16.dp))
        Surface(
            color = Color(0xFFFEF2F2),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = error,
                color = ErrorColor,
                fontSize = 13.sp,
                modifier = Modifier.padding(12.dp),
                textAlign = TextAlign.Center
            )
        }
    }

    Spacer(Modifier.height(24.dp))

    Button(
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .focusRequester(loginButtonRequester),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = "Anmelden",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }

    Spacer(Modifier.height(24.dp))

    HorizontalDivider(color = Color(0xFFE5E7EB))

    Spacer(Modifier.height(24.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Neu bei planTUM? ", color = TextSecondary, fontSize = 14.sp)
        Text(
            text = "Konto erstellen",
            color = PrimaryColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { onCreateUserClick() }
                .padding(4.dp)
        )
    }
}

@Composable
fun FooterLinks() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("Datenschutz", fontSize = 12.sp, color = Color(0xFF9CA3AF), modifier = Modifier.clickable {})
        Text("•", fontSize = 12.sp, color = Color(0xFF9CA3AF))
        Text("Impressum", fontSize = 12.sp, color = Color(0xFF9CA3AF), modifier = Modifier.clickable {})
        Text("•", fontSize = 12.sp, color = Color(0xFF9CA3AF))
        Text("Hilfe", fontSize = 12.sp, color = Color(0xFF9CA3AF), modifier = Modifier.clickable {})
    }
}

// --- CREATE USER MODAL (Simplified for brevity but styled) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserModal(onDismiss: () -> Unit) {
    // States für Formularfelder
    var newUserName by remember { mutableStateOf("") }
    var newFullName by remember { mutableStateOf("") }
    var newEmail by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordRepeat by remember { mutableStateOf("") }


    var newUserRegion by remember { mutableStateOf("Deutschland (DE)") }
    var regionMenuExpanded by remember { mutableStateOf(false) }
    val regions = listOf("Deutschland (DE)", "Österreich (AT)", "Schweiz (CH)", "USA (EN-US)", "UK (EN-GB)")


    var createError by remember { mutableStateOf<String?>(null) }
    var createInfo by remember { mutableStateOf<String?>(null) }
    var creating by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    fun validateCreate(): String? {
        if (newUserName.isBlank()) return "Benutzername fehlt."
        if (newFullName.isBlank()) return "Name fehlt."
        if (!newEmail.contains("@") || !newEmail.contains(".")) return "Ungültige E-Mail."
        if (newPassword.length < 6) return "Passwort zu kurz (min 6)."
        if (newPassword != newPasswordRepeat) return "Passwörter stimmen nicht überein."
        return null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(enabled = false) {}, // Klicks abfangen
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 550.dp)
                .fillMaxWidth()
                .heightIn(max = 800.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Neues Konto erstellen",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                    IconButton(onClick = onDismiss) {
                        Text("✕", fontSize = 24.sp, color = Color(0xFF6B7280))
                    }
                }

                Spacer(Modifier.height(24.dp))

                // --- fields ---

                // 1. Username
                OutlinedTextField(
                    value = newUserName,
                    onValueChange = { newUserName = it; createError = null },
                    label = { Text("Benutzername") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF307FE2),
                        focusedLabelColor = Color(0xFF307FE2)
                    )
                )
                Spacer(Modifier.height(12.dp))

                // 2. Name
                OutlinedTextField(
                    value = newFullName,
                    onValueChange = { newFullName = it; createError = null },
                    label = { Text("Vollständiger Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF307FE2),
                        focusedLabelColor = Color(0xFF307FE2)
                    )
                )
                Spacer(Modifier.height(12.dp))

                // 3. E-Mail
                OutlinedTextField(
                    value = newEmail,
                    onValueChange = { newEmail = it; createError = null },
                    label = { Text("E-Mail Adresse") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF307FE2),
                        focusedLabelColor = Color(0xFF307FE2)
                    )
                )
                Spacer(Modifier.height(12.dp))

                // 4. Password
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it; createError = null },
                    label = { Text("Passwort wählen") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF307FE2),
                        focusedLabelColor = Color(0xFF307FE2)
                    )
                )
                Spacer(Modifier.height(12.dp))

                // 5. Password repeat
                OutlinedTextField(
                    value = newPasswordRepeat,
                    onValueChange = { newPasswordRepeat = it; createError = null },
                    label = { Text("Passwort wiederholen") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF307FE2),
                        focusedLabelColor = Color(0xFF307FE2)
                    )
                )
                Spacer(Modifier.height(12.dp))

                // 6. Region (.Dropdown)
                ExposedDropdownMenuBox(
                    expanded = regionMenuExpanded,
                    onExpandedChange = { regionMenuExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = newUserRegion,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Region") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = regionMenuExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF307FE2),
                            focusedLabelColor = Color(0xFF307FE2)
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = regionMenuExpanded,
                        onDismissRequest = { regionMenuExpanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        regions.forEach { region ->
                            DropdownMenuItem(
                                text = { Text(region) },
                                onClick = {
                                    newUserRegion = region
                                    regionMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // --- Feedback ---
                if (createError != null) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = createError!!,
                        color = Color(0xFFEF4444),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (createInfo != null) {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = createInfo!!,
                        color = Color(0xFF10B981), // Grün
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.height(24.dp))

                // --- Buttons ---
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Abbrechen", color = Color(0xFF6B7280))
                    }

                    // Create
                    Button(
                        onClick = {
                            val err = validateCreate()
                            if (err != null) {
                                createError = err
                                createInfo = null
                                return@Button
                            }

                            creating = true
                            createError = null
                            createInfo = null
                            scope.launch {
                                try {
                                    val user = BackendService.register(
                                        RegisterData(
                                            email = newEmail.trim(),
                                            password = newPassword,
                                            userName = newUserName.ifBlank { null }
                                        )
                                    )
                                    createInfo = "Registrierung erfolgreich: ${'$'}{user.email}"
                                    // Close the dialog after short success message
                                    onDismiss()
                                } catch (t: Throwable) {
                                    createError = t.message ?: "Registrierung fehlgeschlagen."
                                } finally {
                                    creating = false
                                }
                            }
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        if (creating) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text("Registrieren", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}