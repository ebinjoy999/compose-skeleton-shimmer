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
import com.ebin.skeleton.shimmer.ShimmerDirection
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
 * ```
 *
 * @param modifier Modifier to be applied to the skeleton
 * @param shape Shape of the skeleton (defaults to small rounded corners)
 * @param shimmerState Optional shared shimmer state for synchronized animations
 */
@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    shimmerState: ShimmerState? = null
) {
    val colors = LocalSkeletonColors.current
    val state = shimmerState ?: rememberShimmerState()
    
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
 * SkeletonCircle(
 *     size = 64.dp
 * )
 * ```
 *
 * @param size Diameter of the circle
 * @param modifier Modifier to be applied to the skeleton
 * @param shimmerState Optional shared shimmer state for synchronized animations
 */
@Composable
fun SkeletonCircle(
    size: Dp,
    modifier: Modifier = Modifier,
    shimmerState: ShimmerState? = null
) {
    val colors = LocalSkeletonColors.current
    val state = shimmerState ?: rememberShimmerState()
    
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
 * ```
 *
 * @param modifier Modifier to be applied to the skeleton
 * @param height Height of the line (typically matches text line height)
 * @param cornerRadius Corner radius for rounded ends
 * @param shimmerState Optional shared shimmer state for synchronized animations
 */
@Composable
fun SkeletonLine(
    modifier: Modifier = Modifier,
    height: Dp = 14.dp,
    cornerRadius: Dp = 4.dp,
    shimmerState: ShimmerState? = null
) {
    val colors = LocalSkeletonColors.current
    val state = shimmerState ?: rememberShimmerState()
    
    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(colors.baseColor)
            .shimmer(state)
    )
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
