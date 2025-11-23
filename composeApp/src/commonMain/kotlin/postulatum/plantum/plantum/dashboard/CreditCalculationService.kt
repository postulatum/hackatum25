package postulatum.plantum.plantum.dashboard

import postulatum.plantum.plantum.model.Category
import postulatum.plantum.plantum.model.Module
import postulatum.plantum.plantum.model.Semester
import postulatum.plantum.plantum.model.Area


class CreditCalculationService() {

    fun calculateCreditCategories(activatedSemesters: List<Semester>) : Map<Category, UInt> {
        val categoryCredits = mutableMapOf<Category, UInt>()
        for (semester in activatedSemesters) {
            for (module in semester.modules) {
                if (!categoryCredits.containsKey(module.category)) {
                    categoryCredits[module.category] = 0u
                }
                categoryCredits[module.category] = categoryCredits[module.category]?.plus(module.credits) ?: 0u

            }
        }

        return categoryCredits
    }

    fun getModulesByCategory(activatedSemesters: List<Semester>): Map<Category, List<Module>> {
        val modulesByCategory = mutableMapOf<Category, MutableList<Module>>()
        for (semester in activatedSemesters) {
            for (module in semester.modules) {
                if (!modulesByCategory.containsKey(module.category)) {
                    modulesByCategory[module.category] = mutableListOf()
                }
                modulesByCategory[module.category]?.add(module)
            }
        }
        return modulesByCategory
    }

    fun getSumOfCredits(activatedSemesters: List<Semester>) : UInt {
        return calculateCreditCategories(activatedSemesters).values.sum()
    }

    fun getSpecialisationCredits(activatedSemesters: List<Semester>): Map<Area, UInt> {
        val specialisationCredits = mutableMapOf<Area, UInt>()
        for (semester in activatedSemesters) {
            for (module in semester.modules) {
                val area = module.area
                if (area != null && module.category == Category.ELECTIVE) {
                    if (!specialisationCredits.containsKey(area)) {
                        specialisationCredits[area] = 0u
                    }
                    specialisationCredits[area] = specialisationCredits[area]?.plus(module.credits) ?: 0u
                }
            }
        }
        return specialisationCredits
    }

    /**
     * Returns the main specialisation (Schwerpunkt) - the Area with the most credits from ELECTIVE modules
     * Returns null if no ELECTIVE modules with area exist
     */
    fun getMainSpecialisation(activatedSemesters: List<Semester>): Pair<Area, UInt>? {
        val specialisationCredits = getSpecialisationCredits(activatedSemesters)
        return specialisationCredits
            .maxByOrNull { it.value }
            ?.toPair()
    }

    /**
     * Returns the two minor specialisations (Nebenschwerpunkte) - the next 2 areas with most credits from ELECTIVE modules
     * Returns a list with 0-2 elements
     */
    fun getMinorSpecialisations(activatedSemesters: List<Semester>): List<Pair<Area, UInt>> {
        val specialisationCredits = getSpecialisationCredits(activatedSemesters)
        val mainSpecialisation = getMainSpecialisation(activatedSemesters)?.first

        return specialisationCredits
            .filter { it.key != mainSpecialisation }
            .toList()
            .sortedByDescending { it.second }
            .take(2)
    }

    /**
     * Returns all modules for a specific area in the activated semesters
     */
    fun getModulesByArea(activatedSemesters: List<Semester>, area: Area): List<Module> {
        val modules = mutableListOf<Module>()
        for (semester in activatedSemesters) {
            for (module in semester.modules) {
                if (module.area == area && module.category == Category.ELECTIVE) {
                    modules.add(module)
                }
            }
        }
        return modules
    }
}