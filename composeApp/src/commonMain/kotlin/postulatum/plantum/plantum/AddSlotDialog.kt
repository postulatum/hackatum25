package postulatum.plantum.plantum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import postulatum.plantum.plantum.model.*
import java.util.UUID

/**
 * Dialog for adding or editing a slot (academic term).
 * 
 * @param existingSlot If provided, the dialog will be in edit mode with pre-filled values
 * @param onDismiss Callback when dialog is dismissed
 * @param onConfirm Callback when slot is confirmed (returns the new or updated slot)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSlotDialog(
    existingSlot: Slot? = null,
    onDismiss: () -> Unit,
    onConfirm: (Slot) -> Unit
) {
    val isEditMode = existingSlot != null
    
    var description by remember { mutableStateOf(existingSlot?.description ?: "") }
    var selectedTerm by remember { mutableStateOf(existingSlot?.term ?: Term.WiSe) }
    var year by remember { mutableStateOf(existingSlot?.year?.toString() ?: "") }
    var termMenuExpanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = Color(0xFF1F2937),
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Text(
                    text = if (isEditMode) "Slot bearbeiten" else "Neuen Slot hinzufügen",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(Modifier.height(16.dp))
            
                // Term Dropdown
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = when (selectedTerm) {
                            Term.WiSe -> "Wintersemester (WiSe)"
                            Term.SoSe -> "Sommersemester (SoSe)"
                        },
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Semestertyp") },
                        trailingIcon = {
                            Text(
                                if (termMenuExpanded) "▲" else "▼",
                                color = Color(0xFF93C5FD)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF60A5FA),
                            unfocusedBorderColor = Color(0xFF4B5563),
                            focusedLabelColor = Color(0xFF93C5FD),
                            unfocusedLabelColor = Color(0xFF9CA3AF),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    
                    // Invisible clickable box to trigger dropdown
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.Transparent)
                            .then(
                                Modifier.padding(0.dp)
                            )
                    ) {
                        DropdownMenu(
                            expanded = termMenuExpanded,
                            onDismissRequest = { termMenuExpanded = false },
                            modifier = Modifier.background(Color(0xFF374151))
                        ) {
                            DropdownMenuItem(
                                text = { Text("Wintersemester (WiSe)", color = Color.White) },
                                onClick = {
                                    selectedTerm = Term.WiSe
                                    termMenuExpanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Sommersemester (SoSe)", color = Color.White) },
                                onClick = {
                                    selectedTerm = Term.SoSe
                                    termMenuExpanded = false
                                }
                            )
                        }
                    }
                    
                    // Clickable surface
                    Surface(
                        onClick = { termMenuExpanded = !termMenuExpanded },
                        modifier = Modifier.matchParentSize(),
                        color = Color.Transparent
                    ) {}
                }
                
                Spacer(Modifier.height(12.dp))
                
                // Year
                OutlinedTextField(
                    value = year,
                    onValueChange = { 
                        if (it.all { char -> char.isDigit() } && it.length <= 4) {
                            year = it
                            errorMessage = null
                        }
                    },
                    label = { Text("Jahr") },
                    singleLine = true,
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
                
                if (errorMessage != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = errorMessage!!,
                        color = Color(0xFFFCA5A5),
                        fontSize = 12.sp
                    )
                }
                Spacer(Modifier.height(12.dp))
                
                // Description (optional)
                OutlinedTextField(
                    value = description,
                    onValueChange = { 
                        description = it
                        errorMessage = null
                    },
                    label = { Text("Beschreibung (optional)") },
                    singleLine = false,
                    maxLines = 3,
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
                
                Spacer(Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Cancel
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF9CA3AF)
                        )
                    ) {
                        Text("Abbrechen")
                    }
                    
                    // Confirm
                    Button(
                        onClick = {
                            // Validation
                            when {
                                year.isBlank() || year.length != 4 -> {
                                    errorMessage = "Bitte ein gültiges Jahr eingeben (4 Ziffern)"
                                }
                                else -> {
                                    val slot = if (isEditMode) {
                                        // Edit mode: keep existing ID and semesters
                                        Slot(
                                            id = existingSlot!!.id,
                                            term = selectedTerm,
                                            year = year.toUInt(),
                                            semester = existingSlot.semester, // Keep existing semesters
                                            description = description.ifBlank { null }
                                        )
                                    } else {
                                        // Create mode: generate new ID
                                        Slot(
                                            id = UUID.randomUUID().toString(),
                                            term = selectedTerm,
                                            year = year.toUInt(),
                                            semester = listOf<Semester>(), // Empty initially
                                            description = description.ifBlank { null }
                                        )
                                    }
                                    onConfirm(slot)
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isEditMode) Color(0xFF3B82F6) else Color(0xFF10B981)
                        )
                    ) {
                        Text(if (isEditMode) "Speichern" else "Hinzufügen")
                    }
                }
            }
        }
    }
}
