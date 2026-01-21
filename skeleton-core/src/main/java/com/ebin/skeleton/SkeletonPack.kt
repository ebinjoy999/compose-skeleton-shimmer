/**
 * Compose Skeleton Pack - A lightweight, Compose-first Skeleton & Shimmer library
 *
 * This library provides easy-to-use skeleton loading components for Jetpack Compose,
 * featuring high-performance shimmer animations and Material 3 support.
 *
 * ## Quick Start
 *
 * ```kotlin
 * // Basic skeleton with automatic shimmer
 * SkeletonBox(
 *     modifier = Modifier.fillMaxWidth().height(100.dp),
 *     shape = RoundedCornerShape(12.dp)
 * )
 *
 * // Skeleton visibility controller
 * Skeleton(
 *     isLoading = isLoading,
 *     skeleton = { SkeletonCard() }
 * ) {
 *     ActualCard(data)
 * }
 *
 * // List skeleton items
 * LazyColumn {
 *     skeletonItems(isLoading = true, count = 5) {
 *         SkeletonListItem()
 *     }
 * }
 * ```
 *
 * ## Main Components
 *
 * - [SkeletonBox] - Rectangular skeleton placeholder
 * - [SkeletonCircle] - Circular skeleton placeholder
 * - [SkeletonLine] - Text line skeleton placeholder
 * - [SkeletonCard] - Complete card skeleton
 * - [SkeletonListItem] - List item skeleton
 * - [SkeletonProfile] - Profile header skeleton
 * - [SkeletonTile] - Grid tile skeleton
 *
 * ## Shimmer
 *
 * - [rememberShimmerState] - Create shimmer animation state
 * - [Modifier.shimmer] - Apply shimmer effect to any composable
 *
 * ## Visibility Control
 *
 * - [Skeleton] - Show skeleton or content based on loading state
 * - [SkeletonIfNull] - Show skeleton until data is available
 * - [SkeletonIfEmpty] - Show skeleton until list has items
 *
 * @see com.ebin.skeleton.skeleton
 * @see com.ebin.skeleton.shimmer
 * @see com.ebin.skeleton.modifier
 * @see com.ebin.skeleton.theme
 */
package com.ebin.skeleton
