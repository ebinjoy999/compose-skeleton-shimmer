package com.ebin.skeleton.shimmer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * Creates a shimmer state that automatically pauses when the composable
 * is not visible (lifecycle-aware).
 *
 * This is useful for battery optimization - shimmer animations will
 * automatically pause when the screen is not visible.
 *
 * This is an Android-only API that uses AndroidX Lifecycle.
 *
 * Example:
 * ```kotlin
 * val shimmerState = rememberLifecycleAwareShimmerState()
 * 
 * SkeletonCard(shimmerState = shimmerState)
 * ```
 *
 * @param config Configuration for the shimmer animation
 * @param colors Custom colors for the shimmer effect
 * @param pauseOnBackground Whether to pause when app goes to background
 * @return A lifecycle-aware [ShimmerState] instance
 */
@Composable
fun rememberLifecycleAwareShimmerState(
    config: ShimmerConfig = ShimmerConfig.Default,
    colors: ShimmerColors? = null,
    pauseOnBackground: Boolean = true
): ShimmerState {
    val controller = rememberShimmerController()
    val lifecycleOwner = LocalLifecycleOwner.current
    
    if (pauseOnBackground) {
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> controller.pause()
                    Lifecycle.Event.ON_RESUME -> controller.resume()
                    else -> { /* no-op */ }
                }
            }
            
            lifecycleOwner.lifecycle.addObserver(observer)
            
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
    
    return rememberShimmerState(
        config = config,
        colors = colors,
        controller = controller
    )
}
