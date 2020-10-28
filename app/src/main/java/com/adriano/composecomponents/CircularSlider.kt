package com.adriano.composecomponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.dragGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.adriano.compose.util.angleForXAndRadius
import com.adriano.compose.util.pointForRadiusAndAngle
import com.adriano.compose.util.radiusForPoint
import com.adriano.composecomponents.util.horizontalCenter
import com.adriano.composecomponents.util.verticalCenter

@Composable
fun CircularSlider(modifier: Modifier = Modifier) {

    var dragPosition by remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = modifier.dragGestureFilter(
            dragObserver = object : DragObserver {

                override fun onStart(downPosition: Offset) {
                    dragPosition = downPosition
                }

                override fun onDrag(dragDistance: Offset): Offset {
                    dragPosition += dragDistance
                    return super.onDrag(dragDistance)
                }
            }
        )
    ) {
        val (indicatorX, indicatorY) = calculateIndicatorPosition(dragPosition)

        translate(indicatorX, indicatorY) {
            drawCircle(
                color = Color.Magenta,
                radius = indicatorCircleRadius(),
                style = Fill
            )
        }

        drawCircle(
            color = Color.Magenta.copy(alpha = 0.4f),
            radius = outerCircleRadius(),
            style = Stroke(width = 6.dp.toPx())
        )
    }
}

private fun DrawScope.calculateIndicatorPosition(dragPosition: Offset): Offset {
    val dragXOnCanvas = dragPosition.x - horizontalCenter
    val dragYOnCanvas = dragPosition.y - verticalCenter
    val radius = radiusForPoint(dragXOnCanvas, dragYOnCanvas)
    val angle = angleForXAndRadius(radius, dragXOnCanvas)
    val adjustedAngle = if (dragYOnCanvas < 0) angle * -1 else angle
    return pointForRadiusAndAngle(outerCircleRadius(), adjustedAngle)
}

private fun DrawScope.indicatorCircleRadius(): Float {
    return outerCircleRadius() / 12
}

private fun DrawScope.outerCircleRadius(): Float {
    return (horizontalCenter).coerceAtMost(verticalCenter)
}

@Preview
@Composable
fun CircularSliderPreview() {
    CircularSlider(Modifier.fillMaxSize())
}