package com.ebin.skeleton.shimmer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

/**
 * Callback interface for shimmer animation events.
 */
interface ShimmerAnimationCallback {
    /**
     * Called when a shimmer animation cycle starts.
     */
    fun onAnimationStart() {}
    
    /**
     * Called when a shimmer animation cycle completes.
     */
    fun onAnimationEnd() {}
    
    /**
     * Called when a shimmer animation cycle repeats.
     * @param iteration The current iteration number (0-indexed)
     */
    fun onAnimationRepeat(iteration: Int) {}
}

/**
 * Creates a shimmer state that automatically pauses when the composable
 * is not visible (lifecycle-aware).
 *
 * This is useful for battery optimization - shimmer animations will
 * automatically pause when the screen is not visible.
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

/**
 * Creates a shimmer state with animation callbacks.
 *
 * Useful for triggering side effects or analytics when shimmer animations
 * reach certain points.
 *
 * Example:
 * ```kotlin
 * val shimmerState = rememberShimmerStateWithCallbacks(
 *     callback = object : ShimmerAnimationCallback {
 *         override fun onAnimationRepeat(iteration: Int) {
 *             println("Shimmer repeated $iteration times")
 *         }
 *     }
 * )
 * ```
 *
 * @param config Configuration for the shimmer animation
 * @param colors Custom colors for the shimmer effect
 * @param callback Callback for animation events
 * @return A [ShimmerState] instance with callbacks
 */
@Composable
fun rememberShimmerStateWithCallbacks(
    config: ShimmerConfig = ShimmerConfig.Default,
    colors: ShimmerColors? = null,
    callback: ShimmerAnimationCallback
): ShimmerState {
    val shimmerState = rememberShimmerState(config = config, colors = colors)
    var lastProgress by remember { mutableStateOf(0f) }
    var iterationCount by remember { mutableIntStateOf(0) }
    var hasStarted by remember { mutableStateOf(false) }
    
    LaunchedEffect(shimmerState.progress) {
        val currentProgress = shimmerState.progress
        
        // Detect animation start
        if (!hasStarted && currentProgress > 0f) {
            hasStarted = true
            callback.onAnimationStart()
        }
        
        // Detect animation repeat (progress resets to near 0)
        if (lastProgress > 0.9f && currentProgress < 0.1f) {
            callback.onAnimationEnd()
            iterationCount++
            callback.onAnimationRepeat(iterationCount)
            callback.onAnimationStart()
        }
        
        lastProgress = currentProgress
    }
    
    return shimmerState
}

/**
 * Creates a shimmer state with a maximum number of iterations.
 *
 * After the specified number of cycles, the shimmer will automatically stop.
 * Useful for one-time loading indicators that shouldn't run indefinitely.
 *
 * Example:
 * ```kotlin
 * val shimmerState = rememberLimitedShimmerState(
 *     maxIterations = 5,
 *     onComplete = { /* loading timeout */ }
 * )
 * ```
 *
 * @param config Configuration for the shimmer animation
 * @param colors Custom colors for the shimmer effect
 * @param maxIterations Maximum number of animation cycles
 * @param onComplete Callback when max iterations is reached
 * @return A [ShimmerState] that stops after max iterations
 */
@Composable
fun rememberLimitedShimmerState(
    config: ShimmerConfig = ShimmerConfig.Default,
    colors: ShimmerColors? = null,
    maxIterations: Int = 10,
    onComplete: (() -> Unit)? = null
): ShimmerState {
    val controller = rememberShimmerController()
    var iterationCount by remember { mutableIntStateOf(0) }
    var lastProgress by remember { mutableStateOf(0f) }
    
    val shimmerState = rememberShimmerState(
        config = config,
        colors = colors,
        controller = controller
    )
    
    LaunchedEffect(shimmerState.progress) {
        val currentProgress = shimmerState.progress
        
        // Detect iteration completion
        if (lastProgress > 0.9f && currentProgress < 0.1f) {
            iterationCount++
            
            if (iterationCount >= maxIterations) {
                controller.pause()
                onComplete?.invoke()
            }
        }
        
        lastProgress = currentProgress
    }
    
    return shimmerState
}

/**
 * Creates staggered shimmer states for list items.
 *
 * Each item will start its shimmer animation with a delay, creating
 * a cascading effect.
 *
 * Example:
 * ```kotlin
 * val shimmerStates = rememberStaggeredShimmerStates(
 *     count = 5,
 *     staggerDelayMillis = 100
 * )
 * 
 * shimmerStates.forEachIndexed { index, state ->
 *     SkeletonListItem(shimmerState = state)
 * }
 * ```
 *
 * @param count Number of shimmer states to create
 * @param config Base configuration for the shimmer animation
 * @param colors Custom colors for the shimmer effect
 * @param staggerDelayMillis Delay between each item's animation start
 * @return List of staggered [ShimmerState] instances
 */
@Composable
fun rememberStaggeredShimmerStates(
    count: Int,
    config: ShimmerConfig = ShimmerConfig.Default,
    colors: ShimmerColors? = null,
    staggerDelayMillis: Int = 100
): List<ShimmerState> {
    val staggeredConfig = config.copy(staggerDelayMillis = staggerDelayMillis)
    
    return remember(count, config, colors, staggerDelayMillis) {
        List(count) { index -> index }
    }.map { index ->
        rememberShimmerState(
            config = staggeredConfig,
            colors = colors,
            staggerIndex = index
        )
    }
}
