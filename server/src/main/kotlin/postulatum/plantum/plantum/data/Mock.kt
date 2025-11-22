package postulatum.plantum.plantum.data

import postulatum.plantum.plantum.model.Area
import postulatum.plantum.plantum.model.ExaminationType
import postulatum.plantum.plantum.model.Language
import postulatum.plantum.plantum.model.Module
import postulatum.plantum.plantum.model.Workload

/**
 * Static mock data.
 */
class Mock {
    companion object {
        val algModules = arrayOf(
            Module("13406b92-c7a1-11f0-9378-e880887b8081", "IN2239", "Algorithmic Game Theory", Area.ALG, Workload(2u, 2u), 5u, ExaminationType.Exam, Language.EN, true),
            Module("13431200-c7a1-11f0-b791-e880887b8081", "IN2211", "Auction Theory and Market Design", Area.ALG, Workload(2u, 2u), 5u, ExaminationType.Exam, Language.EN, true),
            Module("134312a5-c7a1-11f0-8a3f-e880887b8081", "IN2003", "Efficient Algorithms and Data Structures", Area.ALG, Workload(4u, 2u), 8u, ExaminationType.Exam, Language.EN, true),
            Module("13431316-c7a1-11f0-a4e2-e880887b8081", "IN2007", "Complexity Theory", Area.ALG, Workload(4u, 2u), 8u, ExaminationType.Exam, Language.EN, true),
            Module("13431374-c7a1-11f0-b31a-e880887b8081", "IN2304", "Online and Approximation Algorithms", Area.ALG, Workload(4u, 2u), 8u, ExaminationType.Exam, Language.EN, true)
        )

        val cgvModules = arrayOf(
            Module("b55923ed-c7a6-11f0-8ba7-e880887b8081","IN2015", "Image Synthesis",   Area.CGV, Workload(4u,0u), 5u, ExaminationType.Exam, Language.EN, false),
            Module("b559251a-c7a6-11f0-b6df-e880887b8081","IN2246", "Computer Vision I: Variational Methods",   Area.CGV, Workload(4u,2u), 8u, ExaminationType.Exam, Language.EN, true),
            Module("b559255e-c7a6-11f0-9914-e880887b8081","IN2228", "Computer Vision II: Multiple View Geometry",   Area.CGV, Workload(4u,2u), 8u, ExaminationType.Exam, Language.EN, true),
            Module("b559258e-c7a6-11f0-a710-e880887b8081","IN2124", "Basic Mathematical Methods for Imaging and Visualization",   Area.CGV, Workload(2u,2u), 5u, ExaminationType.Exam, Language.EN, false),
            Module("b55925b2-c7a6-11f0-a476-e880887b8081","IN2026", "Visual Data Analytics",   Area.CGV, Workload(3u,1u), 5u, ExaminationType.Exam, Language.EN, false)
        )

        val dbiModules = arrayOf(
            Module("f950fbfa-c7a6-11f0-a6b4-e880887b8081", "IN2219", "Query Optimization",   Area.DBI, Workload(3u,2u), 6u, ExaminationType.Exam, Language.EN, false),
            Module("f950fd2b-c7a6-11f0-96c9-e880887b8081", "IN2118", "Database Systems on Modern CPU Architectures",   Area.DBI, Workload(3u,2u), 6u, ExaminationType.Exam, Language.EN, false),
            Module("f950fd6a-c7a6-11f0-9669-e880887b8081", "IN2031", "Einsatz und Realisierung von Datenbanksystemen",   Area.DBI, Workload(3u,2u), 6u, ExaminationType.Exam, Language.DE, false),
            Module("f950fd91-c7a6-11f0-81d3-e880887b8081", "IN2267", "Transaction Systems",   Area.DBI, Workload(3u,2u), 6u, ExaminationType.Exam, Language.EN, false)
        )

        val seModules = arrayOf(
            Module("3e992270-c7a7-11f0-8d89-e880887b8081", "IN2309", "Advanced Topics of Software Engineering",   Area.SE, Workload(4u,2u), 8u, ExaminationType.Exam, Language.DE_EN, false),
            Module("3e99239e-c7a7-11f0-bc92-e880887b8081", "IN2084", "Fortgeschrittene Themen des Softwaretests",   Area.SE, Workload(2u,2u), 5u, ExaminationType.Exam, Language.DE, false),
            Module("3e9923d2-c7a7-11f0-92fa-e880887b8081", "IN2078", "Grundlagen der Programm- und Systementwicklung",   Area.SE, Workload(3u,0u), 4u, ExaminationType.Exam, Language.DE, false),
            Module("3e9923f4-c7a7-11f0-99a7-e880887b8081", "IN2087", "Software Engineering for Business Applications - Master's Course: Web Application Engineering",   Area.SE, Workload(2u,2u), 8u, ExaminationType.Project, Language.EN, false)
        )

        val fmaModules = arrayOf(
            Module("5b8a013a-c7a7-11f0-b8f5-e880887b8081", "IN2041", "Automata and Formal Languages", Area.FMA, Workload(4u,2u), 8u, ExaminationType.Exam, Language.EN, true),
            Module("5b8a0285-c7a7-11f0-927d-e880887b8081", "IN2227", "Compilerbau I",   Area.FMA, Workload(2u,2u), 5u, ExaminationType.Exam, Language.DE_EN, true),
            Module("5b8a02bc-c7a7-11f0-b99d-e880887b8081", "IN2050", "Model Checking",   Area.FMA, Workload(4u,2u), 8u, ExaminationType.Exam, Language.EN, true),
            Module("5b8a02e2-c7a7-11f0-aec2-e880887b8081", "IN2055", "Semantics",   Area.FMA, Workload(4u,2u), 8u, ExaminationType.Exam, Language.EN, true)
        )

        val mlaModules = arrayOf(
            Module("6adacf64-c7a7-11f0-b9b7-e880887b8081", "IN2298", "Advanced Deep Learning for Physics",   Area.MLA, Workload(4u,0u), 6u, ExaminationType.Exam, Language.EN, false),
            Module("6adad095-c7a7-11f0-9950-e880887b8081", "IN2028", "Business Analytics and Machine Learning",   Area.MLA, Workload(2u,2u), 5u, ExaminationType.Exam, Language.EN, false),
            Module("6adad0d0-c7a7-11f0-9ad9-e880887b8081", "IN2346", "Introduction to Deep Learning",   Area.MLA, Workload(2u,2u), 6u, ExaminationType.Exam, Language.EN, false),
            Module("6adad0f7-c7a7-11f0-b094-e880887b8081", "IN2064", "Machine Learning",   Area.MLA, Workload(4u,2u), 8u, ExaminationType.Exam, Language.EN, false),
            Module("6adad121-c7a7-11f0-8a26-e880887b8081", "IN2323", "Machine Learning for Graphs and Sequential Data",   Area.MLA, Workload(2u,2u), 5u, ExaminationType.Exam, Language.EN, false)
        )

        val rrvModules = arrayOf(
            Module("7be67deb-c7a7-11f0-b641-e880887b8081", "IN2324", "Connected Mobility Basics",   Area.RRV, Workload(4u,2u), 8u, ExaminationType.Exam, Language.EN, false),
            Module("7be67f12-c7a7-11f0-b6bf-e880887b8081", "IN2097", "Advanced Computer Networking",   Area.RRV, Workload(3u,1u), 5u, ExaminationType.Exam, Language.EN, false),
            Module("7be67f42-c7a7-11f0-8900-e880887b8081", "IN2076", "Advanced Computer Architecture",   Area.RRV, Workload(4u,0u), 6u, ExaminationType.Exam, Language.EN, false),
            Module("7be67f68-c7a7-11f0-a4c0-e880887b8081", "IN2259", "Distributed Systems",   Area.RRV, Workload(3u,1u), 5u, ExaminationType.Exam, Language.EN, false)
        )

        val robModules = arrayOf(
            Module("9b63bebe-c7a7-11f0-8a36-e880887b8081", "IN2060", "Echtzeitsysteme",   Area.ROB, Workload(3u,2u), 6u, ExaminationType.Exam, Language.DE_EN, false),
            Module("9b63bfef-c7a7-11f0-b8df-e880887b8081", "IN2061", "Einführung in die digitale Signalverarbeitung",   Area.ROB, Workload(3u,3u), 7u, ExaminationType.Exam, Language.DE_EN, false),
            Module("9b63c026-c7a7-11f0-98c4-e880887b8081", "IN2406", "Fundamentals of Artificial Intelligence",   Area.ROB, Workload(3u,2u), 6u, ExaminationType.Exam, Language.EN, false),
            Module("9b63c04a-c7a7-11f0-9c1d-e880887b8081", "IN2222", "Cognitive Systems",   Area.ROB, Workload(3u,1u), 5u, ExaminationType.Exam, Language.EN, false),
            Module("9b63c069-c7a7-11f0-9991-e880887b8081", "IN2067", "Robotics",   Area.ROB, Workload(3u,2u), 6u, ExaminationType.Exam, Language.EN, false)
        )

        val spModules = arrayOf(
            Module("ce531391-c7a7-11f0-9e86-e880887b8081", "CIT3330002", "IT-Sicherheit 2",   Area.SP, Workload(2u,2u), 5u, ExaminationType.Exam, Language.DE, false),
            Module("ce5314c7-c7a7-11f0-a2eb-e880887b8081", "CIT3330003", "Kryptografie",   Area.SP, Workload(3u,1u), 5u, ExaminationType.Exam, Language.DE, true),
            Module("ce5314fd-c7a7-11f0-b957-e880887b8081", "IN2101", "Network Security",   Area.SP, Workload(3u,1u), 5u, ExaminationType.Exam, Language.EN, false),
            Module("ce531523-c7a7-11f0-838b-e880887b8081", "IN2194", "Peer-to-Peer-Systems and Security",   Area.SP, Workload(3u,2u), 6u, ExaminationType.Project, Language.EN, false),
            Module("ce531546-c7a7-11f0-b4fb-e880887b8081", "IN2178", "Security Engineering",   Area.SP, Workload(2u,2u), 5u, ExaminationType.Exam, Language.EN, false)
        )

        val hpcModules = arrayOf(
            Module("dedfa667-c7a7-11f0-87a5-e880887b8081", "IN2345", "Algorithms for Uncertainty Quantification",   Area.HPC, Workload(2u,2u), 5u, ExaminationType.Exam, Language.EN, true),
            Module("dedfa796-c7a7-11f0-9af5-e880887b8081", "IN2001", "Algorithms for Scientific Computing",   Area.HPC, Workload(4u,2u), 8u, ExaminationType.Exam, Language.EN, true),
            Module("dedfa7cb-c7a7-11f0-b11e-e880887b8081", "IN2010", "Modelling and Simulation",   Area.HPC, Workload(4u,2u), 8u, ExaminationType.Exam, Language.EN, true),
            Module("dedfa7f3-c7a7-11f0-8bf4-e880887b8081", "IN2147", "Parallel Programming",   Area.HPC, Workload(2u,2u), 5u, ExaminationType.Exam, Language.EN, false),
            Module("dedfa817-c7a7-11f0-b49d-e880887b8081", "IN2311", "Turbulent Flow Simulation on HPC-Systems",   Area.HPC, Workload(2u,1u), 5u, ExaminationType.Exam, Language.EN, true)
        )

        val extraModules = arrayOf(
            Module("e744d136-c7a7-11f0-b2ab-e880887b8081", "IN2169", "Guided Research",   Area.GUIDED, Workload(10u,0u), 10u, ExaminationType.Project, Language.EN, false),
            Module("e744d26c-c7a7-11f0-9b36-e880887b8081", "IN2175", "Vertiefendes Master-Praktikum",   Area.PRACTICAL, Workload(6u,0u), 10u, ExaminationType.Project, Language.DE_EN, false),
            Module("e744d2a5-c7a7-11f0-bbad-e880887b8081", "IN2257", "Zusätzliches Master-Praktikum",   Area.PRACTICAL, Workload(6u, 0u), 10u, ExaminationType.Project, Language.DE_EN, false)
        )

        val allModules: Array<Module> = arrayOf(
            algModules,
            cgvModules,
            dbiModules,
            seModules,
            fmaModules,
            mlaModules,
            rrvModules,
            robModules,
            spModules,
            hpcModules,
            extraModules
        ).flatten().toTypedArray();
    }
}