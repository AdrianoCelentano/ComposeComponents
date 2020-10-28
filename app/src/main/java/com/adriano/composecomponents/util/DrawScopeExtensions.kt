package com.adriano.composecomponents.util

import androidx.compose.ui.graphics.drawscope.DrawScope

val DrawScope.horizontalCenter get() = size.width / 2
val DrawScope.verticalCenter get() = size.height / 2