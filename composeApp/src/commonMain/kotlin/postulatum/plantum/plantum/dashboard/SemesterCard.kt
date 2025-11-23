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
            .fillMaxWidth()
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF000000)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "${semester.modules.size} Kurse",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
                Spacer(Modifier.height(16.dp))

                // Module list as table
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Column headers
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "MODULNAME",
                            fontSize = 12.sp,
                            color = Color(0xFF9CA3AF),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(0.4f).padding(end = 16.dp)
                        )

                        Text(
                            text = "FACHGEBIET / KATEGORIE",
                            fontSize = 12.sp,
                            color = Color(0xFF9CA3AF),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(0.3f).padding(end = 16.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ECTS",
                                fontSize = 12.sp,
                                color = Color(0xFF9CA3AF),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.width(30.dp)
                            )
                            // Spacer für die Icon-Buttons
                            Spacer(modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.size(32.dp))
                        }
                    }

                    // Trennlinie unter den Headers
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        thickness = 1.dp,
                        color = Color(0xFFE5E7EB)
                    )

                    semester.modules.forEach { module ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Links: Modulname (mit fester Breite für einheitlichen Abstand)
                            Text(
                                text = module.name,
                                fontSize = 16.sp,
                                color = Color(0xFF000000),
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(0.4f).padding(end = 16.dp)
                            )

                            // Mitte: Area (nur bei ELECTIVE) mit Kategorie
                            Column(
                                modifier = Modifier.weight(0.3f).padding(end = 16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                if (module.category == postulatum.plantum.plantum.model.Category.ELECTIVE) {
                                    Text(
                                        text = module.area.name,
                                        fontSize = 14.sp,
                                        color = Color(0xFF1F2937),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = module.category.name,
                                    fontSize = 13.sp,
                                    color = Color(0xFF6B7280),
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            // Rechts: Credits + Icons
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Credits
                                Text(
                                    text = "${module.credits}",
                                    fontSize = 16.sp,
                                    color = Color(0xFF000000),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.width(30.dp)
                                )

                                // Bearbeitungsstift (blau)
                                IconButton(
                                    onClick = { /* TODO: Bearbeitungsfunktion */ },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Text(
                                        text = "✏️",
                                        fontSize = 16.sp,
                                        color = Color(0xFF3B82F6)
                                    )
                                }

                                // Mülltonne (rot)
                                IconButton(
                                    onClick = { /* TODO: Löschfunktion */ },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Text(
                                        text = "🗑️",
                                        fontSize = 16.sp,
                                        color = Color(0xFFEF4444)
                                    )
                                }
                            }
                        }

                        // Trennlinie zwischen Modulen
                        if (module != semester.modules.last()) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 4.dp),
                                thickness = 0.5.dp,
                                color = Color(0xFFE5E7EB)
                            )
                        }
                    }
                }
            }
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
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF000000)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${semester.modules.size} Kurse",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
            }
        }
    }
}
