package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import postulatum.plantum.plantum.localizedStringResource
import plantum.composeapp.generated.resources.*
import postulatum.plantum.plantum.model.Slot
import postulatum.plantum.plantum.ui.CustomIcons

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
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF000000)
                    )
                    // Show description if available
                    slot.description?.let { desc ->
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = desc,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF374151)
                        )
                    }
                }
                
                if (onEdit != null) {
                    IconButton(
                        onClick = { onEdit(slot) },
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = CustomIcons.Edit,
                            contentDescription = localizedStringResource(Res.string.slot_card_edit),
                            modifier = Modifier.size(28.dp),
                            tint = Color(0xFF374151)
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

@Composable
fun AddSlotCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .heightIn(min = 100.dp)
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
                color = Color(0xFF10B981), // Green color like the button
                style = Stroke(
                    width = strokeWidth,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(dashWidth, dashGap),
                        phase = 0f
                    )
                )
            )
        }

        // Content - centered text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "+",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF10B981)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = localizedStringResource(Res.string.slot_card_add_new),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF10B981)
                )
            }
        }
    }
}
