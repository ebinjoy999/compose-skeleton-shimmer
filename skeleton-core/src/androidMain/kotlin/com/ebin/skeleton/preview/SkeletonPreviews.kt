package com.ebin.skeleton.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ebin.skeleton.skeleton.SkeletonBox
import com.ebin.skeleton.skeleton.SkeletonCard
import com.ebin.skeleton.skeleton.SkeletonCircle
import com.ebin.skeleton.skeleton.SkeletonLine
import com.ebin.skeleton.skeleton.SkeletonListItem
import com.ebin.skeleton.skeleton.SkeletonParagraph
import com.ebin.skeleton.skeleton.SkeletonProfile

/**
 * Android Preview Composables for Skeleton Components.
 * These are kept in androidMain as @Preview is Android-specific.
 */

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonBoxPreview() {
    SkeletonBox(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonCirclePreview() {
    SkeletonCircle(
        size = 64.dp,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonLinePreview() {
    SkeletonLine(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(16.dp),
        height = 16.dp
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonParagraphPreview() {
    SkeletonParagraph(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        lines = 4
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonCardPreview() {
    SkeletonCard(
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonListItemPreview() {
    Column {
        repeat(3) {
            SkeletonListItem()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SkeletonProfilePreview() {
    SkeletonProfile(
        actionButtonCount = 2
    )
}
