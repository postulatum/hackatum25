package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import postulatum.plantum.plantum.StarterHeader

import postulatum.plantum.plantum.model.*
import postulatum.plantum.plantum.data.DummyData



@Composable
fun DashboardScreen(
    userName: String?,
    logo: Painter,
    onLogout: () -> Unit,    
    viewModel: DashboardViewModel = viewModel { DashboardViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    var extendedSemesters by remember { mutableStateOf<Set<Semester>>(setOf()) }
    var activatedSemesters by remember { mutableStateOf<Map<Slot, Semester>>(mapOf()) }

    var creditCalculationService by remember { mutableStateOf<CreditCalculationService?>(CreditCalculationService()) }

    // Initialize activatedSemesters to the first semester of each slot when slots are loaded
    LaunchedEffect(uiState.slots) {
        if (uiState.slots.isNotEmpty() && activatedSemesters.isEmpty()) {
            activatedSemesters = uiState.slots.mapNotNull { slot ->
                slot.semester.firstOrNull()?.let { slot to it }
            }.toMap()
        }
    }

    StarterHeader(
        userName = userName,
        logo = logo
    )
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Linke Spalte: Hauptinhalt
        val leftScroll = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)            // statt fillMaxSize()
                .fillMaxHeight()
                .verticalScroll(leftScroll)
                .padding(end = 16.dp), // Abstand zur rechten Spalte
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(32.dp))

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

            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color(0xFF10B981))
            } else {
                for (slot in uiState.slots) {
                    SlotCard(
                        slot = slot,
                        onEdit = { viewModel.showEditDialog(it) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            fun onClickSemester(semester: Semester) {
                                if (extendedSemesters.contains(semester)) {
                                    extendedSemesters -= semester
                                } else {
                                    extendedSemesters += semester
                                }
                            }
                            for (semester in slot.semester) {
                                // Each semester gets a hoverable box showing a checkbox at top-right
                                val interactionSource = remember { MutableInteractionSource() }
                                val isHovered by interactionSource.collectIsHoveredAsState()
                                val isActivated = activatedSemesters[slot] == semester
                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .hoverable(interactionSource = interactionSource)
                                ) {
                                    SemesterCard(
                                        semester = semester,
                                        isExtended = extendedSemesters.contains(semester),
                                        onClick = { onClickSemester(semester) },
                                        isActivated = isActivated
                                    )

                                    if (isHovered) {
                                        // Checkbox overlay: checked if this semester is the activated one for the slot
                                        val checked = activatedSemesters[slot] == semester
                                        Checkbox(
                                            checked = checked,
                                            onCheckedChange = { isChecked ->
                                                activatedSemesters = activatedSemesters.toMutableMap().apply {
                                                    if (isChecked) {
                                                        put(slot, semester)
                                                    } else {
                                                        remove(slot)
                                                    }
                                                }
                                            },
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(6.dp)
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = { viewModel.showAddSemesterDialogFor(slot.id) },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0EA5E9))
                        ) { Text("Semester hinzufügen") }
                    }
                    Spacer(Modifier.height(24.dp))
                }
            }

            OutlinedButton(
                onClick = { viewModel.showAddDialog() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF10B981))
            ) { Text("Neuen Slot hinzufügen") }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onLogout,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444))
            ) { Text("Abmelden") }
        }

        // Rechte Spalte: Credit Summary
        val rightScroll = rememberScrollState()

        // Beispielwerte – hier kannst du später echte Werte berechnen
        val creditsByCategory = creditCalculationService?.calculateCreditCategories(activatedSemesters.values.toList())
        val sumCredits = creditsByCategory?.values?.sum() ?: 0u

        CreditSummaryView(
            sumCredits = sumCredits,
            creditsByCategory = creditsByCategory ?: emptyMap(),
            modifier = Modifier
                .width(320.dp)     // feste Breite, damit sie sichtbar Platz bekommt
                .fillMaxHeight()
                .verticalScroll(rightScroll)
        )
    }


    // Show Add Slot Dialog (Create Mode)
    if (uiState.showAddDialog) {
        AddSlotDialog(
            onDismiss = { viewModel.hideAddDialog() },
            onConfirm = { newSlot ->
                viewModel.addSlot(newSlot)
            }
        )
    }

    // Show Edit Slot Dialog (Edit Mode)
    uiState.slotToEdit?.let { slot ->
        AddSlotDialog(
            existingSlot = slot,
            onDismiss = { viewModel.hideAddDialog() },
            onConfirm = { updatedSlot ->
                viewModel.updateSlot(updatedSlot)
            }
        )
    }

    // Show Add Semester Dialog
    if (uiState.showAddSemesterDialog) {
        val allModules = remember {
            // Aggregiere mögliche Module aus DummyData (Platzhalter für echte Mock-Daten)
            DummyData.dummySlots
                .flatMap { it.semester }
                .flatMap { it.modules }
                .distinctBy { it.id }
        }
        SemesterDialog(
            availableModules = allModules,
            onDismiss = { viewModel.hideAddSemesterDialog() },
            onConfirm = { semester ->
                viewModel.addSemesterToSelected(semester)
            }
        )
    }
}


