package postulatum.plantum.plantum.model

import kotlinx.serialization.Serializable

/**
 * These enum values indicate the study category
 * to which the associated credits are assigned.
 */

@Serializable
enum class Category {
    ELECTIVE,
    PROFILE,
    SOFT_SKILLS,
    THESIS,
    IDP,
    SEMINAR,
    PRACTICAL,
    MISC
}