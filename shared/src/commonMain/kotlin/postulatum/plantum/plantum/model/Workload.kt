package postulatum.plantum.plantum.model

/**
 * Workloads are generic pairs of main workload and primary workload.
 * Main may be the lecture while primary is the exercise.
 */
data class Workload (
    val main: UInt,
    val primary: UInt
)
