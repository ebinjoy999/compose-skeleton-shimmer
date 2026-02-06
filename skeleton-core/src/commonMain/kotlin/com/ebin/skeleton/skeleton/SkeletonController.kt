package com.ebin.skeleton.skeleton

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Transition style for switching between skeleton and content.
 */
enum class SkeletonTransition {
    /**
     * No animation - immediate switch
     */
    None,
    
    /**
     * Simple crossfade transition
     */
    Crossfade,
    
    /**
     * Animated content with fade in/out
     */
    Animated
}

/**
 * A container that shows skeleton or real content based on loading state.
 *
 * This composable provides a clean API for switching between skeleton
 * placeholders and actual content with optional transitions.
 *
 * Example:
 * ```kotlin
 * Skeleton(
 *     isLoading = viewModel.isLoading,
 *     skeleton = { SkeletonCard() }
 * ) {
 *     ActualCard(data = viewModel.data)
 * }
 * ```
 *
 * @param isLoading Whether to show the skeleton (true) or content (false)
 * @param modifier Modifier to be applied to the container
 * @param transition Transition style when switching states
 * @param transitionDurationMs Duration of the transition animation
 * @param skeleton Composable function that renders the skeleton placeholder
 * @param content Composable function that renders the actual content
 */
@Composable
fun Skeleton(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    transition: SkeletonTransition = SkeletonTransition.Crossfade,
    transitionDurationMs: Int = 300,
    skeleton: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    when (transition) {
        SkeletonTransition.None -> {
            if (isLoading) {
                skeleton()
            } else {
                content()
            }
        }
        
        SkeletonTransition.Crossfade -> {
            Crossfade(
                targetState = isLoading,
                modifier = modifier,
                animationSpec = tween(transitionDurationMs),
                label = "skeleton_crossfade"
            ) { loading ->
                if (loading) {
                    skeleton()
                } else {
                    content()
                }
            }
        }
        
        SkeletonTransition.Animated -> {
            AnimatedContent(
                targetState = isLoading,
                modifier = modifier,
                transitionSpec = {
                    fadeIn(animationSpec = tween(transitionDurationMs)) togetherWith
                        fadeOut(animationSpec = tween(transitionDurationMs))
                },
                label = "skeleton_animated"
            ) { loading ->
                if (loading) {
                    skeleton()
                } else {
                    content()
                }
            }
        }
    }
}

/**
 * Shows skeleton when loading, content when loaded, with visibility animation.
 *
 * Unlike [Skeleton], this variant uses AnimatedVisibility which preserves
 * the skeleton in the composition tree during transition, potentially
 * providing smoother animations at the cost of slightly more memory.
 *
 * Example:
 * ```kotlin
 * SkeletonVisibility(
 *     isLoading = isLoading,
 *     skeleton = { SkeletonListItem() }
 * ) {
 *     ListItem(data = itemData)
 * }
 * ```
 *
 * @param isLoading Whether to show the skeleton (true) or content (false)
 * @param modifier Modifier to be applied to the container
 * @param transitionDurationMs Duration of the visibility animation
 * @param skeleton Composable function that renders the skeleton placeholder
 * @param content Composable function that renders the actual content
 */
@Composable
fun SkeletonVisibility(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    transitionDurationMs: Int = 300,
    skeleton: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isLoading,
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(transitionDurationMs)),
        exit = fadeOut(animationSpec = tween(transitionDurationMs))
    ) {
        skeleton()
    }
    
    AnimatedVisibility(
        visible = !isLoading,
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(transitionDurationMs)),
        exit = fadeOut(animationSpec = tween(transitionDurationMs))
    ) {
        content()
    }
}

/**
 * A skeleton container that always shows skeleton until content is ready.
 *
 * This is a convenience wrapper that automatically manages the loading state
 * based on whether the data is null or not.
 *
 * Example:
 * ```kotlin
 * SkeletonIfNull(
 *     data = user,
 *     skeleton = { SkeletonProfile() }
 * ) { user ->
 *     UserProfile(user = user)
 * }
 * ```
 *
 * @param data The nullable data to check
 * @param modifier Modifier to be applied to the container
 * @param transition Transition style when data becomes available
 * @param skeleton Composable function that renders the skeleton placeholder
 * @param content Composable function that renders content with non-null data
 */
@Composable
fun <T : Any> SkeletonIfNull(
    data: T?,
    modifier: Modifier = Modifier,
    transition: SkeletonTransition = SkeletonTransition.Crossfade,
    skeleton: @Composable () -> Unit,
    content: @Composable (T) -> Unit
) {
    Skeleton(
        isLoading = data == null,
        modifier = modifier,
        transition = transition,
        skeleton = skeleton
    ) {
        data?.let { content(it) }
    }
}

/**
 * A skeleton container that shows skeleton when list is empty.
 *
 * This is useful for list-based screens where you want to show
 * skeleton items until data is loaded.
 *
 * Example:
 * ```kotlin
 * SkeletonIfEmpty(
 *     data = items,
 *     skeleton = { repeat(5) { SkeletonListItem() } }
 * ) { items ->
 *     items.forEach { item -> ListItem(item) }
 * }
 * ```
 *
 * @param data The list to check
 * @param modifier Modifier to be applied to the container
 * @param transition Transition style when data becomes available
 * @param skeleton Composable function that renders the skeleton placeholder
 * @param content Composable function that renders content with non-empty list
 */
@Composable
fun <T> SkeletonIfEmpty(
    data: List<T>,
    modifier: Modifier = Modifier,
    transition: SkeletonTransition = SkeletonTransition.Crossfade,
    skeleton: @Composable () -> Unit,
    content: @Composable (List<T>) -> Unit
) {
    Skeleton(
        isLoading = data.isEmpty(),
        modifier = modifier,
        transition = transition,
        skeleton = skeleton
    ) {
        content(data)
    }
}
