package postulatum.plantum.plantum.data

import postulatum.plantum.plantum.model.*

/**
 * Dummy data for testing and development purposes.
 * Covers all Areas and Categories for comprehensive testing.
 */
object DummyData {
    
    val dummySlots = listOf(
        Slot(
            id = "1",
            description = "Wintersemester 2024/25",
            term = Term.WISE,
            year = 2024u,
            semester = listOf(
                Semester(
                    id = "sem1",
                    name = "Basis-Semester",
                    modules = listOf(
                        Module(
                            id = "mod1",
                            tumId = "IN2011",
                            name = "Advanced Algorithms",
                            area = Area.ALG,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = true,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod2",
                            tumId = "IN2012",
                            name = "Machine Learning",
                            area = Area.MLA,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod3",
                            tumId = "IN2013",
                            name = "Software Engineering",
                            area = Area.SE,
                            workload = Workload(45u, 45u),
                            credits = 6u,
                            examType = ExaminationType.PROJECT,
                            language = Language.DE_EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod4",
                            tumId = "IN2014",
                            name = "Master Seminar: AI Ethics",
                            area = Area.SEMINAR,
                            workload = Workload(30u, 60u),
                            credits = 5u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = true,
                            category = Category.SEMINAR
                        )
                    )
                ),
                Semester(
                    id = "sem2",
                    name = "Vertiefungs-Semester",
                    modules = listOf(
                        Module(
                            id = "mod5",
                            tumId = "IN2015",
                            name = "Database Systems",
                            area = Area.DBI,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = true,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod6",
                            tumId = "IN2016",
                            name = "Computer Graphics and Visualization",
                            area = Area.CGV,
                            workload = Workload(45u, 45u),
                            credits = 6u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod7",
                            tumId = "IN2017",
                            name = "Robotics",
                            area = Area.ROB,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        )
                    )
                ),
                Semester(
                    id = "sem3",
                    name = "Projekt-Semester",
                    modules = listOf(
                        Module(
                            id = "mod8",
                            tumId = "IN2018",
                            name = "Advanced Practical Course: Web Development",
                            area = Area.PRACTICAL,
                            workload = Workload(30u, 150u),
                            credits = 10u,
                            examType = ExaminationType.PROJECT,
                            language = Language.DE_EN,
                            isTheoretical = false,
                            category = Category.PRACTICAL
                        ),
                        Module(
                            id = "mod9",
                            tumId = "IN2019",
                            name = "Scientific Writing",
                            area = Area.SOFT_SKILLS,
                            workload = Workload(15u, 15u),
                            credits = 2u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = true,
                            category = Category.SOFT_SKILLS
                        )
                    )
                )
            )
        ),
        Slot(
            id = "2",
            description = "Sommersemester 2025",
            term = Term.SOSE,
            year = 2025u,
            semester = listOf(
                Semester(
                    id = "sem4",
                    name = "Spezialisierungs-Semester",
                    modules = listOf(
                        Module(
                            id = "mod10",
                            tumId = "IN2020",
                            name = "Data Mining and Machine Learning",
                            area = Area.DBM,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.PROFILE
                        ),
                        Module(
                            id = "mod11",
                            tumId = "IN2021",
                            name = "Formal Methods in Algorithms",
                            area = Area.FMA,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = true,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod12",
                            tumId = "IN2022",
                            name = "High Performance Computing",
                            area = Area.HPC,
                            workload = Workload(45u, 45u),
                            credits = 6u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod13",
                            tumId = "IN2023",
                            name = "Security and Privacy",
                            area = Area.SP,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = true,
                            category = Category.ELECTIVE
                        )
                    )
                ),
                Semester(
                    id = "sem5",
                    name = "Forschungs-Semester",
                    modules = listOf(
                        Module(
                            id = "mod14",
                            tumId = "IN2024",
                            name = "Interdisciplinary Project",
                            area = Area.IDP,
                            workload = Workload(30u, 150u),
                            credits = 16u,
                            examType = ExaminationType.PROJECT,
                            language = Language.DE_EN,
                            isTheoretical = false,
                            category = Category.IDP
                        ),
                        Module(
                            id = "mod15",
                            tumId = "IN2025",
                            name = "Distributed Systems",
                            area = Area.RRV,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        )
                    )
                )
            )
        ),
        Slot(
            id = "3",
            description = "Wintersemester 2025/26",
            term = Term.WISE,
            year = 2025u,
            semester = listOf(
                Semester(
                    id = "sem6",
                    name = "Abschluss-Semester",
                    modules = listOf(
                        Module(
                            id = "mod16",
                            tumId = "IN2026",
                            name = "Master Thesis",
                            area = Area.THESIS,
                            workload = Workload(60u, 840u),
                            credits = 30u,
                            examType = ExaminationType.EXAM,
                            language = Language.DE_EN,
                            isTheoretical = false,
                            category = Category.THESIS
                        )
                    )
                ),
                Semester(
                    id = "sem7",
                    name = "Zusatz-Optionen",
                    modules = listOf(
                        Module(
                            id = "mod17",
                            tumId = "IN2027",
                            name = "Deep Learning",
                            area = Area.MLA,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod18",
                            tumId = "IN2028",
                            name = "Advanced Software Engineering",
                            area = Area.SE,
                            workload = Workload(45u, 45u),
                            credits = 6u,
                            examType = ExaminationType.PROJECT,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod19",
                            tumId = "IN2029",
                            name = "Presentation Skills",
                            area = Area.SOFT_SKILLS,
                            workload = Workload(15u, 15u),
                            credits = 2u,
                            examType = ExaminationType.EXAM,
                            language = Language.DE_EN,
                            isTheoretical = false,
                            category = Category.SOFT_SKILLS
                        ),
                        Module(
                            id = "mod20",
                            tumId = "IN2030",
                            name = "Guided Research",
                            area = Area.GUIDED,
                            workload = Workload(30u, 60u),
                            credits = 10u,
                            examType = ExaminationType.PROJECT,
                            language = Language.EN,
                            isTheoretical = true,
                            category = Category.MISC
                        )
                    )
                )
            )
        ),
        Slot(
            id = "4",
            description = "Flexible Planung",
            term = Term.SOSE,
            year = 2026u,
            semester = listOf(
                Semester(
                    id = "sem8",
                    name = "Wahlmodul-Mix 1",
                    modules = listOf(
                        Module(
                            id = "mod21",
                            tumId = "IN2031",
                            name = "Computer Vision",
                            area = Area.CGV,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.PROFILE
                        ),
                        Module(
                            id = "mod22",
                            tumId = "IN2032",
                            name = "Natural Language Processing",
                            area = Area.MLA,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod23",
                            tumId = "IN2033",
                            name = "Cloud Computing",
                            area = Area.RRV,
                            workload = Workload(45u, 45u),
                            credits = 6u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        )
                    )
                ),
                Semester(
                    id = "sem9",
                    name = "Wahlmodul-Mix 2",
                    modules = listOf(
                        Module(
                            id = "mod24",
                            tumId = "IN2034",
                            name = "Autonomous Systems",
                            area = Area.ROB,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod25",
                            tumId = "IN2035",
                            name = "Cryptography",
                            area = Area.SP,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = true,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod26",
                            tumId = "IN2036",
                            name = "Big Data Analytics",
                            area = Area.DBM,
                            workload = Workload(45u, 45u),
                            credits = 6u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod27",
                            tumId = "IN2037",
                            name = "Team Management",
                            area = Area.SOFT_SKILLS,
                            workload = Workload(15u, 15u),
                            credits = 2u,
                            examType = ExaminationType.EXAM,
                            language = Language.DE_EN,
                            isTheoretical = false,
                            category = Category.SOFT_SKILLS
                        )
                    )
                ),
                Semester(
                    id = "sem10",
                    name = "Wahlmodul-Mix 3",
                    modules = listOf(
                        Module(
                            id = "mod28",
                            tumId = "IN2038",
                            name = "Quantum Computing",
                            area = Area.FMA,
                            workload = Workload(60u, 30u),
                            credits = 8u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = true,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod29",
                            tumId = "IN2039",
                            name = "Game Development",
                            area = Area.CGV,
                            workload = Workload(45u, 45u),
                            credits = 6u,
                            examType = ExaminationType.PROJECT,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        ),
                        Module(
                            id = "mod30",
                            tumId = "IN2040",
                            name = "Blockchain Technology",
                            area = Area.DBI,
                            workload = Workload(45u, 45u),
                            credits = 6u,
                            examType = ExaminationType.EXAM,
                            language = Language.EN,
                            isTheoretical = false,
                            category = Category.ELECTIVE
                        )
                    )
                )
            )
        )
    )
}
