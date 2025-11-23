package postulatum.plantum.plantum.dashboard

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



@Composable
fun DashboardScreen(
    userName: String?,
    logo: Painter,
    onLogout: () -> Unit,    
    viewModel: DashboardViewModel = viewModel { DashboardViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    var extendedSemesterIds by remember { mutableStateOf<Set<String>>(setOf()) }
    // Map, where first String is slot.id and second is semester.id
    var activatedSemesters by remember { mutableStateOf<Map<String, String>>(mapOf()) }

    var creditCalculationService by remember { mutableStateOf<CreditCalculationService?>(CreditCalculationService()) }

    // Initialize activatedSemesters to the first semester id of each slot when slots are loaded
    LaunchedEffect(uiState.slots) {
        if (uiState.slots.isNotEmpty() && activatedSemesters.isEmpty()) {
            activatedSemesters = uiState.slots.mapNotNull { slot ->
                slot.semester.firstOrNull()?.let { slot.id to it.id }
            }.toMap()
        }
    }

    StarterHeader(
        userName = userName,
        logo = logo
    )

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val sidePadding = maxWidth * 0.10f

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePadding),
            verticalAlignment = Alignment.Top
        ) {
        // Linke Spalte: Hauptinhalt
        val leftScroll = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(0.65f)            // Breiter gemacht für mehr Platz
                .fillMaxHeight()
                .verticalScroll(leftScroll)
                .padding(end = 16.dp), // Abstand zur rechten Spalte
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(32.dp))

            Text(
                text = "Willkommen, $userName!",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF000000)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "TUM Masterplaner Dashboard",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF374151)
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
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            fun onClickSemester(semester: Semester) {
                                if (extendedSemesterIds.contains(semester.id)) {
                                    extendedSemesterIds -= semester.id
                                } else {
                                    extendedSemesterIds += semester.id
                                }
                            }
                            for (semester in slot.semester) {
                                // Each semester gets a hoverable box showing a checkbox at top-right
                                val interactionSource = remember { MutableInteractionSource() }
                                val isHovered by interactionSource.collectIsHoveredAsState()
                                val isActivated = activatedSemesters[slot.id] == semester.id
                                val isExtended = extendedSemesterIds.contains(semester.id)
                                Box(
                                    modifier = Modifier
                                        .then(if (isExtended) Modifier.fillMaxWidth() else Modifier)
                                        .padding(4.dp)
                                        .hoverable(interactionSource = interactionSource)
                                ) {
                                    SemesterCard(
                                        semester = semester,
                                        isExtended = isExtended,
                                        onClick = { onClickSemester(semester) },
                                        isActivated = isActivated,
                                        onAddModule = { sem, module ->
                                            viewModel.addModuleToSemester(slot.id, sem.id, module)
                                        }
                                    )

                                    if (isHovered) {
                                        // Checkbox overlay: checked if this semester id is the activated one for the slot
                                        val checked = activatedSemesters[slot.id] == semester.id
                                        Checkbox(
                                            checked = checked,
                                            onCheckedChange = { isChecked ->
                                                activatedSemesters = activatedSemesters.toMutableMap().apply {
                                                    if (isChecked) {
                                                        put(slot.id, semester.id)
                                                    } else {
                                                        remove(slot.id)
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

                            // Add or Edit Semester Card
                            if (uiState.slotIdAddingSemester == slot.id) {
                                // Show editable card with suggested name
                                EditableSemesterCard(
                                    onSave = { semesterName ->
                                        viewModel.saveSemester(slot.id, semesterName)
                                    },
                                    onCancel = { viewModel.cancelAddingSemester() },
                                    isExtended = false,
                                    initialName = uiState.suggestedSemesterName ?: ""
                                )
                            } else {
                                // Show dashed "add" card
                                AddSemesterCard(
                                    onClick = { viewModel.startAddingSemesterFor(slot.id) },
                                    isExtended = false
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                }
            }

            OutlinedButton(
                onClick = { viewModel.showAddDialog() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF10B981))
            ) {
                Text(
                    "Neuen Slot hinzufügen",
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onLogout,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444))
            ) {
                Text(
                    "Abmelden",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Rechte Spalte: Credit Summary (breiter gemacht und nach unten verschoben)
        val rightScroll = rememberScrollState()

        // Map the activated semester ids back to Semester objects for credit calculation
        val activatedSemesterObjects = activatedSemesters.mapNotNull { (slotId, semesterId) ->
            uiState.slots.find { it.id == slotId }?.semester?.firstOrNull { it.id == semesterId }
        }

        // Beispielwerte – hier kannst du später echte Werte berechnen
        val creditsByCategory = creditCalculationService?.calculateCreditCategories(activatedSemesterObjects)
        val sumCredits = creditsByCategory?.values?.sum() ?: 0u

        Column(
            modifier = Modifier
                .weight(0.35f)     // Schmaler gemacht (von 0.5f auf 0.35f)
                .fillMaxHeight()
                .verticalScroll(rightScroll)
        ) {
            Spacer(Modifier.height(150.dp))  // Weiter nach unten verschoben

            CreditSummaryView(
                sumCredits = sumCredits,
                creditsByCategory = creditsByCategory ?: emptyMap(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    }  // End of BoxWithConstraints
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
}


