package com.ebin.skeleton.accessibility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.ebin.skeleton.shimmer.ShimmerConfig

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

/**
 * Expected function to check if reduced motion is enabled on the platform.
 * Returns false by default for platforms that don't support this check.
 */
expect fun isReduceMotionEnabled(): Boolean

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
    return remember { isReduceMotionEnabled() }
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
    defaultConfig: ShimmerConfig = ShimmerConfig.Default,
    accessibleConfig: ShimmerConfig = ShimmerConfig.Accessible
): ShimmerConfig {
    val reduceMotion = rememberReduceMotionEnabled()
    return remember(reduceMotion) {
        if (reduceMotion) accessibleConfig else defaultConfig
    }
}
