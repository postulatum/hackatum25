package postulatum.plantum.plantum.model

import kotlinx.serialization.Serializable

/**
 * A module is a single course offered at the university that can be taken in one semester.
 */
@Serializable
data class Module(
    val id: String,
    val tumId: String,
    val name: String,
    val area: Area,
    val workload: Workload,
    val credits: UInt,
    val examType: ExaminationType,
    val language: Language,
    val isTheoretical: Boolean
)
