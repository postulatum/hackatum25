package postulatum.plantum

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform