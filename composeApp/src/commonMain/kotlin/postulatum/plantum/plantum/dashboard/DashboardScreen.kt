package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Header
        StarterHeader(
            userName = userName,
            logo = logo
        )
        
        Spacer(Modifier.height(32.dp))
        
        // Welcome Section
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
        
        // Slot Overview
                if (uiState.isLoading) {
            CircularProgressIndicator(
                color = Color(0xFF10B981)
            )
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
                        // Semester Overview inside a slot
                        for (semester in slot.semester) {
                            SemesterCard(semester = semester)
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
        
        // Add slot button
        OutlinedButton(
            onClick = { viewModel.showAddDialog() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF10B981)
            )
        ) {
            Text("Neuen Slot hinzufügen")
        }
        
        Spacer(Modifier.height(12.dp))
        
        // Logout Button
        OutlinedButton(
            onClick = onLogout,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFEF4444)
            )
        ) {
            Text("Abmelden")
        }
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
}


