package postulatum.plantum.plantum

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.compose_multiplatform
import plantum.composeapp.generated.resources.plantum_logo

@Composable
@Preview
fun App() {
    MaterialTheme {
        var loggedInUser by remember { mutableStateOf<String?>(null) }

        if (loggedInUser == null) {
            LoginScreen(
                onLoginSuccess = { user -> loggedInUser = user }
            )
        } else {
            DashboardScreen(
                userName = loggedInUser,
                logo = painterResource(Res.drawable.plantum_logo),
                onLogout = { loggedInUser = null }
            )
        }
    }
}