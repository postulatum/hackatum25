package postulatum.plantum.plantum.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    // Optional ID; MongoDB can generate _id automatically if null/absent
    val id: String? = null,
    val email: String,
    // Only the hashed value is stored
    val passwordHash: String,
    val userName: String? = null,
    val accessToken: String? = null,
    val name: String? = null
)