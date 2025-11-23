package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import postulatum.plantum.plantum.model.Semester

@Composable
fun SemesterCard(
    semester: Semester,
    isExtended: Boolean,
    onClick: (Semester) -> Unit,
    modifier: Modifier = Modifier,
    isActivated: Boolean = false
) {
    if (isExtended) {
        extendSemesterCard(semester, onClick, modifier, isActivated)
    } else {
        unextendedSemesterCard(semester, onClick, modifier, isActivated)
    }
}

@Composable
fun extendSemesterCard(
    semester: Semester,
    onClick: (Semester) -> Unit,
    modifier: Modifier = Modifier,
    isActivated: Boolean = false
) {
    Card(
        modifier = modifier
            .width(560.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                role = Role.Button,
                onClick = { onClick(semester) }
            ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = if (isActivated) androidx.compose.foundation.BorderStroke(3.dp, Color(0xFF3B82F6)) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = semester.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111827)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "${semester.modules.size} Kurse",
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280)
                )
                Spacer(Modifier.height(8.dp))

                // Preview up to 3 modules: title + category on the left, credits on the right
                Column {
                    semester.modules.take(3).forEach { module ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = module.name,
                                    fontSize = 13.sp,
                                    color = Color(0xFF111827),
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }


                            Text(
                                text = "${module.credits} ECTS",
                                fontSize = 13.sp,
                                color = Color(0xFF374151),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
                }
            }

            Text(
                text = "→",
                fontSize = 24.sp,
                color = Color(0xFF0EA5E9)
            )
        }
    }
}

@Composable
fun unextendedSemesterCard(
    semester: Semester,
    onClick: (Semester) -> Unit,
    modifier: Modifier = Modifier,
    isActivated: Boolean = false
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                role = Role.Button,
                onClick = { onClick(semester) }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        border = if (isActivated) androidx.compose.foundation.BorderStroke(3.dp, Color(0xFF3B82F6)) else null
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
