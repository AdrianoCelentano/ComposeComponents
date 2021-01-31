package com.adriano.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.dispatch.withFrameMillis
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientLifecycleOwner
import androidx.lifecycle.whenStarted
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin

@Composable
fun animationTimeMillis(): State<Long> {
    val millisState = mutableStateOf(0L)
    val lifecycleOwner = AmbientLifecycleOwner.current
    LaunchedEffect(Unit) {
        val startTime = withFrameMillis { it }
        lifecycleOwner.whenStarted {
            while (true) {
                withFrameMillis { frameTime ->
                    millisState.value = frameTime - startTime
                }
            }
        }
    }
    return millisState
}

fun sinebow(t: Float): Color {
    return Color(
        red = sin(PI * (t + 0f / 3f)).pow(2).toFloat(),
        green = sin(PI * (t + 1f / 3f)).pow(2).toFloat(),
        blue = sin(PI * (t + 2f / 3f)).pow(2).toFloat(),
    )
}