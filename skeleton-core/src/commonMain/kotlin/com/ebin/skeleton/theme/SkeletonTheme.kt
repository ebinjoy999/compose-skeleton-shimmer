package com.ebin.skeleton.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Provides skeleton theming to the composition tree.
 *
 * Use this composable at the root of your skeleton UI to provide custom
 * skeleton colors throughout the component tree.
 *
 * Example:
 * ```kotlin
 * SkeletonTheme(colors = customSkeletonColors(...)) {
 *     SkeletonCard()
 * }
 * ```
 *
 * @param colors The [SkeletonColors] to use for skeleton components
 * @param content The composable content that will receive the skeleton theme
 */
@Composable
fun SkeletonTheme(
    colors: SkeletonColors = skeletonColors(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalSkeletonColors provides colors
    ) {
        content()
    }
}
