package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.key.*
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import postulatum.plantum.plantum.localizedStringResource
import plantum.composeapp.generated.resources.*

import postulatum.plantum.plantum.model.Semester
import postulatum.plantum.plantum.model.Module
import postulatum.plantum.plantum.model.getDisplayName
import postulatum.plantum.plantum.ui.CustomIcons

@Composable
fun SemesterCard(
    semester: Semester,
    isExtended: Boolean,
    onClick: (Semester) -> Unit,
    modifier: Modifier = Modifier,
    isActivated: Boolean = false,
    onAddModule: ((Semester, Module) -> Unit)? = null
) {
    if (isExtended) {
        extendSemesterCard(semester, onClick, modifier, isActivated, onAddModule)
    } else {
        unextendedSemesterCard(semester, onClick, modifier, isActivated)
    }
}

@Composable
fun extendSemesterCard(
    semester: Semester,
    onClick: (Semester) -> Unit,
    modifier: Modifier = Modifier,
    isActivated: Boolean = false,
    onAddModule: ((Semester, Module) -> Unit)? = null
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
                    text = "${semester.modules.size} Module",
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
                            text = localizedStringResource(Res.string.module_table_name),
                            fontSize = 12.sp,
                            color = Color(0xFF9CA3AF),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(0.4f).padding(end = 16.dp)
                        )

                        Text(
                            text = localizedStringResource(Res.string.module_table_area_category),
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
                                text = localizedStringResource(Res.string.module_table_ects),
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
                                        text = localizedStringResource(module.area?.getDisplayName() ?: Res.string.area_misc), // This should not happen?
                                        fontSize = 14.sp,
                                        color = Color(0xFF1F2937),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = localizedStringResource(module.category.getDisplayName()),
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
                                    onClick = { },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = CustomIcons.Edit,
                                        contentDescription = "Edit",
                                        modifier = Modifier.size(18.dp),
                                        tint = Color(0xFF3B82F6)
                                    )
                                }

                                // Mülltonne (rot)
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = CustomIcons.Delete,
                                        contentDescription = "Delete",
                                        modifier = Modifier.size(18.dp),
                                        tint = Color(0xFFEF4444)
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

                // Add Module Button and Form
                if (onAddModule != null) {
                    var showAddForm by remember { mutableStateOf(false) }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (showAddForm) {
                        AddModuleForm(
                            onAddModule = { module ->
                                onAddModule(semester, module)
                                showAddForm = false
                            },
                            onCancel = { showAddForm = false },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        // + Button to show form
                        OutlinedButton(
                            onClick = { showAddForm = true },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF3B82F6)
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B82F6))
                        ) {
                            Text(
                                text = localizedStringResource(Res.string.module_add),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
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
                    text = "${semester.modules.size} Module",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF374151)
                )
            }
        }
    }
}

@Composable
fun AddSemesterCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isExtended: Boolean = false
) {
    Box(
        modifier = modifier
            .then(if (isExtended) Modifier.fillMaxWidth() else Modifier.width(280.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                role = Role.Button,
                onClick = onClick
            )
    ) {
        // Background with slight transparency
        Surface(
            modifier = Modifier.matchParentSize(),
            color = Color.White.copy(alpha = 0.4f),
            shape = MaterialTheme.shapes.medium
        ) {}

        // Canvas for dashed border
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val strokeWidth = 2.dp.toPx()
            val dashWidth = 10.dp.toPx()
            val dashGap = 8.dp.toPx()
            val cornerRadius = 12.dp.toPx()

            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        left = strokeWidth / 2,
                        top = strokeWidth / 2,
                        right = size.width - strokeWidth / 2,
                        bottom = size.height - strokeWidth / 2,
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                )
            }

            drawPath(
                path = path,
                color = Color(0xFF0EA5E9),
                style = Stroke(
                    width = strokeWidth,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(dashWidth, dashGap),
                        phase = 0f
                    )
                )
            )
        }

        // Content - matching the structure of unextended semester card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "+",
                    fontSize = if (isExtended) 22.sp else 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0EA5E9)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = localizedStringResource(Res.string.semester_add),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0EA5E9)
                )
            }
        }
    }
}

@Composable
fun EditableSemesterCard(
    onSave: (String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    isExtended: Boolean = false,
    initialName: String = ""
) {
    var semesterName by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Card(
        modifier = modifier
            .then(if (isExtended) Modifier.fillMaxWidth() else Modifier.width(280.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF0EA5E9))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = semesterName,
                    onValueChange = { newValue ->
                        semesterName = newValue
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onKeyEvent { keyEvent ->
                            when (keyEvent.type) {
                                KeyEventType.KeyDown if keyEvent.key == Key.Enter -> {
                                    // If field is empty, use the suggested name (placeholder)
                                    val nameToSave = semesterName.ifBlank { initialName }
                                    if (nameToSave.isNotBlank()) {
                                        onSave(nameToSave)
                                    }
                                    true
                                }
                                KeyEventType.KeyDown if keyEvent.key == Key.Escape -> {
                                    onCancel()
                                    true
                                }
                                else -> false
                            }
                        },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF000000)
                    ),
                    cursorBrush = SolidColor(Color(0xFF0EA5E9)),
                    decorationBox = { innerTextField ->
                        Box {
                            if (semesterName.isEmpty()) {
                                Text(
                                    text = initialName.ifEmpty { localizedStringResource(Res.string.semester_name_placeholder) },
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF9CA3AF)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val nameToSave = semesterName.ifBlank { initialName }
                            if (nameToSave.isNotBlank()) {
                                onSave(nameToSave)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0EA5E9)
                        )
                    ) {
                        Text(localizedStringResource(Res.string.button_save), fontSize = 13.sp)
                    }
                    OutlinedButton(
                        onClick = onCancel,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF6B7280)
                        )
                    ) {
                        Text(localizedStringResource(Res.string.button_cancel), fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
