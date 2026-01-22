package com.ebin.skeleton.modifier

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.ebin.skeleton.shimmer.ShimmerConfig
import com.ebin.skeleton.shimmer.ShimmerDirection
import com.ebin.skeleton.shimmer.ShimmerDropOff
import com.ebin.skeleton.shimmer.ShimmerState
import com.ebin.skeleton.shimmer.ShimmerType
import com.ebin.skeleton.theme.LocalSkeletonColors
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Applies a shimmer animation effect to the composable.
 *
 * This modifier creates a GPU-friendly shimmer animation using linear gradients
 * that sweep across the composable. The animation is efficient and maintains
 * 60fps scrolling performance.
 *
 * Supports multiple shimmer types:
 * - **Linear**: Standard sweeping shimmer
 * - **Radial**: Spotlight/circular shimmer
 * - **Pulse**: Breathing/fading effect
 * - **Wave**: Multiple shimmer bands
 *
 * Example:
 * ```kotlin
 * val shimmerState = rememberShimmerState()
 *
 * Box(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(48.dp)
 *         .shimmer(shimmerState)
 * )
 * ```
 *
 * @param shimmerState The [ShimmerState] controlling the animation
 * @return Modified [Modifier] with shimmer effect applied
 */
fun Modifier.shimmer(shimmerState: ShimmerState): Modifier = this.drawWithContent {
    val progress = shimmerState.progress
    val baseColor = shimmerState.baseColor
    val highlightColor = shimmerState.highlightColor
    val intensity = shimmerState.intensity
    
    // Apply intensity to highlight color
    val effectiveHighlight = highlightColor.copy(
        alpha = highlightColor.alpha * intensity
    )
    
    when (shimmerState.type) {
        ShimmerType.Linear -> drawLinearShimmer(
            progress = progress,
            baseColor = baseColor,
            highlightColor = effectiveHighlight,
            shimmerState = shimmerState
        )
        ShimmerType.Radial -> drawRadialShimmer(
            progress = progress,
            baseColor = baseColor,
            highlightColor = effectiveHighlight,
            shimmerWidth = shimmerState.shimmerWidth
        )
        ShimmerType.Pulse -> drawPulseShimmer(
            progress = progress,
            baseColor = baseColor,
            highlightColor = effectiveHighlight
        )
        ShimmerType.Wave -> drawWaveShimmer(
            progress = progress,
            baseColor = baseColor,
            highlightColor = effectiveHighlight,
            shimmerState = shimmerState
        )
    }
}

/**
 * Draws linear shimmer effect.
 */
private fun DrawScope.drawLinearShimmer(
    progress: Float,
    baseColor: Color,
    highlightColor: Color,
    shimmerState: ShimmerState
) {
    val shimmerWidth = size.width * shimmerState.shimmerWidth
    val shimmerHeight = size.height * shimmerState.shimmerWidth
    
    // Check for custom angle
    val angle = shimmerState.angle
    
    val (startOffset, endOffset) = if (angle != null) {
        // Use custom angle
        calculateAngleOffsets(progress, angle, shimmerWidth, size)
    } else {
        // Use direction-based offsets
        calculateDirectionOffsets(
            progress = progress,
            direction = shimmerState.direction,
            shimmerWidth = shimmerWidth,
            shimmerHeight = shimmerHeight,
            size = size
        )
    }
    
    // Build gradient colors based on drop-off
    val gradientColors = buildGradientColors(
        baseColor = baseColor,
        highlightColor = highlightColor,
        dropOff = shimmerState.dropOff
    )
    
    val brush = Brush.linearGradient(
        colors = gradientColors,
        start = startOffset,
        end = endOffset
    )
    
    drawRect(brush = brush, blendMode = shimmerState.blendMode)
}

/**
 * Draws radial/spotlight shimmer effect.
 */
private fun DrawScope.drawRadialShimmer(
    progress: Float,
    baseColor: Color,
    highlightColor: Color,
    shimmerWidth: Float
) {
    val centerX = size.width * progress
    val centerY = size.height / 2
    val maxRadius = sqrt(size.width * size.width + size.height * size.height) * shimmerWidth
    
    val brush = Brush.radialGradient(
        colors = listOf(highlightColor, highlightColor.copy(alpha = 0.5f), baseColor),
        center = Offset(centerX, centerY),
        radius = maxRadius * (0.2f + progress * 0.8f)
    )
    
    drawRect(brush = brush)
}

/**
 * Draws pulse/breathing shimmer effect.
 */
private fun DrawScope.drawPulseShimmer(
    progress: Float,
    baseColor: Color,
    highlightColor: Color
) {
    // Smooth pulse using sine wave
    val pulseAlpha = (sin(progress * PI * 2 - PI / 2) + 1) / 2
    val color = lerp(baseColor, highlightColor, pulseAlpha.toFloat())
    
    drawRect(color = color)
}

/**
 * Draws wave shimmer effect with multiple bands.
 */
private fun DrawScope.drawWaveShimmer(
    progress: Float,
    baseColor: Color,
    highlightColor: Color,
    shimmerState: ShimmerState
) {
    val waveCount = shimmerState.waveCount
    val shimmerWidth = shimmerState.shimmerWidth / waveCount
    val totalWidth = size.width + size.width * shimmerWidth * 2
    
    val colors = mutableListOf<Color>()
    val stops = mutableListOf<Float>()
    
    // Build multi-wave gradient
    repeat(waveCount) { waveIndex ->
        val waveProgress = (progress + waveIndex.toFloat() / waveCount) % 1f
        val waveStart = waveProgress - shimmerWidth / 2
        val waveEnd = waveProgress + shimmerWidth / 2
        
        if (waveStart >= 0f) {
            stops.add(waveStart)
            colors.add(baseColor)
        }
        stops.add(waveProgress.coerceIn(0f, 1f))
        colors.add(highlightColor)
        if (waveEnd <= 1f) {
            stops.add(waveEnd)
            colors.add(baseColor)
        }
    }
    
    // Ensure we have valid gradient
    if (colors.size < 2) {
        colors.clear()
        colors.addAll(listOf(baseColor, highlightColor, baseColor))
    }
    
    val brush = Brush.horizontalGradient(colors = colors)
    drawRect(brush = brush)
}

/**
 * Calculates offsets for custom angle shimmer.
 */
private fun calculateAngleOffsets(
    progress: Float,
    angleDegrees: Float,
    shimmerWidth: Float,
    size: Size
): Pair<Offset, Offset> {
    val angleRadians = angleDegrees * PI.toFloat() / 180f
    val diagonal = sqrt(size.width * size.width + size.height * size.height)
    
    val dx = cos(angleRadians) * diagonal
    val dy = sin(angleRadians) * diagonal
    
    val startX = -shimmerWidth + (size.width + shimmerWidth * 2) * progress
    val startY = 0f
    
    return Offset(startX, startY) to Offset(startX + dx * shimmerWidth / diagonal, dy * shimmerWidth / diagonal)
}

/**
 * Calculates offsets based on shimmer direction.
 */
private fun calculateDirectionOffsets(
    progress: Float,
    direction: ShimmerDirection,
    shimmerWidth: Float,
    shimmerHeight: Float,
    size: Size
): Pair<Offset, Offset> {
    return when (direction) {
        ShimmerDirection.LeftToRight -> {
            val start = -shimmerWidth + (size.width + shimmerWidth * 2) * progress
            Offset(start, 0f) to Offset(start + shimmerWidth, size.height)
        }
        ShimmerDirection.RightToLeft -> {
            val start = size.width + shimmerWidth - (size.width + shimmerWidth * 2) * progress
            Offset(start, 0f) to Offset(start - shimmerWidth, size.height)
        }
        ShimmerDirection.TopToBottom -> {
            val start = -shimmerHeight + (size.height + shimmerHeight * 2) * progress
            Offset(0f, start) to Offset(size.width, start + shimmerHeight)
        }
        ShimmerDirection.BottomToTop -> {
            val start = size.height + shimmerHeight - (size.height + shimmerHeight * 2) * progress
            Offset(0f, start) to Offset(size.width, start - shimmerHeight)
        }
    }
}

/**
 * Builds gradient colors based on drop-off setting.
 */
private fun buildGradientColors(
    baseColor: Color,
    highlightColor: Color,
    dropOff: ShimmerDropOff
): List<Color> {
    return when (dropOff) {
        ShimmerDropOff.Sharp -> listOf(
            baseColor,
            highlightColor,
            highlightColor,
            baseColor
        )
        ShimmerDropOff.Normal -> listOf(
            baseColor,
            baseColor.copy(alpha = 0.8f),
            highlightColor,
            highlightColor,
            baseColor.copy(alpha = 0.8f),
            baseColor
        )
        ShimmerDropOff.Soft -> listOf(
            baseColor,
            baseColor.copy(alpha = 0.9f),
            baseColor.copy(alpha = 0.7f),
            highlightColor,
            highlightColor,
            baseColor.copy(alpha = 0.7f),
            baseColor.copy(alpha = 0.9f),
            baseColor
        )
        ShimmerDropOff.UltraSoft -> listOf(
            baseColor,
            baseColor.copy(alpha = 0.95f),
            baseColor.copy(alpha = 0.85f),
            baseColor.copy(alpha = 0.7f),
            highlightColor.copy(alpha = 0.8f),
            highlightColor,
            highlightColor,
            highlightColor.copy(alpha = 0.8f),
            baseColor.copy(alpha = 0.7f),
            baseColor.copy(alpha = 0.85f),
            baseColor.copy(alpha = 0.95f),
            baseColor
        )
    }
}

/**
 * Linear interpolation between two colors.
 */
private fun lerp(start: Color, end: Color, fraction: Float): Color {
    return Color(
        red = start.red + (end.red - start.red) * fraction,
        green = start.green + (end.green - start.green) * fraction,
        blue = start.blue + (end.blue - start.blue) * fraction,
        alpha = start.alpha + (end.alpha - start.alpha) * fraction
    )
}

/**
 * Applies a shimmer animation effect with default configuration.
 *
 * This is a convenience modifier that creates its own shimmer animation
 * internally. Use this when you don't need to share shimmer state across
 * multiple components.
 *
 * Example:
 * ```kotlin
 * Box(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(48.dp)
 *         .shimmer()
 * )
 * ```
 *
 * @param durationMillis Duration of one shimmer cycle
 * @param direction Direction of the shimmer animation
 * @return Modified [Modifier] with shimmer effect applied
 */
fun Modifier.shimmer(
    durationMillis: Int = 1000,
    direction: ShimmerDirection = ShimmerDirection.LeftToRight
): Modifier = composed {
    val skeletonColors = LocalSkeletonColors.current
    
    val transition = rememberInfiniteTransition(label = "shimmer")
    
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_progress"
    )
    
    drawWithContent {
        val shimmerWidth = size.width * 0.4f
        val shimmerHeight = size.height * 0.4f
        
        val (startOffset, endOffset) = when (direction) {
            ShimmerDirection.LeftToRight -> {
                val start = -shimmerWidth + (size.width + shimmerWidth * 2) * progress
                Offset(start, 0f) to Offset(start + shimmerWidth, size.height)
            }
            ShimmerDirection.RightToLeft -> {
                val start = size.width + shimmerWidth - (size.width + shimmerWidth * 2) * progress
                Offset(start, 0f) to Offset(start - shimmerWidth, size.height)
            }
            ShimmerDirection.TopToBottom -> {
                val start = -shimmerHeight + (size.height + shimmerHeight * 2) * progress
                Offset(0f, start) to Offset(size.width, start + shimmerHeight)
            }
            ShimmerDirection.BottomToTop -> {
                val start = size.height + shimmerHeight - (size.height + shimmerHeight * 2) * progress
                Offset(0f, start) to Offset(size.width, start - shimmerHeight)
            }
        }
        
        val brush = Brush.linearGradient(
            colors = listOf(
                skeletonColors.baseColor,
                skeletonColors.highlightColor,
                skeletonColors.highlightColor,
                skeletonColors.baseColor
            ),
            start = startOffset,
            end = endOffset
        )
        
        drawRect(brush = brush)
    }
}

/**
 * Applies a custom shimmer effect using a provided brush factory.
 *
 * This modifier allows full customization of the shimmer appearance by
 * providing a custom brush at each animation frame.
 *
 * Example:
 * ```kotlin
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .shimmerWithBrush { progress, size ->
 *             Brush.linearGradient(
 *                 colors = listOf(Color.Red, Color.Blue),
 *                 start = Offset(-size.width + size.width * 2 * progress, 0f),
 *                 end = Offset(size.width * 2 * progress, size.height)
 *             )
 *         }
 * )
 * ```
 *
 * @param durationMillis Duration of one shimmer cycle
 * @param brushFactory Factory function that creates the brush for each frame
 * @return Modified [Modifier] with custom shimmer effect applied
 */
fun Modifier.shimmerWithBrush(
    durationMillis: Int = 1000,
    brushFactory: (progress: Float, size: androidx.compose.ui.geometry.Size) -> Brush
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "custom_shimmer")
    
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "custom_shimmer_progress"
    )
    
    drawWithContent {
        val brush = brushFactory(progress, size)
        drawRect(brush = brush)
    }
}
