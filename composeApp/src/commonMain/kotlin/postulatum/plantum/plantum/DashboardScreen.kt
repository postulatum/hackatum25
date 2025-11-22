package postulatum.plantum.plantum

import androidx.compose.foundation.Image
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

import postulatum.plantum.plantum.model.*
import postulatum.plantum.plantum.data.SlotRepository

@Composable
fun DashboardScreen(
    userName: String?,
    logo: Painter,
    onLogout: () -> Unit
) {
    // Initialize repository with state management
    val repository = remember { SlotRepository() }
    val slots by remember { derivedStateOf { repository.slots } }
    var showAddSlotDialog by remember { mutableStateOf(false) }
    var slotToEdit by remember { mutableStateOf<Slot?>(null) }

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
        for (slot in slots) {
            SlotCard(
                slot = slot,
                onEdit = { slotToEdit = it }
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
        // Add slot button
        OutlinedButton(
            onClick = { showAddSlotDialog = true },
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
    if (showAddSlotDialog) {
        AddSlotDialog(
            onDismiss = { showAddSlotDialog = false },
            onConfirm = { newSlot ->
                repository.addSlot(newSlot)
                showAddSlotDialog = false
            }
        )
    }
    
    // Show Edit Slot Dialog (Edit Mode)
    slotToEdit?.let { slot ->
        AddSlotDialog(
            existingSlot = slot,
            onDismiss = { slotToEdit = null },
            onConfirm = { updatedSlot ->
                repository.updateSlot(updatedSlot)
                slotToEdit = null
            }
        )
    }
}


