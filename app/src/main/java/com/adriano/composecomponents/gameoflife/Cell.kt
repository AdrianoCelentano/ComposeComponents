package com.adriano.composecomponents.gameoflife

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill

data class Cell(
    val x: Int,
    val y: Int,
    var isAlive: Boolean,
) {

    fun draw(drawScope: DrawScope, cellWidth: Float, cellHeight: Float) {
        val color = if (isAlive) Color.Black else Color.White
        drawScope.drawRect(
            color = color,
            topLeft = Offset(x = x * cellWidth, y = y * cellHeight),
            size = Size(cellWidth, cellHeight),
            style = Fill
        )
    }
}