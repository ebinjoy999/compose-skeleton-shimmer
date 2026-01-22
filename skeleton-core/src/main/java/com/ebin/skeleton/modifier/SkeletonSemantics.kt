package com.ebin.skeleton.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.ebin.skeleton.accessibility.SkeletonContentDescriptions

/**
 * Applies semantic information for accessibility to skeleton components.
 *
 * This modifier adds content descriptions for screen readers, making
 * skeleton loading states accessible to users with visual impairments.
 *
 * Example:
 * ```kotlin
 * SkeletonBox(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(100.dp)
 *         .skeletonSemantics("product image")
 * )
 * ```
 *
 * @param itemDescription Description of what content is loading
 * @return Modifier with semantic information applied
 */
fun Modifier.skeletonSemantics(
    itemDescription: String = "content"
): Modifier = this.semantics {
    contentDescription = SkeletonContentDescriptions.loadingItem(itemDescription)
}

/**
 * Applies semantic information for a list of loading items.
 *
 * Example:
 * ```kotlin
 * Column(
 *     modifier = Modifier.skeletonListSemantics(count = 5, itemType = "messages")
 * ) {
 *     repeat(5) { SkeletonListItem() }
 * }
 * ```
 *
 * @param count Number of items loading
 * @param itemType Type of items (e.g., "messages", "products")
 * @return Modifier with semantic information applied
 */
fun Modifier.skeletonListSemantics(
    count: Int,
    itemType: String = "items"
): Modifier = this.semantics {
    contentDescription = SkeletonContentDescriptions.loadingItems(count, itemType)
}

/**
 * Applies pre-defined skeleton semantics.
 *
 * @param type The type of skeleton content
 * @return Modifier with semantic information applied
 */
fun Modifier.skeletonSemantics(
    type: SkeletonSemanticType
): Modifier = this.semantics {
    contentDescription = when (type) {
        SkeletonSemanticType.Generic -> SkeletonContentDescriptions.LOADING
        SkeletonSemanticType.Image -> SkeletonContentDescriptions.LOADING_IMAGE
        SkeletonSemanticType.Text -> SkeletonContentDescriptions.LOADING_TEXT
        SkeletonSemanticType.List -> SkeletonContentDescriptions.LOADING_LIST
        SkeletonSemanticType.Profile -> SkeletonContentDescriptions.LOADING_PROFILE
        SkeletonSemanticType.Card -> SkeletonContentDescriptions.LOADING_CARD
    }
}

/**
 * Pre-defined semantic types for common skeleton use cases.
 */
enum class SkeletonSemanticType {
    Generic,
    Image,
    Text,
    List,
    Profile,
    Card
}
