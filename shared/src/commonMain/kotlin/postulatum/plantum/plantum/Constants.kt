package postulatum.plantum.plantum

import postulatum.plantum.plantum.model.Category

const val SERVER_PORT = 9321
const val SERVER_IP = "localhost"
const val BASE_ENPOINT = "/api/v1"


/**
 * The credit goals the master students need to hit for every category.
 */
const val TotalCreditGoal: UInt = 120u

/**
 * The credit goals the master students need to hit for every category.
 */
val CategoryCreditGoals: Map<Category, UInt> = mapOf(
    Category.ELECTIVE to 43u,
    Category.PROFILE to 10u,
    Category.SOFT_SKILLS to 6u,
    Category.IDP to 16u,
    Category.THESIS to 30u,
    Category.SEMINAR to 5u,
    Category.PRACTICAL to 10u,
    Category.MISC to 0u,
)
