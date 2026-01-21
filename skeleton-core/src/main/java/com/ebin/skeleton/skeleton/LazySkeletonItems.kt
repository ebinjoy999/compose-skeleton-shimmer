package com.ebin.skeleton.skeleton

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import com.ebin.skeleton.shimmer.ShimmerState

/**
 * Extension function for LazyListScope to add skeleton items.
 *
 * This function conditionally adds skeleton placeholder items to a LazyColumn
 * or LazyRow based on the loading state.
 *
 * Example:
 * ```kotlin
 * LazyColumn {
 *     skeletonItems(
 *         isLoading = isLoading,
 *         count = 5
 *     ) {
 *         SkeletonListItem()
 *     }
 *
 *     items(actualItems) { item ->
 *         ListItem(item)
 *     }
 * }
 * ```
 *
 * @param isLoading Whether to show skeleton items
 * @param count Number of skeleton items to display
 * @param key Optional key factory for skeleton items
 * @param contentType Content type for efficient recycling
 * @param itemContent Composable content for each skeleton item
 */
fun LazyListScope.skeletonItems(
    isLoading: Boolean,
    count: Int,
    key: ((index: Int) -> Any)? = { "skeleton_$it" },
    contentType: (index: Int) -> Any? = { "skeleton" },
    itemContent: @Composable LazyItemScope.(index: Int) -> Unit
) {
    if (isLoading) {
        items(
            count = count,
            key = key,
            contentType = contentType
        ) { index ->
            itemContent(index)
        }
    }
}

/**
 * Extension function for LazyListScope to add skeleton items with shared shimmer.
 *
 * This variant accepts a ShimmerState to synchronize shimmer animations
 * across all skeleton items.
 *
 * Example:
 * ```kotlin
 * val shimmerState = rememberShimmerState()
 *
 * LazyColumn {
 *     skeletonItems(
 *         isLoading = isLoading,
 *         count = 5,
 *         shimmerState = shimmerState
 *     ) { index, state ->
 *         SkeletonListItem(shimmerState = state)
 *     }
 * }
 * ```
 *
 * @param isLoading Whether to show skeleton items
 * @param count Number of skeleton items to display
 * @param shimmerState Shared shimmer state for synchronized animations
 * @param key Optional key factory for skeleton items
 * @param contentType Content type for efficient recycling
 * @param itemContent Composable content for each skeleton item with shimmer state
 */
fun LazyListScope.skeletonItems(
    isLoading: Boolean,
    count: Int,
    shimmerState: ShimmerState,
    key: ((index: Int) -> Any)? = { "skeleton_$it" },
    contentType: (index: Int) -> Any? = { "skeleton" },
    itemContent: @Composable LazyItemScope.(index: Int, ShimmerState) -> Unit
) {
    if (isLoading) {
        items(
            count = count,
            key = key,
            contentType = contentType
        ) { index ->
            itemContent(index, shimmerState)
        }
    }
}

/**
 * Extension function for LazyListScope that conditionally shows skeleton or real items.
 *
 * This is a convenience function that combines skeleton and real items in one call.
 *
 * Example:
 * ```kotlin
 * LazyColumn {
 *     skeletonItemsIndexed(
 *         isLoading = isLoading,
 *         data = items,
 *         skeletonCount = 5,
 *         skeleton = { SkeletonListItem() },
 *         content = { index, item -> ListItem(item) }
 *     )
 * }
 * ```
 *
 * @param isLoading Whether to show skeleton items
 * @param data The actual data list to display when not loading
 * @param skeletonCount Number of skeleton items to show when loading
 * @param skeleton Composable for skeleton items
 * @param content Composable for real content items
 */
fun <T> LazyListScope.skeletonItemsIndexed(
    isLoading: Boolean,
    data: List<T>,
    skeletonCount: Int,
    skeleton: @Composable LazyItemScope.() -> Unit,
    content: @Composable LazyItemScope.(index: Int, item: T) -> Unit
) {
    if (isLoading) {
        items(
            count = skeletonCount,
            key = { "skeleton_$it" },
            contentType = { "skeleton" }
        ) {
            skeleton()
        }
    } else {
        items(
            count = data.size,
            key = { it },
            contentType = { "content" }
        ) { index ->
            content(index, data[index])
        }
    }
}

/**
 * Extension function for LazyGridScope to add skeleton items.
 *
 * This function conditionally adds skeleton placeholder items to a LazyVerticalGrid
 * or LazyHorizontalGrid based on the loading state.
 *
 * Example:
 * ```kotlin
 * LazyVerticalGrid(columns = GridCells.Fixed(2)) {
 *     skeletonGridItems(
 *         isLoading = isLoading,
 *         count = 6
 *     ) {
 *         SkeletonTile(
 *             modifier = Modifier.aspectRatio(1f)
 *         )
 *     }
 * }
 * ```
 *
 * @param isLoading Whether to show skeleton items
 * @param count Number of skeleton items to display
 * @param key Optional key factory for skeleton items
 * @param contentType Content type for efficient recycling
 * @param itemContent Composable content for each skeleton item
 */
fun LazyGridScope.skeletonGridItems(
    isLoading: Boolean,
    count: Int,
    key: ((index: Int) -> Any)? = { "skeleton_grid_$it" },
    contentType: (index: Int) -> Any? = { "skeleton" },
    itemContent: @Composable LazyGridItemScope.(index: Int) -> Unit
) {
    if (isLoading) {
        items(
            count = count,
            key = key,
            contentType = contentType
        ) { index ->
            itemContent(index)
        }
    }
}

/**
 * Extension function for LazyGridScope to add skeleton items with shared shimmer.
 *
 * This variant accepts a ShimmerState to synchronize shimmer animations
 * across all skeleton items in the grid.
 *
 * @param isLoading Whether to show skeleton items
 * @param count Number of skeleton items to display
 * @param shimmerState Shared shimmer state for synchronized animations
 * @param key Optional key factory for skeleton items
 * @param contentType Content type for efficient recycling
 * @param itemContent Composable content for each skeleton item with shimmer state
 */
fun LazyGridScope.skeletonGridItems(
    isLoading: Boolean,
    count: Int,
    shimmerState: ShimmerState,
    key: ((index: Int) -> Any)? = { "skeleton_grid_$it" },
    contentType: (index: Int) -> Any? = { "skeleton" },
    itemContent: @Composable LazyGridItemScope.(index: Int, ShimmerState) -> Unit
) {
    if (isLoading) {
        items(
            count = count,
            key = key,
            contentType = contentType
        ) { index ->
            itemContent(index, shimmerState)
        }
    }
}

/**
 * Extension function for LazyGridScope that conditionally shows skeleton or real items.
 *
 * @param isLoading Whether to show skeleton items
 * @param data The actual data list to display when not loading
 * @param skeletonCount Number of skeleton items to show when loading
 * @param skeleton Composable for skeleton items
 * @param content Composable for real content items
 */
fun <T> LazyGridScope.skeletonGridItemsIndexed(
    isLoading: Boolean,
    data: List<T>,
    skeletonCount: Int,
    skeleton: @Composable LazyGridItemScope.() -> Unit,
    content: @Composable LazyGridItemScope.(index: Int, item: T) -> Unit
) {
    if (isLoading) {
        items(
            count = skeletonCount,
            key = { "skeleton_grid_$it" },
            contentType = { "skeleton" }
        ) {
            skeleton()
        }
    } else {
        items(
            count = data.size,
            key = { it },
            contentType = { "content" }
        ) { index ->
            content(index, data[index])
        }
    }
}
