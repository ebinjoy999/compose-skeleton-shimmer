package com.ebin.skeleton.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ebin.skeleton.shimmer.ShimmerConfig
import com.ebin.skeleton.shimmer.ShimmerState
import com.ebin.skeleton.shimmer.rememberShimmerState

/**
 * A card-style skeleton placeholder with shimmer animation.
 *
 * This component provides a complete card skeleton with customizable
 * image area, title lines, and optional description. Perfect for
 * representing loading states of content cards in feeds or lists.
 *
 * Example:
 * ```kotlin
 * SkeletonCard(
 *     modifier = Modifier.fillMaxWidth(),
 *     imageHeight = 180.dp,
 *     titleLines = 2,
 *     showDescription = true
 * )
 * ```
 *
 * @param modifier Modifier to be applied to the card
 * @param imageHeight Height of the image placeholder area
 * @param titleLines Number of title skeleton lines to show
 * @param showDescription Whether to show description skeleton lines
 * @param descriptionLines Number of description lines (if shown)
 * @param cornerRadius Corner radius for the card
 * @param shimmerState Optional shared shimmer state for synchronized animations
 * @param config Optional shimmer configuration (used when shimmerState is null)
 */
@Composable
fun SkeletonCard(
    modifier: Modifier = Modifier,
    imageHeight: Dp = 160.dp,
    titleLines: Int = 1,
    showDescription: Boolean = true,
    descriptionLines: Int = 2,
    cornerRadius: Dp = 12.dp,
    shimmerState: ShimmerState? = null,
    config: ShimmerConfig = ShimmerConfig.Default
) {
    val state = shimmerState ?: rememberShimmerState(config = config)
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Image placeholder
            SkeletonBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
                shape = RoundedCornerShape(
                    topStart = cornerRadius,
                    topEnd = cornerRadius,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                shimmerState = state
            )
            
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Title lines
                repeat(titleLines) { index ->
                    val widthFraction = if (index == titleLines - 1) 0.6f else 0.9f
                    SkeletonLine(
                        modifier = Modifier.fillMaxWidth(widthFraction),
                        height = 18.dp,
                        shimmerState = state
                    )
                }
                
                // Description lines
                if (showDescription) {
                    Spacer(modifier = Modifier.height(4.dp))
                    repeat(descriptionLines) { index ->
                        val widthFraction = when {
                            index == descriptionLines - 1 -> 0.5f
                            index % 2 == 0 -> 0.95f
                            else -> 0.85f
                        }
                        SkeletonLine(
                            modifier = Modifier.fillMaxWidth(widthFraction),
                            height = 14.dp,
                            shimmerState = state
                        )
                    }
                }
            }
        }
    }
}

/**
 * A list item skeleton placeholder with shimmer animation.
 *
 * This component provides a typical list item skeleton with leading
 * icon/avatar, title, subtitle, and optional trailing element.
 * Perfect for representing loading states in vertical lists.
 *
 * Example:
 * ```kotlin
 * SkeletonListItem(
 *     leadingSize = 48.dp,
 *     isLeadingCircle = true,
 *     showSubtitle = true
 * )
 * ```
 *
 * @param modifier Modifier to be applied to the list item
 * @param leadingSize Size of the leading element (avatar/icon)
 * @param isLeadingCircle Whether the leading element is circular
 * @param showSubtitle Whether to show a subtitle skeleton line
 * @param showTrailing Whether to show a trailing element skeleton
 * @param trailingWidth Width of the trailing element
 * @param shimmerState Optional shared shimmer state for synchronized animations
 * @param config Optional shimmer configuration (used when shimmerState is null)
 */
@Composable
fun SkeletonListItem(
    modifier: Modifier = Modifier,
    leadingSize: Dp = 48.dp,
    isLeadingCircle: Boolean = true,
    showSubtitle: Boolean = true,
    showTrailing: Boolean = false,
    trailingWidth: Dp = 60.dp,
    shimmerState: ShimmerState? = null,
    config: ShimmerConfig = ShimmerConfig.Default
) {
    val state = shimmerState ?: rememberShimmerState(config = config)
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading element (avatar/icon)
        if (isLeadingCircle) {
            SkeletonCircle(
                size = leadingSize,
                shimmerState = state
            )
        } else {
            SkeletonBox(
                modifier = Modifier.size(leadingSize),
                shape = RoundedCornerShape(8.dp),
                shimmerState = state
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // Text content
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Title
            SkeletonLine(
                modifier = Modifier.fillMaxWidth(0.7f),
                height = 16.dp,
                shimmerState = state
            )
            
            // Subtitle
            if (showSubtitle) {
                SkeletonLine(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    height = 14.dp,
                    shimmerState = state
                )
            }
        }
        
        // Trailing element
        if (showTrailing) {
            Spacer(modifier = Modifier.width(16.dp))
            SkeletonBox(
                modifier = Modifier
                    .width(trailingWidth)
                    .height(32.dp),
                shape = RoundedCornerShape(8.dp),
                shimmerState = state
            )
        }
    }
}

/**
 * A profile header skeleton placeholder with shimmer animation.
 *
 * This component provides a complete profile header skeleton with
 * avatar, name, subtitle, and optional action buttons. Perfect for
 * representing loading states of user profiles.
 *
 * Example:
 * ```kotlin
 * SkeletonProfile(
 *     avatarSize = 80.dp,
 *     showBio = true,
 *     actionButtonCount = 2
 * )
 * ```
 *
 * @param modifier Modifier to be applied to the profile skeleton
 * @param avatarSize Size of the avatar placeholder
 * @param showBio Whether to show bio/description lines
 * @param bioLines Number of bio lines to show
 * @param actionButtonCount Number of action button placeholders
 * @param shimmerState Optional shared shimmer state for synchronized animations
 * @param config Optional shimmer configuration (used when shimmerState is null)
 */
@Composable
fun SkeletonProfile(
    modifier: Modifier = Modifier,
    avatarSize: Dp = 80.dp,
    showBio: Boolean = true,
    bioLines: Int = 2,
    actionButtonCount: Int = 1,
    shimmerState: ShimmerState? = null,
    config: ShimmerConfig = ShimmerConfig.Default
) {
    val state = shimmerState ?: rememberShimmerState(config = config)
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar
        SkeletonCircle(
            size = avatarSize,
            shimmerState = state
        )
        
        // Name
        SkeletonLine(
            modifier = Modifier.width(140.dp),
            height = 20.dp,
            shimmerState = state
        )
        
        // Subtitle/handle
        SkeletonLine(
            modifier = Modifier.width(100.dp),
            height = 14.dp,
            shimmerState = state
        )
        
        // Bio
        if (showBio) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(bioLines) { index ->
                    val widthFraction = if (index == bioLines - 1) 0.6f else 0.85f
                    SkeletonLine(
                        modifier = Modifier.fillMaxWidth(widthFraction),
                        height = 14.dp,
                        shimmerState = state
                    )
                }
            }
        }
        
        // Action buttons
        if (actionButtonCount > 0) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(actionButtonCount) {
                    SkeletonBox(
                        modifier = Modifier
                            .width(100.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(20.dp),
                        shimmerState = state
                    )
                }
            }
        }
    }
}

/**
 * A grid tile skeleton placeholder with shimmer animation.
 *
 * This component provides a square or rectangular tile skeleton
 * suitable for grid layouts. Perfect for dashboard widgets or
 * image grids.
 *
 * Example:
 * ```kotlin
 * SkeletonTile(
 *     modifier = Modifier.aspectRatio(1f),
 *     showLabel = true
 * )
 * ```
 *
 * @param modifier Modifier to be applied to the tile
 * @param showLabel Whether to show a label skeleton at the bottom
 * @param cornerRadius Corner radius for the tile
 * @param shimmerState Optional shared shimmer state for synchronized animations
 * @param config Optional shimmer configuration (used when shimmerState is null)
 */
@Composable
fun SkeletonTile(
    modifier: Modifier = Modifier,
    showLabel: Boolean = false,
    cornerRadius: Dp = 12.dp,
    shimmerState: ShimmerState? = null,
    config: ShimmerConfig = ShimmerConfig.Default
) {
    val state = shimmerState ?: rememberShimmerState(config = config)
    
    Column(modifier = modifier) {
        SkeletonBox(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(cornerRadius),
            shimmerState = state
        )
        
        if (showLabel) {
            Spacer(modifier = Modifier.height(8.dp))
            SkeletonLine(
                modifier = Modifier.fillMaxWidth(0.7f),
                height = 14.dp,
                shimmerState = state
            )
        }
    }
}
