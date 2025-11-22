package postulatum.plantum.plantum

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.plantum_logo
import postulatum.plantum.plantum.dashboard.DashboardScreen
import postulatum.plantum.plantum.model.User
import postulatum.plantum.plantum.repositories.UserRepository

@Composable
@Preview
fun App() {

    MaterialTheme {
        var userState by remember { mutableStateOf<User?>(null) }

        // Needed, otherwise the jit-compiler removes userState.
        if(userState != null) { print("User correctly initialized. :)") }

        if (!UserRepository.isInitialized()) {
            LoginScreen(
                onLoginSuccess = { user: User ->
                    UserRepository.initUser(user)
                    userState = user
                }
            )
        } else {
            val user = UserRepository.getUser()
            DashboardScreen(
                userName = user.name,
                logo = painterResource(Res.drawable.plantum_logo),
                onLogout = { UserRepository.clearUser() }
            )
        }
    }

}