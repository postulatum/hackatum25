package postulatum.plantum.plantum

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import postulatum.plantum.plantum.model.*

@Composable
fun DashboardScreen(
    userName: String?,
    logo: Painter,
    onLogout: () -> Unit
) {
    // Example slots with multiple semesters
    val slots = listOf(
        Slot(
            id = "1", 
            name = "Wintersemester 2024/25", 
            term = Term.WiSe, 
            year = 2024u, 
            semester = arrayOf(
                Semester(
                    id = "sem1",
                    name = "1. Semester",
                    modules = arrayOf(
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
                    modules = arrayOf(
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
            semester = arrayOf(
                Semester(
                    id = "sem3",
                    name = "3. Semester",
                    modules = arrayOf(
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Header
        StarterHeader(
            userName = userName,
            logo = logo
        )
        
        Spacer(Modifier.height(32.dp))
        
        // Welcome Section
        Text(
            text = "Willkommen, $userName!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )
        
        Spacer(Modifier.height(16.dp))
        
        Text(
            text = "TUM Masterplaner Dashboard",
            fontSize = 16.sp,
            color = Color(0xFF6B7280)
        )
        
        Spacer(Modifier.height(48.dp))
        
        // Semester Overview
        for (slot in slots) {
            SlotCard(slot = slot) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Iterate over the semesters in this slot
                    for (semester in slot.semester) {
                        SemesterCard(semester = semester)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
        
        
        // Logout Button
        OutlinedButton(
            onClick = onLogout,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFEF4444)
            )
        ) {
            Text("Abmelden")
        }
    }
}


