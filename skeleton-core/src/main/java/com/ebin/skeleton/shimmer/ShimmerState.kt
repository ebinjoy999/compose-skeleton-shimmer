package com.ebin.skeleton.shimmer

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import com.ebin.skeleton.theme.LocalSkeletonColors

/**
 * Holds the state of a shimmer animation with advanced configuration.
 *
 * This class manages the animated progress value and full configuration
 * for shimmer effects. Use [rememberShimmerState] to create and remember
 * an instance of this class.
 *
 * @property progress The current animation progress (0f to 1f)
 * @property config The full shimmer configuration
 * @property colors The shimmer color configuration
 * @property isPaused Whether the animation is currently paused
 * @property isReducedMotion Whether reduced motion is active
 */
@Stable
class ShimmerState internal constructor(
    private val progressState: State<Float>,
    val config: ShimmerConfig,
    val colors: ShimmerColors,
    private val pausedState: State<Boolean>,
    val isReducedMotion: Boolean = false
) {
    /**
     * Current progress of the shimmer animation (0f to 1f).
     * Returns 0f when paused or reduced motion is active with non-pulse type.
     */
    val progress: Float
        get() = when {
            pausedState.value -> 0f
            isReducedMotion && config.type != ShimmerType.Pulse -> 0f
            else -> progressState.value
        }
    
    /**
     * Whether the animation is currently paused.
     */
    val isPaused: Boolean
        get() = pausedState.value
    
    // Legacy compatibility properties
    val baseColor: Color get() = colors.baseColor
    val highlightColor: Color get() = colors.highlightColor
    val direction: ShimmerDirection get() = config.direction
    
    /**
     * Effective shimmer width based on config.
     */
    val shimmerWidth: Float get() = config.shimmerWidth
    
    /**
     * Effective intensity based on config.
     */
    val intensity: Float get() = config.intensity
    
    /**
     * Shimmer type (Linear, Radial, Pulse, Wave).
     */
    val type: ShimmerType get() = config.type
    
    /**
     * Number of waves for wave type shimmer.
     */
    val waveCount: Int get() = config.waveCount
    
    /**
     * Drop-off style for gradient edges.
     */
    val dropOff: ShimmerDropOff get() = config.dropOff
    
    /**
     * Custom angle for the shimmer gradient (if specified).
     */
    val angle: Float? get() = config.angle
    
    /**
     * Blend mode for shimmer overlay.
     */
    val blendMode: BlendMode get() = config.blendMode
}

/**
 * Controller for managing shimmer animations across multiple components.
 *
 * Use this to create synchronized shimmer effects or to control
 * shimmer state globally (pause/resume all shimmers).
 *
 * Example:
 * ```kotlin
 * val controller = rememberShimmerController()
 * 
 * // Pause all shimmers
 * controller.pause()
 * 
 * // Resume all shimmers  
 * controller.resume()
 * ```
 */
@Stable
class ShimmerController internal constructor(
    internal val pausedState: androidx.compose.runtime.MutableState<Boolean>
) {
    /**
     * Whether all shimmers controlled by this controller are paused.
     */
    var isPaused: Boolean
        get() = pausedState.value
        set(value) { pausedState.value = value }
    
    /**
     * Pauses all shimmer animations.
     */
    fun pause() {
        pausedState.value = true
    }
    
    /**
     * Resumes all shimmer animations.
     */
    fun resume() {
        pausedState.value = false
    }
    
    /**
     * Toggles the pause state.
     */
    fun toggle() {
        pausedState.value = !pausedState.value
    }
}

/**
 * Creates and remembers a [ShimmerController] for managing multiple shimmer states.
 *
 * Example:
 * ```kotlin
 * val controller = rememberShimmerController()
 * 
 * // Create states linked to this controller
 * val state1 = rememberShimmerState(controller = controller)
 * val state2 = rememberShimmerState(controller = controller)
 * 
 * // Pause all shimmers
 * controller.pause()
 * ```
 *
 * @param initiallyPaused Whether shimmers should start paused
 * @return A remembered [ShimmerController] instance
 */
@Composable
fun rememberShimmerController(
    initiallyPaused: Boolean = false
): ShimmerController {
    val pausedState = remember { mutableStateOf(initiallyPaused) }
    return remember { ShimmerController(pausedState) }
}

/**
 * Creates and remembers a [ShimmerState] with advanced configuration.
 *
 * This composable function creates an infinite shimmer animation that can be
 * applied to any composable using [Modifier.shimmer].
 *
 * Example:
 * ```kotlin
 * val shimmerState = rememberShimmerState(
 *     config = ShimmerConfig(
 *         durationMillis = 1200,
 *         shimmerWidth = 0.5f,
 *         easing = ShimmerEasing.EaseInOut
 *     )
 * )
 *
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .shimmer(shimmerState)
 * )
 * ```
 *
 * @param config Configuration for the shimmer animation
 * @param colors Custom colors for the shimmer effect
 * @param controller Optional controller for global shimmer management
 * @param staggerIndex Index for staggered animations (delays animation start)
 * @return A remembered [ShimmerState] instance
 */
@Composable
fun rememberShimmerState(
    config: ShimmerConfig = ShimmerConfig.Default,
    colors: ShimmerColors? = null,
    controller: ShimmerController? = null,
    staggerIndex: Int = 0
): ShimmerState {
    val skeletonColors = LocalSkeletonColors.current
    
    // Reduced motion check would go here in production
    val isReducedMotion = false
    
    val effectiveColors = colors ?: ShimmerColors.fromHighlight(
        baseColor = skeletonColors.baseColor,
        highlightColor = skeletonColors.highlightColor
    )
    
    // Calculate stagger delay
    val totalDelay = config.delayMillis + (staggerIndex * config.staggerDelayMillis)
    
    val transition = rememberInfiniteTransition(label = "shimmer_transition")
    
    val repeatMode = when (config.repeatMode) {
        ShimmerRepeatMode.Restart -> RepeatMode.Restart
        ShimmerRepeatMode.Reverse -> RepeatMode.Reverse
    }
    
    val animSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(
            durationMillis = config.durationMillis,
            delayMillis = totalDelay,
            easing = config.easing.easing
        ),
        repeatMode = repeatMode
    )
    
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = animSpec,
        label = "shimmer_progress"
    )
    
    // Use controller's paused state or create local one
    val pausedState = controller?.pausedState ?: remember { mutableStateOf(false) }
    
    return remember(effectiveColors, config, isReducedMotion) {
        ShimmerState(
            progressState = derivedStateOf { progress },
            config = config,
            colors = effectiveColors,
            pausedState = pausedState,
            isReducedMotion = isReducedMotion && config.respectReducedMotion
        )
    }
}

/**
 * Creates and remembers a [ShimmerState] with simple parameters.
 *
 * This overload provides a convenient API for common customizations
 * without needing to create a full [ShimmerConfig].
 *
 * @param durationMillis Duration of one shimmer cycle in milliseconds
 * @param direction Direction of the shimmer animation
 * @param type Type of shimmer effect
 * @param shimmerWidth Width of shimmer highlight (0.1 to 1.0)
 * @param intensity Shimmer intensity (0.0 to 1.0)
 * @param easing Animation easing function
 * @param baseColor Base/background color of the shimmer
 * @param highlightColor Highlight color that animates across
 * @param controller Optional controller for global shimmer management
 * @return A remembered [ShimmerState] instance
 */
@Composable
fun rememberShimmerState(
    durationMillis: Int = 1000,
    direction: ShimmerDirection = ShimmerDirection.LeftToRight,
    type: ShimmerType = ShimmerType.Linear,
    shimmerWidth: Float = 0.4f,
    intensity: Float = 1.0f,
    easing: ShimmerEasing = ShimmerEasing.Linear,
    baseColor: Color? = null,
    highlightColor: Color? = null,
    controller: ShimmerController? = null
): ShimmerState {
    val skeletonColors = LocalSkeletonColors.current
    
    val config = ShimmerConfig(
        durationMillis = durationMillis,
        direction = direction,
        type = type,
        shimmerWidth = shimmerWidth.coerceIn(0.1f, 1.0f),
        intensity = intensity.coerceIn(0.0f, 1.0f),
        easing = easing
    )
    
    val colors = ShimmerColors.fromHighlight(
        baseColor = baseColor ?: skeletonColors.baseColor,
        highlightColor = highlightColor ?: skeletonColors.highlightColor
    )
    
    return rememberShimmerState(
        config = config,
        colors = colors,
        controller = controller
    )
}

/**
 * Creates a shimmer state using a preset configuration.
 *
 * Available presets:
 * - [ShimmerConfig.Default] - Standard shimmer
 * - [ShimmerConfig.Subtle] - Fast, low-intensity shimmer
 * - [ShimmerConfig.Prominent] - Slow, high-intensity shimmer
 * - [ShimmerConfig.Pulse] - Breathing/pulse effect
 * - [ShimmerConfig.Spotlight] - Radial spotlight effect
 * - [ShimmerConfig.MultiWave] - Multiple wave shimmer
 * - [ShimmerConfig.Accessible] - Reduced motion friendly
 *
 * @param preset The preset configuration to use
 * @param controller Optional controller for global shimmer management
 * @return A remembered [ShimmerState] instance
 */
@Composable
fun rememberShimmerStateWithPreset(
    preset: ShimmerConfig = ShimmerConfig.Default,
    controller: ShimmerController? = null
): ShimmerState {
    return rememberShimmerState(
        config = preset,
        controller = controller
    )
}
