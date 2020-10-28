package com.adriano.composecomponents.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Rect

@Stable
fun Rect.translateX(translateX: Float): Rect {
    return Rect(
        left + translateX,
        top,
        right + translateX,
        bottom
    )
}