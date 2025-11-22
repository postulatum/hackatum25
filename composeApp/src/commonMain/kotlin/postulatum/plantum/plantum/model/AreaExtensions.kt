package postulatum.plantum.plantum.model

import org.jetbrains.compose.resources.StringResource
import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.*

/**
 * Returns the localized display name for this area.
 */
fun Area.getDisplayName(): StringResource = when (this) {
    Area.ALG -> Res.string.area_alg
    Area.CGV -> Res.string.area_cgv
    Area.DBI -> Res.string.area_dbi
    Area.DBM -> Res.string.area_dbm
    Area.SE -> Res.string.area_se
    Area.FMA -> Res.string.area_fma
    Area.MLA -> Res.string.area_mla
    Area.RRV -> Res.string.area_rrv
    Area.ROB -> Res.string.area_rob
    Area.SP -> Res.string.area_sp
    Area.HPC -> Res.string.area_hpc
    Area.SEMINAR -> Res.string.area_seminar
    Area.PRACTICAL -> Res.string.area_practical
    Area.SOFT_SKILLS -> Res.string.area_soft_skills
    Area.IDP -> Res.string.area_idp
    Area.THESIS -> Res.string.area_thesis
    Area.GUIDED -> Res.string.area_guided
    Area.MISC -> Res.string.area_misc
}
