package postulatum.plantum.plantum

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.plantum_logo
import postulatum.plantum.plantum.dashboard.DashboardScreen

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