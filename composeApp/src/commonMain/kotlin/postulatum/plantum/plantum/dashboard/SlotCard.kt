package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import postulatum.plantum.plantum.model.Slot

@Composable
fun SlotCard(
    slot: Slot,
    modifier: Modifier = Modifier,
    onEdit: ((Slot) -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3F4F6)
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Header row with title and edit button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = slot.displayName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF111827)
                    )
                    // Show description if available
                    slot.description?.let { desc ->
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = desc,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF6B7280)
                        )
                    }
                }
                
                if (onEdit != null) {
                    IconButton(
                        onClick = { onEdit(slot) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            text = "✏️",
                            fontSize = 16.sp
                        )
                    }
                }
            }
            
            Spacer(Modifier.height(16.dp))

            // Content slot for inner composables
            content()
        }
    }
}