package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import postulatum.plantum.plantum.localizedStringResource
import plantum.composeapp.generated.resources.*
import postulatum.plantum.plantum.model.Module
import postulatum.plantum.plantum.model.Semester
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi

/**
 * Dialog zum Hinzufügen eines Semesters.
 * Ähnlich aufgebaut wie AddSlotDialog: Name eingeben und Module auswählen.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class, ExperimentalLayoutApi::class)
@Composable
fun SemesterDialog(
    availableModules: List<Module>,
    onDismiss: () -> Unit,
    onConfirm: (Semester) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(setOf<String>()) } // Module IDs
    var query by remember { mutableStateOf("") }
    var suggestionsExpanded by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current

    val errorNameRequired = localizedStringResource(Res.string.dialog_error_name_required)

    val filteredModules = remember(query, availableModules) {
        if (query.isBlank()) availableModules
        else {
            val q = query.trim().lowercase()
            availableModules.filter { module ->
                module.name.lowercase().contains(q) ||
                    module.tumId.lowercase().contains(q)
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = Color(0xFF1F2937),
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp).verticalScroll(rememberScrollState())
            ) {
                Text(localizedStringResource(Res.string.dialog_add_semester_title), color = Color.White, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        error = null
                    },
                    label = { Text(localizedStringResource(Res.string.dialog_semester_name_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF60A5FA),
                        unfocusedBorderColor = Color(0xFF4B5563),
                        focusedLabelColor = Color(0xFF93C5FD),
                        unfocusedLabelColor = Color(0xFF9CA3AF),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color(0xFF93C5FD)
                    )
                )

                Spacer(Modifier.height(16.dp))

                Text(localizedStringResource(Res.string.dialog_select_modules), color = Color.White)
                Spacer(Modifier.height(8.dp))

                Box {
                    OutlinedTextField(
                        value = query,
                        onValueChange = {
                            query = it
                            suggestionsExpanded = true
                        },
                        label = { Text(localizedStringResource(Res.string.dialog_search_module)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                suggestionsExpanded = focusState.isFocused && filteredModules.isNotEmpty()
                            },
                        trailingIcon = {
                            // Replace material icon usage with a simple text-only IconButton so we don't need
                            // androidx.compose.material.icons. This gives the same UX: clear when there's text,
                            // or toggle suggestions otherwise.
                            IconButton(onClick = {
                                if (query.isNotBlank()) query = "" else suggestionsExpanded = !suggestionsExpanded
                            }) {
                                Text(
                                    text = if (query.isNotBlank()) "✕" else if (suggestionsExpanded) "▲" else "▼",
                                    color = Color(0xFF93C5FD)
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF60A5FA),
                            unfocusedBorderColor = Color(0xFF4B5563),
                            focusedLabelColor = Color(0xFF93C5FD),
                            unfocusedLabelColor = Color(0xFF9CA3AF),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF93C5FD)
                        ),
                        singleLine = true
                    )

                    DropdownMenu(
                        expanded = suggestionsExpanded && filteredModules.isNotEmpty(),
                        onDismissRequest = {
                            suggestionsExpanded = false
                            focusManager.clearFocus()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1F2937))
                    ) {
                        filteredModules.forEach { module ->
                            val alreadySelected = module.id in selected
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "${module.tumId} — ${module.name}",
                                        color = if (alreadySelected) Color(0xFF10B981) else Color.White
                                    )
                                },
                                onClick = {
                                    selected = if (alreadySelected) selected - module.id else selected + module.id
                                    query = ""
                                    suggestionsExpanded = false
                                    focusManager.clearFocus()
                                }
                            )
                        }
                    }
                }

                if (selected.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        selected.mapNotNull { id -> availableModules.firstOrNull { it.id == id } }
                            .forEach { module ->
                                AssistChip(
                                    onClick = {},
                                    label = { Text("${module.tumId} ${module.name}") },
                                    trailingIcon = {
                                        IconButton(onClick = { selected -= module.id }) {
                                            Text("×", color = Color.White)
                                        }
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = Color(0xFF374151),
                                        labelColor = Color.White
                                    )
                                )
                            }
                    }
                }

                if (error != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(error!!, color = Color(0xFFFCA5A5), style = MaterialTheme.typography.bodySmall)
                }

                Spacer(Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF9CA3AF))
                    ) { Text(localizedStringResource(Res.string.button_cancel)) }

                    Button(
                        onClick = {
                            if (name.isBlank()) {
                                error = errorNameRequired
                                return@Button
                            }
                            val selectedModules = availableModules.filter { selected.contains(it.id) }
                            val semester = Semester(
                                id = Uuid.random().toString(),
                                name = name.trim(),
                                modules = selectedModules
                            )
                            onConfirm(semester)
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) { Text(localizedStringResource(Res.string.button_add)) }
                }
            }
        }
    }
}
