package postulatum.plantum.plantum.dashboard

import postulatum.plantum.plantum.model.Category
import postulatum.plantum.plantum.model.Module
import postulatum.plantum.plantum.model.Semester

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
}