package postulatum.plantum.plantum

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StarterHeader(userName: String?, logo: Painter? = null) {
    // TUM brand blue
    val tumBlue = Color(0xFF0065BD)
    val tumBlueDark = Color(0xFF004B8D)

    // Floating animation
    val infinite = rememberInfiniteTransition()
    val t1 by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val t2 by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 16000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(tumBlue, tumBlueDark)
                )
            )
            .padding(top = 18.dp, bottom = 18.dp)
    ) {
        // Decorative floating circles behind content
        FloatingDecor(t1 = t1, t2 = t2)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                if (logo != null) {
                    Image(
                        painter = logo,
                        contentDescription = "Logo",
                        modifier = Modifier.size(42.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                }
                Column {
                    Text(
                        text = "planTum",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "Study Planner for TUM Students",
                        color = Color.White.copy(alpha = 0.95f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "From Students",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // Simple user icon on the right (no extra icon deps)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.95f), shape = androidx.compose.foundation.shape.CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("👤", color = tumBlueDark)
            }
        }
    }
}

@Composable
fun FloatingDecor(t1: Float, t2: Float) {
    // Semi-transparent circles floating subtly
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
    ) {
        val w = size.width
        val h = size.height

        // Circle 1
        drawCircle(
            color = Color.White.copy(alpha = 0.08f),
            radius = h * 0.6f,
            center = androidx.compose.ui.geometry.Offset(
                x = (w * (0.2f + 0.1f * kotlin.math.sin(2 * kotlin.math.PI * t1).toFloat())),
                y = (h * (0.8f - 0.2f * kotlin.math.cos(2 * kotlin.math.PI * t1).toFloat()))
            )
        )

        // Circle 2
        drawCircle(
            color = Color.White.copy(alpha = 0.06f),
            radius = h * 0.5f,
            center = androidx.compose.ui.geometry.Offset(
                x = (w * (0.7f + 0.1f * kotlin.math.cos(2 * kotlin.math.PI * t2).toFloat())),
                y = (h * (0.3f + 0.2f * kotlin.math.sin(2 * kotlin.math.PI * t2).toFloat()))
            )
        )

        // Accent circle
        drawCircle(
            color = Color(0xFF5FB3FF).copy(alpha = 0.10f),
            radius = h * 0.35f,
            center = androidx.compose.ui.geometry.Offset(
                x = (w * (0.45f + 0.12f * kotlin.math.sin(2 * kotlin.math.PI * (1 - t1)).toFloat())),
                y = (h * (0.5f + 0.15f * kotlin.math.cos(2 * kotlin.math.PI * (1 - t2)).toFloat()))
            )
        )
    }
}
