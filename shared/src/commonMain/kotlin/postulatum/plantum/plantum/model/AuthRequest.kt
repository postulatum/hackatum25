package postulatum.plantum.plantum.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterData(
    val email: String,
    val password: String,
    val userName: String?
)

@Serializable
data class LoginData(
    val email: String,
    val password: String
)