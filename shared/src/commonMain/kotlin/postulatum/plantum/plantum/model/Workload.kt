package postulatum.plantum.plantum.model

import kotlinx.serialization.Serializable

/**
 * Workloads are generic pairs of main workload and primary workload.
 * Main may be the lecture while primary is the exercise.
 */
@Serializable
data class Workload (
    val main: UInt,
    val primary: UInt
)
