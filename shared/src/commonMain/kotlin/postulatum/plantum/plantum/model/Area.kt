package postulatum.plantum.plantum.model

import kotlinx.serialization.Serializable

/**
 * These values describe the subject areas
 * of the various modules offered.
 */
@Serializable
enum class Area {
    ALG,
    CGV,
    DBI,
    DBM,
    SE,
    FMA,
    MLA,
    RRV,
    ROB,
    SP,
    HPC,
    SEMINAR,
    PRACTICAL,
    SOFT_SKILLS,
    IDP,
    THESIS,
    GUIDED,
    MISC
}