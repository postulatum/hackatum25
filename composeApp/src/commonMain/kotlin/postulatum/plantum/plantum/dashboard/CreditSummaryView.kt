package postulatum.plantum.plantum.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import postulatum.plantum.plantum.CategoryCreditGoals
import postulatum.plantum.plantum.TotalCreditGoal
import postulatum.plantum.plantum.model.Category
import postulatum.plantum.plantum.model.getDisplayName
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun CreditSummaryView (
    sumCredits: UInt,
    creditsByCategory: Map<Category, UInt>,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Total credits: $sumCredits / $TotalCreditGoal",
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = androidx.compose.ui.graphics.Color(0xFF000000)
        )

        Spacer(modifier = Modifier.height(12.dp))

        CategoryCreditGoals.forEach { (category, goal) ->
            val achieved : UInt= creditsByCategory[category] ?: 0u
            val progress = if (goal == 0u) {
                1f
            } else {
                (achieved.toFloat() / goal.toFloat()).coerceIn(0f, 1f)
            }

            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(category.getDisplayName()),
                            modifier = Modifier.weight(1f),
                            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                            color = androidx.compose.ui.graphics.Color(0xFF000000)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "$achieved / $goal",
                            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = androidx.compose.ui.graphics.Color(0xFF1F2937)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }
            }
        }
    }


}