package com.adriano.composecomponents.gameoflife

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun GameOfLife() {

    val cellGrid = remember { mutableStateOf(CellGrid.create()) }

    LaunchedEffect(Unit) {
        while (isActive) {
            cellGrid.value = cellGrid.value.calculateNextGrid()
            delay(100)
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        cellGrid.value.draw(this)
    }
}