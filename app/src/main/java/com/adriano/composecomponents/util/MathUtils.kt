package com.adriano.compose.util

import androidx.compose.ui.geometry.Offset
import kotlin.math.*

const val PI = Math.PI.toFloat()
const val TWO_PI = 2 * PI
const val HALF_PI = PI / 2

/**
 * gives the radius for a point on the circle
 */
fun radiusForPoint(x: Double, y: Double): Double {
    return sqrt(x.pow(2) + y.pow(2))
}

/**
 * gives the radius for a point on the circle
 */
fun radiusForPoint(x: Float, y: Float): Float {
    return sqrt(x.pow(2) + y.pow(2))
}

/**
 * gives a point on a circle for the radius and an angle
 */
fun pointForRadiusAndAngle(radius: Float, angle: Float): Offset {
    return Offset(
        x = radius * cos(angle),
        y = radius * sin(angle)
    )
}


/**
 * gives an angle for a x-coordinate and a radius
 */
fun angleForXAndRadius(radius: Float, x: Float): Float {
    return acos(x / radius)
}
/**
 * Re-maps a number from one range to another.
 */
fun mapRange(value: Float, start1: Float, stop1: Float, start2: Float, stop2: Float): Float {
    return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))
}