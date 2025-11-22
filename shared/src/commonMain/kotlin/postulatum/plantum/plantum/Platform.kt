package postulatum.plantum.plantum

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform