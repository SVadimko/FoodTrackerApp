package com.vadimko.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vadimko.core_ui.CarbColor
import com.vadimko.core_ui.FatColor
import com.vadimko.core_ui.LocalSpacing
import com.vadimko.core_ui.ProteinColor
import com.vadimko.tracker_presentation.R
import com.vadimko.tracker_presentation.components.UnitDisplay
import com.vadimko.tracker_presentation.tracker_overview.TrackerOverviewState

@Composable
fun NutrientsHeader(
    state: TrackerOverviewState,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val animatedCalorieCount = animateIntAsState(targetValue = state.totalCalories)
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = state.userName,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
            Image(
                imageVector = Icons.Default.Face,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    Color.White
                )
            )
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 50.dp,
                        bottomEnd = 50.dp
                    )
                )
                .background(MaterialTheme.colors.primary)
                .padding(
                    end = spacing.spaceLarge,
                    start = spacing.spaceLarge,
                    bottom = spacing.spaceExtraLarge,
                    top = spacing.spaceSmall
                    //horizontal = spacing.spaceLarge,
                    //vertical = spacing.spaceExtraLarge
                )

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                UnitDisplay(
                    amount = animatedCalorieCount.value,
                    unit = stringResource(id = R.string.kcal),
                    amountColor = MaterialTheme.colors.onPrimary,
                    amountTextSize = 40.sp,
                    unitColor = MaterialTheme.colors.onPrimary,
                    //uniTextSize = ,
                    modifier = Modifier.align(Alignment.Bottom)
                )
                Column {
                    Text(
                        text = stringResource(id = R.string.your_goal),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onPrimary
                    )
                    UnitDisplay(
                        amount = state.caloriesGoal,
                        unit = stringResource(id = R.string.kcal),
                        amountColor = MaterialTheme.colors.onPrimary,
                        amountTextSize = 40.sp,
                        unitColor = MaterialTheme.colors.onPrimary,
                    )
                }

            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            NutrientsBar(
                carbs = state.totalCarbs,
                protein = state.totalProtein,
                fat = state.totalFat,
                calories = state.totalCalories,
                caloriesGoal = state.caloriesGoal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            Spacer(modifier = Modifier.height(spacing.spaceLarge))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NutrientBarInfo(
                    value = state.totalProtein,
                    goal = state.proteinGoal,
                    name = stringResource(id = R.string.protein),
                    color = ProteinColor,
                    modifier = Modifier.size(90.dp)
                )
                NutrientBarInfo(
                    value = state.totalCarbs,
                    goal = state.carbsGoal,
                    name = stringResource(id = R.string.carbs),
                    color = CarbColor,
                    modifier = Modifier.size(90.dp)
                )
                NutrientBarInfo(
                    value = state.totalFat,
                    goal = state.fatGoal,
                    name = stringResource(id = R.string.fat),
                    color = FatColor,
                    modifier = Modifier.size(90.dp)
                )
            }
        }
    }
}