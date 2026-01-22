package com.ebin.skeleton.accessibility

import android.content.Context
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * Accessibility utilities for skeleton and shimmer components.
 *
 * This module provides utilities for respecting user accessibility
 * preferences, particularly around motion and animations.
 */
object SkeletonAccessibility {
    
    /**
     * Checks if the user has enabled "Remove Animations" or "Reduce Motion"
     * in system accessibility settings.
     *
     * @param context Android context
     * @return true if animations should be reduced
     */
    fun isReduceMotionEnabled(context: Context): Boolean {
        return try {
            val animatorDuration = Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.ANIMATOR_DURATION_SCALE,
                1f
            )
            val transitionDuration = Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.TRANSITION_ANIMATION_SCALE,
                1f
            )
            val windowDuration = Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.WINDOW_ANIMATION_SCALE,
                1f
            )
            
            // If any animation scale is 0, user wants reduced motion
            animatorDuration == 0f || transitionDuration == 0f || windowDuration == 0f
        } catch (e: Exception) {
            false
        }
    }
}

/**
 * Remembers whether reduced motion is enabled on the device.
 *
 * This composable function checks system accessibility settings to determine
 * if the user prefers reduced motion. Use this to conditionally disable
 * or simplify shimmer animations.
 *
 * Example:
 * ```kotlin
 * val reduceMotion = rememberReduceMotionEnabled()
 * 
 * val config = if (reduceMotion) {
 *     ShimmerConfig.Accessible
 * } else {
 *     ShimmerConfig.Default
 * }
 * ```
 *
 * @return true if reduced motion is preferred
 */
@Composable
fun rememberReduceMotionEnabled(): Boolean {
    val context = LocalContext.current
    return remember {
        SkeletonAccessibility.isReduceMotionEnabled(context)
    }
}

/**
 * Provides a shimmer configuration that respects accessibility settings.
 *
 * If reduced motion is enabled, returns a simplified configuration.
 * Otherwise, returns the provided default configuration.
 *
 * Example:
 * ```kotlin
 * val config = rememberAccessibleShimmerConfig()
 * val shimmerState = rememberShimmerState(config = config)
 * ```
 *
 * @param defaultConfig Configuration to use when reduced motion is not enabled
 * @param accessibleConfig Configuration to use when reduced motion is enabled
 * @return Appropriate [ShimmerConfig] based on accessibility settings
 */
@Composable
fun rememberAccessibleShimmerConfig(
    defaultConfig: com.ebin.skeleton.shimmer.ShimmerConfig = com.ebin.skeleton.shimmer.ShimmerConfig.Default,
    accessibleConfig: com.ebin.skeleton.shimmer.ShimmerConfig = com.ebin.skeleton.shimmer.ShimmerConfig.Accessible
): com.ebin.skeleton.shimmer.ShimmerConfig {
    val reduceMotion = rememberReduceMotionEnabled()
    return remember(reduceMotion) {
        if (reduceMotion) accessibleConfig else defaultConfig
    }
}

/**
 * Content description for skeleton loading states.
 *
 * Use these with `Modifier.semantics` to provide meaningful
 * descriptions for screen readers.
 */
object SkeletonContentDescriptions {
    const val LOADING = "Loading content"
    const val LOADING_IMAGE = "Loading image"
    const val LOADING_TEXT = "Loading text"
    const val LOADING_LIST = "Loading list items"
    const val LOADING_PROFILE = "Loading profile information"
    const val LOADING_CARD = "Loading card content"
    
    /**
     * Creates a loading description for a specific item type.
     */
    fun loadingItem(itemType: String) = "Loading $itemType"
    
    /**
     * Creates a loading description with count.
     */
    fun loadingItems(count: Int, itemType: String) = "Loading $count ${itemType}s"
}
