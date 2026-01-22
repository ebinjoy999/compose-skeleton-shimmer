package com.ebin.skeleton.shimmer

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color

/**
 * Defines the direction of the shimmer animation.
 */
enum class ShimmerDirection {
    /**
     * Shimmer animates from left to right.
     */
    LeftToRight,
    
    /**
     * Shimmer animates from right to left.
     */
    RightToLeft,
    
    /**
     * Shimmer animates from top to bottom.
     */
    TopToBottom,
    
    /**
     * Shimmer animates from bottom to top.
     */
    BottomToTop
}

/**
 * Defines the type of shimmer effect.
 */
enum class ShimmerType {
    /**
     * Standard linear gradient shimmer that sweeps across.
     */
    Linear,
    
    /**
     * Radial/spotlight shimmer that expands from center.
     */
    Radial,
    
    /**
     * Pulse/breathing effect - fades in and out.
     */
    Pulse,
    
    /**
     * Wave effect with multiple shimmer bands.
     */
    Wave
}

/**
 * Defines the repeat mode for shimmer animation.
 */
enum class ShimmerRepeatMode {
    /**
     * Animation restarts from the beginning after completing.
     */
    Restart,
    
    /**
     * Animation reverses direction after completing.
     */
    Reverse
}

/**
 * Pre-defined easing functions for shimmer animations.
 */
enum class ShimmerEasing(val easing: Easing) {
    /**
     * Constant speed throughout the animation.
     */
    Linear(LinearEasing),
    
    /**
     * Starts fast and slows down at the end.
     */
    EaseOut(LinearOutSlowInEasing),
    
    /**
     * Starts slow and speeds up at the end.
     */
    EaseIn(FastOutLinearInEasing),
    
    /**
     * Starts slow, speeds up in the middle, and slows down at the end.
     */
    EaseInOut(FastOutSlowInEasing)
}

/**
 * Defines intensity drop-off for shimmer gradient edges.
 * Controls how sharply the highlight fades at its boundaries.
 */
enum class ShimmerDropOff {
    /**
     * Sharp, well-defined edges on the shimmer highlight.
     */
    Sharp,
    
    /**
     * Standard gradient fade.
     */
    Normal,
    
    /**
     * Very gradual, soft fade at edges.
     */
    Soft,
    
    /**
     * Ultra-smooth gradient transition.
     */
    UltraSoft
}

/**
 * Advanced configuration for shimmer animation behavior.
 *
 * This configuration class provides fine-grained control over every aspect
 * of the shimmer animation, making it suitable for production apps with
 * specific design requirements.
 *
 * Example:
 * ```kotlin
 * val config = ShimmerConfig(
 *     durationMillis = 1200,
 *     direction = ShimmerDirection.LeftToRight,
 *     angle = 20f,
 *     shimmerWidth = 0.5f,
 *     intensity = 0.8f,
 *     dropOff = ShimmerDropOff.Soft,
 *     easing = ShimmerEasing.EaseInOut,
 *     repeatMode = ShimmerRepeatMode.Restart
 * )
 * ```
 *
 * @property durationMillis Duration of one shimmer cycle in milliseconds
 * @property delayMillis Delay between shimmer cycles in milliseconds
 * @property direction Direction of the shimmer animation
 * @property type Type of shimmer effect (Linear, Radial, Pulse, Wave)
 * @property angle Custom angle in degrees (0-360) for the shimmer gradient. 
 *                 Only applies when direction is not explicitly set. 0° is horizontal LTR.
 * @property shimmerWidth Width of the shimmer highlight band as a fraction (0.1 to 1.0)
 * @property intensity Overall intensity/opacity of the shimmer effect (0.0 to 1.0)
 * @property dropOff How sharply the shimmer gradient fades at edges
 * @property easing Animation easing function
 * @property repeatMode How the animation repeats (Restart or Reverse)
 * @property waveCount Number of shimmer waves for Wave type (1-5)
 * @property blendMode Blend mode for shimmer overlay
 * @property clipToContent Whether to clip shimmer to actual content bounds
 * @property respectReducedMotion Whether to respect system reduced motion settings
 * @property staggerDelayMillis Delay offset for staggered list animations
 */
@Stable
data class ShimmerConfig(
    val durationMillis: Int = 1000,
    val delayMillis: Int = 200,
    val direction: ShimmerDirection = ShimmerDirection.LeftToRight,
    val type: ShimmerType = ShimmerType.Linear,
    val angle: Float? = null,
    val shimmerWidth: Float = 0.4f,
    val intensity: Float = 1.0f,
    val dropOff: ShimmerDropOff = ShimmerDropOff.Normal,
    val easing: ShimmerEasing = ShimmerEasing.Linear,
    val repeatMode: ShimmerRepeatMode = ShimmerRepeatMode.Restart,
    val waveCount: Int = 1,
    val blendMode: BlendMode = BlendMode.SrcOver,
    val clipToContent: Boolean = false,
    val respectReducedMotion: Boolean = true,
    val staggerDelayMillis: Int = 0
) {
    init {
        require(shimmerWidth in 0.1f..1.0f) { "shimmerWidth must be between 0.1 and 1.0" }
        require(intensity in 0.0f..1.0f) { "intensity must be between 0.0 and 1.0" }
        require(waveCount in 1..5) { "waveCount must be between 1 and 5" }
        require(durationMillis > 0) { "durationMillis must be positive" }
        angle?.let {
            require(it in 0f..360f) { "angle must be between 0 and 360 degrees" }
        }
    }
    
    companion object {
        /**
         * Default shimmer configuration.
         */
        val Default = ShimmerConfig()
        
        /**
         * Fast, subtle shimmer for minimal distraction.
         */
        val Subtle = ShimmerConfig(
            durationMillis = 800,
            shimmerWidth = 0.3f,
            intensity = 0.6f,
            dropOff = ShimmerDropOff.Soft
        )
        
        /**
         * Slow, prominent shimmer for emphasis.
         */
        val Prominent = ShimmerConfig(
            durationMillis = 1500,
            shimmerWidth = 0.5f,
            intensity = 1.0f,
            dropOff = ShimmerDropOff.Sharp
        )
        
        /**
         * Pulse/breathing effect configuration.
         */
        val Pulse = ShimmerConfig(
            type = ShimmerType.Pulse,
            durationMillis = 1200,
            easing = ShimmerEasing.EaseInOut,
            repeatMode = ShimmerRepeatMode.Reverse
        )
        
        /**
         * Radial/spotlight shimmer configuration.
         */
        val Spotlight = ShimmerConfig(
            type = ShimmerType.Radial,
            durationMillis = 1500,
            shimmerWidth = 0.6f
        )
        
        /**
         * Multi-wave shimmer configuration.
         */
        val MultiWave = ShimmerConfig(
            type = ShimmerType.Wave,
            waveCount = 3,
            durationMillis = 1800,
            shimmerWidth = 0.25f
        )
        
        /**
         * Accessibility-friendly configuration with reduced motion.
         */
        val Accessible = ShimmerConfig(
            type = ShimmerType.Pulse,
            durationMillis = 2000,
            intensity = 0.5f,
            easing = ShimmerEasing.EaseInOut,
            repeatMode = ShimmerRepeatMode.Reverse,
            respectReducedMotion = true
        )
    }
}

/**
 * Extended color configuration for shimmer effects.
 *
 * Supports multi-color gradients for more complex shimmer appearances.
 *
 * @property baseColor The base/background color
 * @property highlightColor The primary highlight color
 * @property secondaryHighlight Optional secondary highlight for multi-color gradients
 * @property tertiaryHighlight Optional tertiary highlight for complex gradients
 */
@Stable
data class ShimmerColors(
    val baseColor: Color,
    val highlightColor: Color,
    val secondaryHighlight: Color? = null,
    val tertiaryHighlight: Color? = null
) {
    /**
     * Returns the list of colors for gradient creation.
     */
    fun toGradientColors(): List<Color> {
        return buildList {
            add(baseColor)
            add(highlightColor)
            secondaryHighlight?.let { add(it) }
            tertiaryHighlight?.let { add(it) }
            add(highlightColor)
            add(baseColor)
        }
    }
    
    companion object {
        /**
         * Creates shimmer colors from a single highlight color.
         */
        fun fromHighlight(baseColor: Color, highlightColor: Color) = ShimmerColors(
            baseColor = baseColor,
            highlightColor = highlightColor
        )
        
        /**
         * Creates a rainbow shimmer effect.
         */
        fun rainbow(baseColor: Color) = ShimmerColors(
            baseColor = baseColor,
            highlightColor = Color(0xFFFF6B6B),
            secondaryHighlight = Color(0xFF4ECDC4),
            tertiaryHighlight = Color(0xFFFFE66D)
        )
        
        /**
         * Creates a cool-toned shimmer effect.
         */
        fun cool(baseColor: Color) = ShimmerColors(
            baseColor = baseColor,
            highlightColor = Color(0xFF667EEA),
            secondaryHighlight = Color(0xFF64B5F6)
        )
        
        /**
         * Creates a warm-toned shimmer effect.
         */
        fun warm(baseColor: Color) = ShimmerColors(
            baseColor = baseColor,
            highlightColor = Color(0xFFFF8A65),
            secondaryHighlight = Color(0xFFFFD54F)
        )
    }
}
