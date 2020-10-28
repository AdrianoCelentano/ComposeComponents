package com.adriano.compose.composables

import androidx.compose.animation.animate
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Layout
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview

@Composable
fun ColorCheckChip(
    modifier: Modifier = Modifier,
    color: Color,
    text: String
) {
    val (checked, setChecked) = remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                setChecked(!checked)
            },
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        ChipLayout(checked) {
            ChipContent(isChecked = checked, color, text)
        }
    }
}

@Composable
private fun ChipLayout(checked: Boolean, slot: @Composable () -> Unit) {
    val textMargin = if (checked) 8.dp else 32.dp
    val textMarginAnimated = animate(textMargin, TweenSpec(delay = 50))
    Layout(
        children = slot,
        measureBlock = { measurables, constraints ->
            val textPlaceable = measurables[1].measure(constraints)
            val width = textPlaceable.width + 40.dp.toIntPx()
            val height = textPlaceable.height + 8.dp.toIntPx()
            val canvasConstraints = Constraints(
                maxHeight = height,
                minHeight = height,
                maxWidth = width,
                minWidth = width
            )
            val canvasPlaceable = measurables[0].measure(canvasConstraints)
            val iconPlaceable = measurables[2].measure(constraints)
            layout(width, height) {
                canvasPlaceable.place(x = 0, y = 0)
                textPlaceable.place(
                    x = textMarginAnimated.toIntPx(),
                    y = 4.dp.toIntPx()
                )
                iconPlaceable.place(
                    x = width - 8.dp.toIntPx() - iconPlaceable.width,
                    y = height / 2 - iconPlaceable.height / 2
                )
            }
        }
    )
}

@Composable
fun ChipContent(isChecked: Boolean, color: Color, text: String) {

    val radiusExpandedFactor = if (isChecked) 1f else 0f
    val radiusExpandedFactorAnimated = animate(radiusExpandedFactor)

    val textColor = if (isChecked) Color.White else Color.Black
    val textColorAnimated = animate(textColor)

    val iconSize = if (isChecked) 16.dp else 0.dp
    val iconSizeAnimated = animate(iconSize, TweenSpec(delay = 100))

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val baseRadius = size.height / 6
            val radius = baseRadius + (radiusExpandedFactorAnimated * (size.width - baseRadius))
            val left = -size.width / 2 + 16.dp.toPx()
            translate(left, 0f) {
                drawCircle(color = color, radius = radius)
            }
        }
    )
    Text(
        modifier = Modifier,
        style = TextStyle(
            color = textColorAnimated
        ),
        text = text
    )
    Image(
        asset = Icons.Filled.Close,
        modifier = Modifier.preferredSize(iconSizeAnimated),
        colorFilter = ColorFilter.tint(Color.White),
        contentScale = ContentScale.Fit
    )
}

@Preview
@Composable
fun ColorCheckChipPreview() {
    ColorCheckChip(color = Color.Cyan, text = "Very long test to test long text")
}