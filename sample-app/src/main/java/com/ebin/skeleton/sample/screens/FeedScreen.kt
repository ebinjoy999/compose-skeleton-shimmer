package com.ebin.skeleton.sample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ebin.skeleton.sample.components.FeedPostCard
import com.ebin.skeleton.sample.data.FeedPost
import com.ebin.skeleton.sample.data.SampleData
import com.ebin.skeleton.shimmer.rememberShimmerState
import com.ebin.skeleton.skeleton.Skeleton
import com.ebin.skeleton.skeleton.SkeletonCard
import com.ebin.skeleton.skeleton.skeletonItems
import kotlinx.coroutines.delay

/**
 * Feed Screen - Demonstrates skeleton loading in a LazyColumn of cards.
 *
 * Features:
 * - LazyColumn with skeleton items during loading
 * - Synchronized shimmer animation across all skeleton cards
 * - Smooth crossfade transition to real content
 * - Pull-to-refresh style reload button
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen() {
    var isLoading by remember { mutableStateOf(true) }
    var posts by remember { mutableStateOf<List<FeedPost>>(emptyList()) }
    
    // Simulate loading
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2500)
            posts = SampleData.feedPosts
            isLoading = false
        }
    }
    
    // Shared shimmer state for synchronized animation
    val shimmerState = rememberShimmerState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Feed") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    posts = emptyList()
                    isLoading = true
                }
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reload")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Skeleton items when loading
            skeletonItems(
                isLoading = isLoading,
                count = 5,
                shimmerState = shimmerState
            ) { _, state ->
                SkeletonCard(
                    modifier = Modifier.fillMaxWidth(),
                    imageHeight = 160.dp,
                    titleLines = 1,
                    showDescription = true,
                    descriptionLines = 2,
                    shimmerState = state
                )
            }
            
            // Real content when loaded
            if (!isLoading) {
                items(
                    items = posts,
                    key = { it.id }
                ) { post ->
                    FeedPostCard(post = post)
                }
            }
        }
    }
}
