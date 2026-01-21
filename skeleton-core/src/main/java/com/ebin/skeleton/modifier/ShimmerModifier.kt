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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.ebin.skeleton.shimmer.ShimmerDirection
import com.ebin.skeleton.shimmer.ShimmerState
import com.ebin.skeleton.theme.LocalSkeletonColors

/**
 * Applies a shimmer animation effect to the composable.
 *
 * This modifier creates a GPU-friendly shimmer animation using linear gradients
 * that sweep across the composable. The animation is efficient and maintains
 * 60fps scrolling performance.
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
    val direction = shimmerState.direction
    
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
            baseColor,
            highlightColor,
            highlightColor,
            baseColor
        ),
        start = startOffset,
        end = endOffset
    )
    
    drawRect(brush = brush)
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
