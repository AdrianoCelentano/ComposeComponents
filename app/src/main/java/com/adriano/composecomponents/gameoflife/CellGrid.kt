package com.adriano.composecomponents.gameoflife

import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.random.Random

data class CellGrid constructor(
    private val cells: MutableList<MutableList<Cell>>
) {

    fun calculateNextGrid(): CellGrid {
        val newGrid = create()
        (0 until cellRowSize).forEach { x ->
            (0 until cellColumnSize).forEach { y ->
                val above = if (y == 0) cellColumnSize - 1 else y - 1
                val below = if (y == cellColumnSize - 1) 0 else y + 1
                val left = if (x == 0) cellRowSize - 1 else x - 1
                val right = if (x == cellRowSize - 1) 0 else x + 1

                val currentCell = cells[x][y]

                val neighbours = mutableListOf<Cell>()
                neighbours.add(cells[left][above])
                neighbours.add((cells[left][y]))
                neighbours.add((cells[left][below]))
                neighbours.add(cells[x][below])
                neighbours.add(cells[right][below])
                neighbours.add(cells[right][y])
                neighbours.add(cells[right][above])
                neighbours.add(cells[x][above])
                val aliveNeighbours = neighbours.count { it.isAlive }

                val nextStep = classicalRules(currentCell, aliveNeighbours)
                newGrid.cells[x][y] = Cell(isAlive = nextStep, x = x, y = y)
            }
        }
        return newGrid
    }

    fun draw(drawScope: DrawScope) {
        val cellWidth = drawScope.size.width / cellRowSize
        val cellHeight = drawScope.size.height / cellColumnSize
        (0 until cellRowSize).forEach { x ->
            (0 until cellColumnSize).forEach { y ->
                cells[x][y].draw(drawScope, cellWidth, cellHeight)
            }
        }
    }

    private fun classicalRules(
        currentCell: Cell,
        aliveNeighbours: Int
    ) = when {
        currentCell.isAlive && aliveNeighbours in 2..3 -> true
        currentCell.isAlive.not() && aliveNeighbours == 3 -> true
        else -> false
    }

    private fun vichniacVoteRules(
        currentCell: Cell,
        aliveNeighbours: Int
    ): Boolean {
        val aliveCount = aliveNeighbours + if (currentCell.isAlive) 1 else 0
        return when {
            aliveCount <= 4 -> false
            else -> true
        }
    }

    companion object {

        const val cellRowSize = 100
        const val cellColumnSize = 200

        fun create(): CellGrid {
            return CellGrid(MutableList(cellRowSize) { x ->
                MutableList(cellColumnSize) { y ->
                    Cell(
                        x = x,
                        y = y,
                        isAlive = Random.nextFloat() > 0.5f
                    )
                }
            })
        }
    }
}