package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import postulatum.plantum.plantum.model.Semester

@Composable
fun SemesterCard(
    semester: Semester,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(280.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = semester.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF111827)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${semester.modules.size} Kurse",
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280)
                )
            }
            
            // Pfeil-Icon als Indikator für Interaktivität
            Text(
                text = "→",
                fontSize = 20.sp,
                color = Color(0xFF0EA5E9)
            )
        }
    }
}
