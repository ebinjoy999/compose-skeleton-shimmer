package com.ebin.skeleton.accessibility

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ebin.skeleton.shimmer.ShimmerConfig

/**
 * Android-specific composable that checks reduced motion with proper Context access.
 *
 * This composable function checks system accessibility settings to determine
 * if the user prefers reduced motion. Use this to conditionally disable
 * or simplify shimmer animations.
 *
 * Example:
 * ```kotlin
 * val reduceMotion = rememberReduceMotionEnabledWithContext()
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
fun rememberReduceMotionEnabledWithContext(): Boolean {
    val context = LocalContext.current
    return remember {
        AndroidAccessibility.isReduceMotionEnabled(context)
    }
}

/**
 * Provides a shimmer configuration that respects Android accessibility settings.
 *
 * If reduced motion is enabled, returns a simplified configuration.
 * Otherwise, returns the provided default configuration.
 *
 * This is the Android-specific version that properly accesses Context.
 *
 * Example:
 * ```kotlin
 * val config = rememberAccessibleShimmerConfigAndroid()
 * val shimmerState = rememberShimmerState(config = config)
 * ```
 *
 * @param defaultConfig Configuration to use when reduced motion is not enabled
 * @param accessibleConfig Configuration to use when reduced motion is enabled
 * @return Appropriate [ShimmerConfig] based on accessibility settings
 */
@Composable
fun rememberAccessibleShimmerConfigAndroid(
    defaultConfig: ShimmerConfig = ShimmerConfig.Default,
    accessibleConfig: ShimmerConfig = ShimmerConfig.Accessible
): ShimmerConfig {
    val reduceMotion = rememberReduceMotionEnabledWithContext()
    return remember(reduceMotion) {
        if (reduceMotion) accessibleConfig else defaultConfig
    }
}
