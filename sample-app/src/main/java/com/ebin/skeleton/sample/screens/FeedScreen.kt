package com.ebin.skeleton.sample.screens

import androidx.compose.foundation.layout.Arrangement
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
import com.ebin.skeleton.shimmer.ShimmerConfig
import com.ebin.skeleton.shimmer.ShimmerDropOff
import com.ebin.skeleton.shimmer.ShimmerEasing
import com.ebin.skeleton.shimmer.rememberShimmerController
import com.ebin.skeleton.shimmer.rememberShimmerState
import com.ebin.skeleton.skeleton.Skeleton
import com.ebin.skeleton.skeleton.SkeletonCard
import com.ebin.skeleton.skeleton.SkeletonTransition
import com.ebin.skeleton.skeleton.skeletonItems
import kotlinx.coroutines.delay

/**
 * Feed Screen - Demonstrates advanced skeleton loading features.
 *
 * Features showcased:
 * - LazyColumn with skeleton items during loading
 * - **Staggered shimmer animation** with delay between items
 * - **Soft drop-off** gradient for smooth shimmer edges
 * - **EaseInOut easing** for natural animation feel
 * - **Shimmer controller** for pause/resume control
 * - Crossfade transition to real content
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen() {
    var isLoading by remember { mutableStateOf(true) }
    var posts by remember { mutableStateOf<List<FeedPost>>(emptyList()) }
    
    // Shimmer controller for global control
    val shimmerController = rememberShimmerController()
    
    // Advanced shimmer configuration with staggered delay
    val shimmerConfig = ShimmerConfig(
        durationMillis = 1200,
        delayMillis = 100,
        shimmerWidth = 0.45f,
        dropOff = ShimmerDropOff.Soft,
        easing = ShimmerEasing.EaseInOut,
        staggerDelayMillis = 80  // Stagger delay between items
    )
    
    // Shared shimmer state
    val shimmerState = rememberShimmerState(
        config = shimmerConfig,
        controller = shimmerController
    )
    
    // Simulate loading
    LaunchedEffect(isLoading) {
        if (isLoading) {
            shimmerController.resume()
            delay(2500)
            posts = SampleData.feedPosts
            isLoading = false
        }
    }
    
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
            // Skeleton items with staggered animation
            skeletonItems(
                isLoading = isLoading,
                count = 5,
                shimmerState = shimmerState
            ) { index, state ->
                // Each item gets a stagger offset based on index
                val staggeredState = rememberShimmerState(
                    config = shimmerConfig,
                    controller = shimmerController,
                    staggerIndex = index
                )
                
                SkeletonCard(
                    modifier = Modifier.fillMaxWidth(),
                    imageHeight = 160.dp,
                    titleLines = 1,
                    showDescription = true,
                    descriptionLines = 2,
                    shimmerState = staggeredState
                )
            }
            
            // Real content when loaded
            if (!isLoading) {
                items(
                    items = posts,
                    key = { it.id }
                ) { post ->
                    Skeleton(
                        isLoading = false,
                        transition = SkeletonTransition.Crossfade,
                        transitionDurationMs = 400,
                        skeleton = { SkeletonCard(shimmerState = shimmerState) }
                    ) {
                        FeedPostCard(post = post)
                    }
                }
            }
        }
    }
}
