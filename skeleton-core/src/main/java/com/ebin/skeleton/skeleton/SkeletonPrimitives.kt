package com.ebin.skeleton.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ebin.skeleton.modifier.shimmer
import com.ebin.skeleton.shimmer.ShimmerConfig
import com.ebin.skeleton.shimmer.ShimmerState
import com.ebin.skeleton.shimmer.rememberShimmerState
import com.ebin.skeleton.theme.LocalSkeletonColors

/**
 * A rectangular skeleton placeholder with shimmer animation.
 *
 * Use this component to represent rectangular content areas during loading,
 * such as images, buttons, or generic content blocks.
 *
 * Example:
 * ```kotlin
 * SkeletonBox(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(120.dp),
 *     shape = RoundedCornerShape(12.dp)
 * )
 * 
 * // With custom shimmer config
 * SkeletonBox(
 *     modifier = Modifier.size(100.dp),
 *     config = ShimmerConfig.Pulse
 * )
 * ```
 *
 * @param modifier Modifier to be applied to the skeleton
 * @param shape Shape of the skeleton (defaults to small rounded corners)
 * @param shimmerState Optional shared shimmer state for synchronized animations
 * @param config Optional shimmer configuration (used when shimmerState is null)
 */
@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    shimmerState: ShimmerState? = null,
    config: ShimmerConfig = ShimmerConfig.Default
) {
    val colors = LocalSkeletonColors.current
    val state = shimmerState ?: rememberShimmerState(config = config)
    
    Box(
        modifier = modifier
            .clip(shape)
            .background(colors.baseColor)
            .shimmer(state)
    )
}

/**
 * A circular skeleton placeholder with shimmer animation.
 *
 * Use this component to represent circular content during loading,
 * such as avatars, profile pictures, or circular icons.
 *
 * Example:
 * ```kotlin
 * SkeletonCircle(size = 64.dp)
 * 
 * // With radial shimmer effect
 * SkeletonCircle(
 *     size = 80.dp,
 *     config = ShimmerConfig.Spotlight
 * )
 * ```
 *
 * @param size Diameter of the circle
 * @param modifier Modifier to be applied to the skeleton
 * @param shimmerState Optional shared shimmer state for synchronized animations
 * @param config Optional shimmer configuration (used when shimmerState is null)
 */
@Composable
fun SkeletonCircle(
    size: Dp,
    modifier: Modifier = Modifier,
    shimmerState: ShimmerState? = null,
    config: ShimmerConfig = ShimmerConfig.Default
) {
    val colors = LocalSkeletonColors.current
    val state = shimmerState ?: rememberShimmerState(config = config)
    
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(colors.baseColor)
            .shimmer(state)
    )
}

/**
 * A text line skeleton placeholder with shimmer animation.
 *
 * Use this component to represent text lines during loading.
 * It creates a pill-shaped skeleton ideal for text placeholders.
 *
 * Example:
 * ```kotlin
 * SkeletonLine(
 *     modifier = Modifier.fillMaxWidth(0.8f),
 *     height = 16.dp
 * )
 * 
 * // With subtle shimmer
 * SkeletonLine(
 *     modifier = Modifier.fillMaxWidth(),
 *     config = ShimmerConfig.Subtle
 * )
 * ```
 *
 * @param modifier Modifier to be applied to the skeleton
 * @param height Height of the line (typically matches text line height)
 * @param cornerRadius Corner radius for rounded ends
 * @param shimmerState Optional shared shimmer state for synchronized animations
 * @param config Optional shimmer configuration (used when shimmerState is null)
 */
@Composable
fun SkeletonLine(
    modifier: Modifier = Modifier,
    height: Dp = 14.dp,
    cornerRadius: Dp = 4.dp,
    shimmerState: ShimmerState? = null,
    config: ShimmerConfig = ShimmerConfig.Default
) {
    val colors = LocalSkeletonColors.current
    val state = shimmerState ?: rememberShimmerState(config = config)
    
    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(colors.baseColor)
            .shimmer(state)
    )
}

/**
 * A paragraph skeleton with multiple lines.
 *
 * Creates multiple skeleton lines with varying widths to simulate
 * a paragraph of text.
 *
 * Example:
 * ```kotlin
 * SkeletonParagraph(
 *     lines = 4,
 *     lastLineWidth = 0.6f
 * )
 * ```
 *
 * @param modifier Modifier to be applied to the container
 * @param lines Number of text lines to display
 * @param lineHeight Height of each line
 * @param lineSpacing Spacing between lines
 * @param lastLineWidth Width fraction for the last line (0.0 to 1.0)
 * @param shimmerState Optional shared shimmer state
 * @param config Optional shimmer configuration
 */
@Composable
fun SkeletonParagraph(
    modifier: Modifier = Modifier,
    lines: Int = 3,
    lineHeight: Dp = 14.dp,
    lineSpacing: Dp = 8.dp,
    lastLineWidth: Float = 0.7f,
    shimmerState: ShimmerState? = null,
    config: ShimmerConfig = ShimmerConfig.Default
) {
    val state = shimmerState ?: rememberShimmerState(config = config)
    
    androidx.compose.foundation.layout.Column(
        modifier = modifier,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(lineSpacing)
    ) {
        repeat(lines) { index ->
            val widthFraction = if (index == lines - 1) lastLineWidth else 1f
            SkeletonLine(
                modifier = Modifier.fillMaxWidth(widthFraction),
                height = lineHeight,
                shimmerState = state
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonBoxPreview() {
    SkeletonBox(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonCirclePreview() {
    SkeletonCircle(size = 64.dp)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonLinePreview() {
    SkeletonLine(
        modifier = Modifier.fillMaxWidth(0.7f),
        height = 16.dp
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonParagraphPreview() {
    SkeletonParagraph(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        lines = 4
    )
}
