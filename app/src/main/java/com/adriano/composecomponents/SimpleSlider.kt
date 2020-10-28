package com.adriano.compose.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.gesture.DragObserver
import androidx.compose.ui.gesture.dragGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.adriano.compose.creative.CircularMotion
import com.adriano.composecomponents.util.translateX
import com.adriano.composecomponents.util.verticalCenter

@Composable
fun SimpleSlider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary
) {
    val dragArea = mutableStateOf(Rect.Zero)
    Canvas(
        modifier = modifier.dragGestureFilter(
            startDragImmediately = true,
            dragObserver = dragObserver(dragArea)
        )
    ) {
        if (initIndicatorArea(dragArea)) return@Canvas
        drawProgress(dragArea.value.center.x, color)
        drawIndicator(dragArea.value.center.x, color)
    }
}

@Composable
fun dragObserver(indicatorArea: MutableState<Rect>): DragObserver {

    val indicatorAreaRadius = indicatorRadiusPixel()
    var isDragging = false

    return object : DragObserver {

        override fun onStart(downPosition: Offset) {
            if (indicatorArea.value.contains(downPosition)) {
                isDragging = true
                indicatorArea.value = Rect(
                    center = downPosition,
                    radius = indicatorAreaRadius
                )
            }
        }

        override fun onDrag(dragDistance: Offset): Offset {
            if (isDragging) {
                indicatorArea.value = indicatorArea.value.translateX(dragDistance.x)
            }
            return super.onDrag(dragDistance)
        }

        override fun onStop(velocity: Offset) {
            isDragging = false
        }
    }
}

private fun DrawScope.initIndicatorArea(dragArea: MutableState<Rect>): Boolean {
    val notInitialized = dragArea.value == Rect.Zero
    if (notInitialized) {
        val circleRadiusPixel = indicatorRadiusPixel()
        dragArea.value = Rect(
            0f,
            verticalCenter - circleRadiusPixel,
            circleRadiusPixel * 2,
            verticalCenter + circleRadiusPixel
        )
    }
    return notInitialized
}

private fun DrawScope.drawProgress(
    dragPosition: Float,
    color: Color
) {
    val strokeWidth = 12

    drawLine(
        color = color,
        strokeWidth = strokeWidth.dp.toPx(),
        start = Offset(0f, verticalCenter),
        end = Offset(dragPosition, verticalCenter)
    )

    drawLine(
        color = color.copy(alpha = 0.3f),
        strokeWidth = strokeWidth.dp.toPx(),
        start = Offset(dragPosition, verticalCenter),
        end = Offset(size.width, verticalCenter)
    )
}

private fun DrawScope.drawIndicator(
    dragPosition: Float,
    color: Color
) {
    val circleRadiusPixel = indicatorRadiusPixel()
    val canvasDragPosition = dragPosition - size.width / 2
    val minPosition = -size.width / 2 + circleRadiusPixel
    val maxPosition = size.width / 2 - circleRadiusPixel
    val indicatorPosition = canvasDragPosition.coerceIn(minPosition, maxPosition)
    translate(left = indicatorPosition) {
        drawCircle(color = color, radius = circleRadiusPixel)
    }
}

@Composable
private fun indicatorRadiusPixel(): Float = with(DensityAmbient.current) { 16.dp.toPx() }

private fun DrawScope.indicatorRadiusPixel(): Float = 16.dp.toPx()

@Preview
@Composable
fun SimpleSliderPreview() {
    SimpleSlider(Modifier.fillMaxSize())
}