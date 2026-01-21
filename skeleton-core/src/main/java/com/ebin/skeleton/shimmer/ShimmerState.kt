package com.ebin.skeleton.shimmer

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.ebin.skeleton.theme.LocalSkeletonColors
import com.ebin.skeleton.theme.SkeletonColors

/**
 * Configuration for shimmer animation behavior.
 *
 * @property durationMillis Duration of one shimmer cycle in milliseconds
 * @property delayMillis Delay between shimmer cycles in milliseconds
 * @property direction Direction of the shimmer animation
 * @property animationSpec Custom animation specification (overrides duration if provided)
 */
@Stable
data class ShimmerConfig(
    val durationMillis: Int = 1000,
    val delayMillis: Int = 200,
    val direction: ShimmerDirection = ShimmerDirection.LeftToRight,
    val animationSpec: AnimationSpec<Float>? = null
)

/**
 * Holds the state of a shimmer animation.
 *
 * This class manages the animated progress value and color configuration
 * for shimmer effects. Use [rememberShimmerState] to create and remember
 * an instance of this class.
 *
 * @property progress The current animation progress (0f to 1f)
 * @property baseColor The base/background color of the shimmer
 * @property highlightColor The highlight color that animates across
 * @property direction The direction of the shimmer animation
 */
@Stable
class ShimmerState internal constructor(
    private val progressState: State<Float>,
    val baseColor: Color,
    val highlightColor: Color,
    val direction: ShimmerDirection
) {
    /**
     * Current progress of the shimmer animation (0f to 1f).
     */
    val progress: Float
        get() = progressState.value
}

/**
 * Creates and remembers a [ShimmerState] for use with the shimmer modifier.
 *
 * This composable function creates an infinite shimmer animation that can be
 * applied to any composable using [Modifier.shimmer].
 *
 * Example:
 * ```kotlin
 * val shimmerState = rememberShimmerState()
 *
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .shimmer(shimmerState)
 * )
 * ```
 *
 * @param config Configuration for the shimmer animation
 * @param colors Custom colors for the shimmer effect (defaults to theme colors)
 * @return A remembered [ShimmerState] instance
 */
@Composable
fun rememberShimmerState(
    config: ShimmerConfig = ShimmerConfig(),
    colors: SkeletonColors? = null
): ShimmerState {
    val skeletonColors = colors ?: LocalSkeletonColors.current
    
    val transition = rememberInfiniteTransition(label = "shimmer_transition")
    
    val animSpec = config.animationSpec ?: infiniteRepeatable(
        animation = tween(
            durationMillis = config.durationMillis,
            delayMillis = config.delayMillis,
            easing = LinearEasing
        ),
        repeatMode = RepeatMode.Restart
    )
    
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = animSpec as InfiniteRepeatableSpec<Float>,
        label = "shimmer_progress"
    )
    
    return remember(skeletonColors, config.direction) {
        ShimmerState(
            progressState = object : State<Float> {
                override val value: Float
                    get() = progress
            },
            baseColor = skeletonColors.baseColor,
            highlightColor = skeletonColors.highlightColor,
            direction = config.direction
        )
    }.also {
        // Update progress reference
    }
}

/**
 * Creates and remembers a [ShimmerState] with custom parameters.
 *
 * This overload provides a more convenient API for common customizations.
 *
 * @param durationMillis Duration of one shimmer cycle in milliseconds
 * @param direction Direction of the shimmer animation
 * @param baseColor Base/background color of the shimmer
 * @param highlightColor Highlight color that animates across
 * @return A remembered [ShimmerState] instance
 */
@Composable
fun rememberShimmerState(
    durationMillis: Int = 1000,
    direction: ShimmerDirection = ShimmerDirection.LeftToRight,
    baseColor: Color? = null,
    highlightColor: Color? = null
): ShimmerState {
    val skeletonColors = LocalSkeletonColors.current
    
    val config = ShimmerConfig(
        durationMillis = durationMillis,
        direction = direction
    )
    
    val effectiveColors = if (baseColor != null || highlightColor != null) {
        SkeletonColors(
            baseColor = baseColor ?: skeletonColors.baseColor,
            highlightColor = highlightColor ?: skeletonColors.highlightColor
        )
    } else {
        null
    }
    
    return rememberShimmerState(
        config = config,
        colors = effectiveColors
    )
}
