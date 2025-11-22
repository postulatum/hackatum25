package postulatum.plantum.plantum.model

import org.jetbrains.compose.resources.StringResource
import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.*

/**
 * Returns the localized display name for this category.
 */
fun Category.getDisplayName(): StringResource = when (this) {
    Category.ELECTIVE -> Res.string.category_elective
    Category.PROFILE -> Res.string.category_profile
    Category.SOFT_SKILLS -> Res.string.category_soft_skills
    Category.THESIS -> Res.string.category_thesis
    Category.IDP -> Res.string.category_idp
    Category.SEMINAR -> Res.string.category_seminar
    Category.PRACTICAL -> Res.string.category_practical
    Category.MISC -> Res.string.category_misc
}
