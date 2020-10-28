package com.adriano.compose.creative

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.adriano.compose.composables.ColorCheckChip
import com.adriano.compose.util.TWO_PI
import com.adriano.compose.util.animationTimeMillis
import com.adriano.compose.util.mapRange
import com.adriano.compose.util.pointForRadiusAndAngle
import com.adriano.composecomponents.util.horizontalCenter
import com.adriano.composecomponents.util.verticalCenter
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun CircularMotion() {

    val millis by animationTimeMillis()
    val circleDataList = remember { createCircleData() }
    val path = remember { Path() }

    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(horizontalCenter, verticalCenter) {
            circleDataList.forEach { circleData ->
                drawArc(
                    millis = millis,
                    path = path,
                    circleData = circleData,
                )
            }
        }
    }
}

private fun DrawScope.drawArc(
    millis: Long,
    path: Path,
    circleData: CircleData,
) {
    path.reset()
    val angles = getAngles(circleData.fraction)
    val circleOffsets = getCircleOffsets(angles, circleData, millis)

    circleOffsets.forEachIndexed { index, offset ->
        if (index == 0) path.moveTo(offset.x, offset.y)
        else path.lineTo(offset.x, offset.y)
    }

    drawPath(
        path = path,
        brush = LinearGradient(
            colors = listOf(circleData.color, circleData.color.copy(alpha = 0f)),
            startX = circleOffsets.first().x,
            startY = circleOffsets.first().y,
            endX = circleOffsets.last().x,
            endY = circleOffsets.last().y,
        ),
        style = Stroke(
            width = 4.dp.toPx(),
            join = StrokeJoin.Round,
        )
    )
}

private fun DrawScope.getCircleOffsets(
    angles: List<Float>,
    circleData: CircleData,
    millis: Long,
): List<Offset> {
    return angles.map { angle ->
        val circleProgress = millis / 1000f * TWO_PI * circleData.speed
        return@map pointForRadiusAndAngle(
            radius = circleData.radius.toPx(),
            angle = angle - circleProgress
        )
    }
}

private fun getAngles(
    @FloatRange(from = 0.0, to = 1.0) circleFraction: Float = 1.0f
): List<Float> {
    return (0..100).map {
        mapRange(
            it.toFloat(),
            0f,
            100f,
            0f,
            TWO_PI * circleFraction
        )
    }
}

private fun createCircleData(): List<CircleData> {
    return List(50) {
        CircleData(
            radius = Random.nextInt(10..120).dp,
            fraction = Random.nextInt(3..6) / 10f,
            speed = Random.nextInt(20..80) / 100f,
            color = lerp(Color.Cyan, Color.Blue, Random.nextFloat())
        )
    }
}

data class CircleData(
    val radius: Dp,
    val fraction: Float,
    val speed: Float,
    val color: Color
)

@Preview
@Composable
fun CircularMotionPreview() {
    CircularMotion()
}