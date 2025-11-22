package postulatum.plantum.plantum


import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.Document
import postulatum.plantum.plantum.model.Slot
import postulatum.plantum.plantum.model.User
import com.mongodb.client.model.Filters

// Einfache Service-Klasse ohne Interface-Abhängigkeit
class ServerStudyPlannerService {

    private val slotCollection: MongoCollection<Slot> by lazy {
        MongoService.database.getCollection("slots")
    }

    // NEU: Collection für Benutzer (User)
    private val userCollection: MongoCollection<User> by lazy {
        MongoService.database.getCollection("users")
    }

    // --- Slot-Methoden ---
    /**
     * Liefert alle Slots aus der Datenbank.
     */
    suspend fun getAllSlots(): List<Slot> {
        return slotCollection
            .find()
            .toList()
    }

    /**
     * Speichert einen neuen Slot in der Datenbank.
     */
    suspend fun saveSlot(slot: Slot) {
        slotCollection.insertOne(slot)
    }

    // --- NEUE Benutzer-Methoden ---

    /**
     * Speichert einen neuen Benutzer (Registrierung).
     * Führt automatisch das Hashing des Passworts durch.
     */
    suspend fun registerUser(email: String, password: String, userName: String?): User {
        // 1. Passwort hashen
        val hashedPassword = SecurityService.hashPassword(password)

        // 2. User-Objekt erstellen
        val newUser = User(
            email = email,
            passwordHash = hashedPassword,
            userName = userName
        )

        // 3. In Datenbank speichern
        userCollection.insertOne(newUser)

        return newUser
    }

    /**
     * Ruft einen Benutzer anhand der E-Mail ab (zum Login).
     */
    suspend fun getUserByEmail(email: String): User? {
        // JSON-ähnliche Filter wären möglich, hier nutzen wir einen typisierten Filter für Kompatibilität
        return userCollection
            .find(Filters.eq("email", email))
            .firstOrNull()
    }
}