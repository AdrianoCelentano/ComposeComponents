package com.adriano.compose.creative

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.adriano.compose.util.animationTimeMillis
import com.adriano.composecomponents.util.verticalCenter
import kotlin.math.sin

@Composable
fun SinusWave() {

    val millis by animationTimeMillis()
    val path = remember { Path() }
    Canvas(modifier = Modifier.fillMaxSize()) {
        path.reset()
        (1..WAVE_COUNT).forEach { waveIndex ->
            val waveDistance = waveIndex.dp.toPx() * 4
            translate(waveDistance, verticalCenter) {
                drawWave(
                    path,
                    color = Color.Green.copy(alpha = 1f / waveIndex),
                    millis
                )
            }
        }
    }
}

fun DrawScope.drawWave(path: Path, color: Color, millis: Long) {
    path.moveTo(0f, 0f)
    (0..size.width.toInt()).forEach { x ->
        val frequency = millis / 1000f
        val altitude = 100 * (sin(millis / 1000f))
        val waveLength = 0.015f
        val y = sin(x * waveLength + (frequency)) * altitude
        path.lineTo(x.toFloat(), y)
    }
    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = 2.dp.toPx(),
            join = StrokeJoin.Round,
        )
    )
}

private const val WAVE_COUNT = 10

@Preview
@Composable
fun SinusWavePreview() {
    SinusWave()
}