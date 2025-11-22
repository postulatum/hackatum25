package postulatum.plantum.plantum.data

import postulatum.plantum.plantum.model.*

/**
 * Dummy data for testing and development purposes.
 */
object DummyData {
    
    val dummySlots = listOf(
        Slot(
            id = "1",
            name = "Wintersemester 2024/25",
            term = Term.WiSe,
            year = 2024u,
            semester = listOf(
                Semester(
                    id = "sem1",
                    name = "1. Semester",
                    modules = listOf(
                        Module(
                            id = "mod1",
                            tumId = "IN2011",
                            name = "Algorithms",
                            area = Area.ALG,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.Exam,
                            language = Language.EN,
                            isTheoretical = true
                        )
                    )
                ),
                Semester(
                    id = "sem2",
                    name = "2. Semester",
                    modules = listOf(
                        Module(
                            id = "mod2",
                            tumId = "IN2012",
                            name = "Machine Learning",
                            area = Area.MLA,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.Exam,
                            language = Language.EN,
                            isTheoretical = false
                        ),
                        Module(
                            id = "mod3",
                            tumId = "IN2013",
                            name = "Software Engineering",
                            area = Area.SE,
                            workload = Workload(45u, 45u),
                            credits = 6u,
                            examType = ExaminationType.Project,
                            language = Language.DE_EN,
                            isTheoretical = false
                        )
                    )
                )
            )
        ),
        Slot(
            id = "2",
            name = "Sommersemester 2025",
            term = Term.SoSe,
            year = 2025u,
            semester = listOf(
                Semester(
                    id = "sem3",
                    name = "3. Semester",
                    modules = listOf(
                        Module(
                            id = "mod4",
                            tumId = "IN2014",
                            name = "Databases",
                            area = Area.DBI,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.Exam,
                            language = Language.EN,
                            isTheoretical = true
                        )
                    )
                )
            )
        )
    )
}
