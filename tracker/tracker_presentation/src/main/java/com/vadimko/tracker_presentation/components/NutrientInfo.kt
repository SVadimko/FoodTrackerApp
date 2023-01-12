package com.vadimko.tracker_presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.vadimko.core_ui.LocalSpacing

@Composable
fun NutrientInfo(
    name: String,
    amount: Int,
    unit: String,
    modifier: Modifier = Modifier,
    amountTextSize: TextUnit = 20.sp,
    amountColor: Color = MaterialTheme.colors.onBackground,
    uniTextSize: TextUnit = 14.sp,
    unitColor: Color = MaterialTheme.colors.onBackground,
    nameTextStyle: TextStyle = MaterialTheme.typography.body1
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UnitDisplay(
            amount = amount,
            unit = unit,
            amountTextSize = amountTextSize,
            amountColor = amountColor,
            unitColor = unitColor,
            uniTextSize = uniTextSize
        )
        Text(
            text = name,
            color = MaterialTheme.colors.onBackground,
            style = nameTextStyle,
            fontWeight = FontWeight.Light
        )
    }
    /* Row(
         modifier = modifier
     ) {
         Text(
             text = amount.toString(),
             style = MaterialTheme.typography.h1,
             fontSize = amountTextSize,
             color = amountColor,
             modifier = Modifier.alignBy(LastBaseline)
         )
         Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
         Text(
             text = unit,
             style = MaterialTheme.typography.body1,
             fontSize = uniTextSize,
             color = unitColor,
             modifier = Modifier.alignBy(LastBaseline)
         )
     }*/

}