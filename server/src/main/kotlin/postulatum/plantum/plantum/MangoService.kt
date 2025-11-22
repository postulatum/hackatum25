package postulatum.plantum.plantum

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider

object MongoService {
    // Wähle den Namen deiner Datenbank, z.B. "planTUM_production"
    private const val DATABASE_NAME = "plantum_db"

    // Verwende die vom Benutzer vorgegebene Verbindungs-URI direkt (ohne String-Building)
    private const val CONNECTION_STRING =
        "mongodb+srv://fabianhirschmeier1_db_user:HiC5l8IezSxWrhfs@plantum.vpfsgis.mongodb.net/?appName=planTUM"

    // Erstellt den Client nur einmal, wenn das Objekt geladen wird
    private val client: MongoClient by lazy {
        val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()

        // POJO CodecRegistry für Kotlin/Java-Datenklassen aktivieren
        val pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
        )

        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(CONNECTION_STRING))
            .serverApi(serverApi)
            .codecRegistry(pojoCodecRegistry)
            .build()

        MongoClient.create(mongoClientSettings)
    }

    // Stellt die Datenbank-Instanz bereit
    val database by lazy { client.getDatabase(DATABASE_NAME) }

    // Optional: Testen der Verbindung
    suspend fun ping(): Boolean {
        return try {
            database.runCommand(org.bson.Document("ping", 1))
            true
        } catch (e: Exception) {
            println("MongoDB Ping fehlgeschlagen: ${e.message}")
            false
        }
    }
}