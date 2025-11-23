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
    onLanguageChange: (String) -> Unit = {},
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

    // Layout-Fix: Column sorgt dafür, dass der Header oben fix bleibt
    Column(modifier = Modifier.fillMaxSize()) {
        StarterHeader(
            userName = userName,
            logo = logo,
            onLogout = onLogout,
            onLanguageChange = onLanguageChange, // <--- SPRACH-CALLBACK WEITERGEGEBEN
            // Optional: Implementierung für das Impressum
            onImprintClick = {
                // Hier könnte eine Navigation oder ein Dialog für das Impressum ausgelöst werden
                println("Impressum clicked!")
            }
        )

        BoxWithConstraints(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
        val sidePadding = maxWidth * 0.10f

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = sidePadding)
                .padding(bottom = 40.dp), // Space for disclaimer
            verticalAlignment = Alignment.Top
        ) {
        // Linke Spalte: Hauptinhalt
        val leftScroll = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(0.65f)
                .fillMaxHeight()
                .verticalScroll(leftScroll)
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(32.dp))

            Spacer(Modifier.height(16.dp))
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

            AddSlotCard(
                onClick = { viewModel.showAddDialog() }
            )

            Spacer(Modifier.height(12.dp))
        }

        val rightScroll = rememberScrollState()

        // Map the activated semester ids back to Semester objects for credit calculation
        val activatedSemesterObjects = activatedSemesters.mapNotNull { (slotId, semesterId) ->
            uiState.slots.find { it.id == slotId }?.semester?.firstOrNull { it.id == semesterId }
        }

        val creditsByCategory = creditCalculationService?.calculateCreditCategories(activatedSemesterObjects)
        val sumCredits : UInt = creditCalculationService?.getSumOfCredits(activatedSemesterObjects) ?: 0u
        val modulesByCategory = creditCalculationService?.getModulesByCategory(activatedSemesterObjects) ?: emptyMap()
        val mainSpecialisation = creditCalculationService?.getMainSpecialisation(activatedSemesterObjects)
        val minorSpecialisations = creditCalculationService?.getMinorSpecialisations(activatedSemesterObjects) ?: emptyList()

        Column(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxHeight()
                .verticalScroll(rightScroll)
        ) {
            Spacer(Modifier.height(96.dp))

            CreditSummaryView(
                sumCredits = sumCredits,
                creditsByCategory = creditsByCategory ?: emptyMap(),
                modulesByCategory = modulesByCategory,
                mainSpecialisation = mainSpecialisation,
                minorSpecialisations = minorSpecialisations,
                activatedSemesters = activatedSemesterObjects,
                creditCalculationService = creditCalculationService,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // Disclaimer at the bottom - fixed position, unobtrusive
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(bottom = 8.dp, start = sidePadding, end = sidePadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Disclaimer: Diese Anwendung bietet keine Garantie auf Korrektheit. Bitte verwenden Sie immer die offiziellen Angaben und Dokumente der TUM sowie die Studienberatung für verbindliche Informationen.",
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF9CA3AF), // Light gray
            lineHeight = 14.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
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
}

