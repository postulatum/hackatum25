package postulatum.plantum.plantum.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import plantum.composeapp.generated.resources.Res
import plantum.composeapp.generated.resources.*
import postulatum.plantum.plantum.CategoryCreditGoals
import postulatum.plantum.plantum.TotalCreditGoal
import postulatum.plantum.plantum.model.Category
import postulatum.plantum.plantum.model.Module
import postulatum.plantum.plantum.model.getDisplayName

@Composable
fun CreditSummaryView(
    sumCredits: UInt,
    creditsByCategory: Map<Category, UInt>,
    modulesByCategory: Map<Category, List<Module>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // State to track which categories are expanded
        var expandedCategories by remember { mutableStateOf(setOf<Category>()) }

        // Title
        Text(
            text = stringResource(Res.string.credit_summary_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Overall Progress Card (prominent like in the image)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF3B82F6) // Blue background
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.credit_summary_overall_progress),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "$sumCredits / $TotalCreditGoal ${stringResource(Res.string.credit_summary_ects)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Overall progress bar
                val overallProgress = (sumCredits.toFloat() / TotalCreditGoal.toFloat()).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.3f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(overallProgress)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Info Box (like "Regel für Informatik-Module" in the image)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEFF6FF) // Light blue background
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                // Info icon
                Text(
                    text = "ℹ️",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Column {
                    Text(
                        text = stringResource(Res.string.credit_summary_hint_title),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E40AF)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(Res.string.credit_summary_hint_text),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF1E3A8A),
                        lineHeight = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Category breakdown title
        Text(
            text = stringResource(Res.string.credit_summary_category_details),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category progress items
        CategoryCreditGoals.forEach { (category, goal) ->
            val achieved: UInt = creditsByCategory[category] ?: 0u
            val progress = if (goal == 0u) {
                0f
            } else {
                (achieved.toFloat() / goal.toFloat()).coerceIn(0f, 1f)
            }
            val modules = modulesByCategory[category] ?: emptyList()
            val isExpanded = expandedCategories.contains(category)

            CategoryProgressItem(
                categoryName = stringResource(category.getDisplayName()),
                achieved = achieved,
                goal = goal,
                progress = progress,
                modules = modules,
                isExpanded = isExpanded,
                onToggleExpand = {
                    expandedCategories = if (isExpanded) {
                        expandedCategories - category
                    } else {
                        expandedCategories + category
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun CategoryProgressItem(
    categoryName: String,
    achieved: UInt,
    goal: UInt,
    progress: Float,
    modules: List<Module>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    // Determine color based on completion
    val isComplete = achieved >= goal
    val progressColor = if (isComplete) Color(0xFF10B981) else Color(0xFF3B82F6)
    val backgroundColor = if (isComplete) Color(0xFFECFDF5) else Color(0xFFF3F4F6)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Main category row (clickable)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category info
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = categoryName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF111827)
                        )

                        // Expand/Collapse indicator
                        Text(
                            text = if (isExpanded) "▼" else "▶",
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.White.copy(alpha = 0.5f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(progress)
                                .clip(RoundedCornerShape(4.dp))
                                .background(progressColor)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Credits display
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$achieved / $goal",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = progressColor
                    )
                    Text(
                        text = stringResource(Res.string.credit_summary_ects),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF6B7280)
                    )
                }
            }

            // Expandable module details
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp)
                ) {
                    // Divider
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 12.dp),
                        thickness = 1.dp,
                        color = Color(0xFFE5E7EB)
                    )

                    if (modules.isEmpty()) {
                        Text(
                            text = "Keine Module in dieser Kategorie",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF6B7280),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        modules.forEach { module ->
                            ModuleDetailRow(module = module)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModuleDetailRow(module: Module) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White.copy(alpha = 0.6f))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = module.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF111827)
            )
            // Show area if it's ELECTIVE
            if (module.category == postulatum.plantum.plantum.model.Category.ELECTIVE) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = module.area.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF6B7280)
                )
            }
        }
    }

        Text(
            text = "${module.credits} ECTS",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF3B82F6)
        )
    }
}