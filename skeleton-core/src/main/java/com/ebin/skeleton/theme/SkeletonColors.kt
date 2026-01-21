package com.ebin.skeleton.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Defines the color scheme for skeleton components.
 *
 * @property baseColor The base/background color of skeleton elements
 * @property highlightColor The shimmer highlight color that animates across elements
 */
@Immutable
data class SkeletonColors(
    val baseColor: Color,
    val highlightColor: Color
)

/**
 * Default skeleton colors for light theme.
 * Uses subtle gray tones that work well with Material 3 surfaces.
 */
val LightSkeletonColors = SkeletonColors(
    baseColor = Color(0xFFE0E0E0),
    highlightColor = Color(0xFFF5F5F5)
)

/**
 * Default skeleton colors for dark theme.
 * Uses darker gray tones suitable for dark mode surfaces.
 */
val DarkSkeletonColors = SkeletonColors(
    baseColor = Color(0xFF3A3A3A),
    highlightColor = Color(0xFF4A4A4A)
)

/**
 * CompositionLocal for providing skeleton colors throughout the composition tree.
 */
val LocalSkeletonColors = staticCompositionLocalOf { LightSkeletonColors }

/**
 * Returns appropriate skeleton colors based on the current theme.
 *
 * This function automatically selects light or dark skeleton colors based on
 * the system theme setting, making it easy to create theme-aware skeleton UI.
 *
 * @return [SkeletonColors] appropriate for the current theme
 */
@Composable
@ReadOnlyComposable
fun skeletonColors(): SkeletonColors {
    return if (isSystemInDarkTheme()) DarkSkeletonColors else LightSkeletonColors
}

/**
 * Creates custom skeleton colors derived from Material 3 theme.
 *
 * This function creates skeleton colors that harmonize with the current
 * Material 3 color scheme, supporting Material You dynamic colors.
 *
 * @return [SkeletonColors] derived from Material 3 theme colors
 */
@Composable
@ReadOnlyComposable
fun materialSkeletonColors(): SkeletonColors {
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
    val surface = MaterialTheme.colorScheme.surface
    
    return SkeletonColors(
        baseColor = surfaceVariant,
        highlightColor = surface
    )
}

/**
 * Creates custom skeleton colors with specified base and highlight colors.
 *
 * @param baseColor The base/background color for skeleton elements
 * @param highlightColor The shimmer highlight color
 * @return Custom [SkeletonColors] instance
 */
fun customSkeletonColors(
    baseColor: Color,
    highlightColor: Color
): SkeletonColors = SkeletonColors(
    baseColor = baseColor,
    highlightColor = highlightColor
)
