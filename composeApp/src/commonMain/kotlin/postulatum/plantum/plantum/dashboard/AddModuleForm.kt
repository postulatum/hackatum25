package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.*
import postulatum.plantum.plantum.model.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun AddModuleForm(
    onAddModule: (Module) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var moduleName by remember { mutableStateOf("") }
    var isTheoretical by remember { mutableStateOf(false) }
    var selectedArea by remember { mutableStateOf(Area.MISC) }
    var credits by remember { mutableStateOf("0") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory: Category

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        // Header with blue left border
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCancel() }
                .border(
                    width = 4.dp,
                    color = Color(0xFF3B82F6),
                    shape = MaterialTheme.shapes.small
                )
                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "✏️",
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "Modul bearbeiten",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF3B82F6)
            )
        }

            Spacer(modifier = Modifier.height(24.dp))

            // Modulname field
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(Res.string.form_module_name_label),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF374151),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = moduleName,
                        onValueChange = { moduleName = it },
                        placeholder = { Text("z.B. Advanced Deep Learning", color = Color(0xFF9CA3AF)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF3B82F6),
                            unfocusedBorderColor = Color(0xFFD1D5DB),
                            focusedTextColor = Color(0xFF111827),
                            unfocusedTextColor = Color(0xFF111827)
                        )
                    )
                }

                // ECTS Credits field
                Column(modifier = Modifier.width(150.dp)) {
                    Text(
                        text = "ECTS Credits",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF374151),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = credits,
                        onValueChange = {
                            if (it.isEmpty() || it.toUIntOrNull() != null) {
                                credits = it
                            }
                        },
                        placeholder = { Text("0") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF3B82F6),
                            unfocusedBorderColor = Color(0xFFD1D5DB),
                            focusedTextColor = Color(0xFF111827),
                            unfocusedTextColor = Color(0xFF111827)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Theoretisches Modul checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Checkbox(
                    checked = isTheoretical,
                    onCheckedChange = { isTheoretical = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF3B82F6),
                        uncheckedColor = Color(0xFF9CA3AF)
                    )
                )
                Text(
                    text = "Theoretisches Modul",
                    fontSize = 14.sp,
                    color = Color(0xFF374151),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fachgebiet dropdown
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Fachgebiet",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF374151),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = stringResource(selectedArea.getDisplayName()),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF3B82F6),
                            unfocusedBorderColor = Color(0xFFD1D5DB),
                            focusedTextColor = Color(0xFF111827),
                            unfocusedTextColor = Color(0xFF111827)
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Area.entries.forEach { area ->
                            DropdownMenuItem(
                                text = { Text(stringResource(area.getDisplayName())) },
                                onClick = {
                                    selectedArea = area
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        onCancel()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFF6B7280)
                    )
                ) {
                    Text(stringResource(Res.string.button_cancel), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = {
                        if (moduleName.isNotBlank() && credits.toUIntOrNull() != null) {
                            // This is because not all areas are also categories (electives have the category ELECTIVE)
                            selectedCategory = try {
                                Category.valueOf(selectedArea.name)
                            } catch (_: IllegalArgumentException) {
                                Category.ELECTIVE
                            }
                            val module = Module(
                                id = Uuid.random().toString(),
                                tumId = "TBD-${Uuid.random().toString().take(8)}",
                                name = moduleName,
                                area = selectedArea,
                                workload = Workload(main = 2u, primary = 2u),
                                credits = credits.toUInt(),
                                examType = ExaminationType.EXAM,
                                language = Language.EN,
                                isTheoretical = isTheoretical,
                                category = selectedCategory
                            )
                            onAddModule(module)

                            // Reset form
                            moduleName = ""
                            isTheoretical = false
                            selectedArea = Area.MISC
                            selectedCategory = Category.ELECTIVE
                            credits = "0"
                        }
                    },
                    enabled = moduleName.isNotBlank() && credits.toUIntOrNull() != null && credits != "0",
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3B82F6),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFD1D5DB),
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(stringResource(Res.string.button_save), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
    }
}