package postulatum.plantum.model


/** Category of module */
enum class Category(val displayName: String) {
    ELECTIVE("Informatik Wahlmodule"),
    PROFILE("Profilbildung"),
    SOFT_SKILLS("Überfachliche Grundlagen"),
    THESIS("Master-Arbeit"),
    IDP("Interdisciplinary Project (IDP)"),
    SEMINAR("Master-Seminar"),
    PRACTICAL("Advanced Practical Course"),
    MISC("Sonstiges"),
}

/** Subject area of specialization for ELECTIVE modules */
enum class Area(val displayName: String) {
    ALG("ALG - Algorithms"),
    CGV("CGV - Computer Graphics and Vision"),
    DBI("DBI - Databases and Information Systems"),
    DBM("DBM - Digital Biology and Digital Medicine"),
    SE("SE - Engineering Software-intensive Systems"),
    FMA("FMA - Formal Methods and their Applications"),
    MLA("MLA - Machine Learning and Analytics"),
    RRV("RRV - Computer Architecture, Computer Networks, and Distributed Systems"),
    ROB("ROB - Robotics"),
    SP("SP - Security and Privacy"),
    HPC("HPC - Scientific Computing and High Performance Computing"),
    SEMINAR("Master-Seminar"),
    PRACTICAL("Advanced Practical Course"),
    SOFT_SKILLS("Überfachliche Grundlagen"),
    IDP("Interdisciplinary Project"),
    THESIS("Master-Arbeit"),
    GUIDED("Guided Research"),
    MISC("Anderes (z.B. Bachelor-Anerkennung)"),
}

/** Academic term: Wintersemester (WiSe) or Sommersemester (SoSe). */
enum class Term {
    WiSe,
    SoSe
}

/** Instance of a Term (e.g. WiSe25) */
data class Slot(
    val id: String,
    val year: Int,
    val term: Term,
    val semesters: List<Semester>,
)

/** Variant associated with a Slot */
data class Semester(
    val id: String,
    val name: String,
    val modules: List<Module>,
)

data class Module(
    val id: String,
    val name: String,
    val credits: Int,
    val area: Area,
    val isTheoretical: Boolean,
)
