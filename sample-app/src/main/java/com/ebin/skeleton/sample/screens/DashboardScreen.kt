package com.ebin.skeleton.sample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.ebin.skeleton.sample.components.DashboardTileCard
import com.ebin.skeleton.sample.data.DashboardTile
import com.ebin.skeleton.sample.data.SampleData
import com.ebin.skeleton.shimmer.ShimmerConfig
import com.ebin.skeleton.shimmer.ShimmerType
import com.ebin.skeleton.shimmer.rememberShimmerState
import com.ebin.skeleton.skeleton.SkeletonTile
import com.ebin.skeleton.skeleton.skeletonGridItems
import kotlinx.coroutines.delay

/**
 * Dashboard Screen - Demonstrates skeleton loading in a grid layout.
 *
 * Features:
 * - LazyVerticalGrid with skeleton tiles during loading
 * - Mixed shapes and sizes in grid
 * - Synchronized shimmer across all tiles
 * - Smooth transition to real dashboard data
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    var isLoading by remember { mutableStateOf(true) }
    var tiles by remember { mutableStateOf<List<DashboardTile>>(emptyList()) }
    
    // Simulate loading
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2500)
            tiles = SampleData.dashboardTiles
            isLoading = false
        }
    }
    
    // Use Radial/Spotlight shimmer for grid tiles - creates a spotlight effect
    val radialConfig = ShimmerConfig(
        type = ShimmerType.Radial,
        durationMillis = 1800,
        shimmerWidth = 1f,
        intensity = 0.8f
    )
    val shimmerState = rememberShimmerState(config = radialConfig)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    tiles = emptyList()
                    isLoading = true
                }
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reload")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header section
            Text(
                text = "Overview",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Skeleton grid items when loading
                skeletonGridItems(
                    isLoading = isLoading,
                    count = 6,
                    shimmerState = shimmerState
                ) { _, state ->
                    SkeletonTile(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        showLabel = false,
                        cornerRadius = 16.dp,
                        shimmerState = state
                    )
                }
                
                // Real content when loaded
                if (!isLoading) {
                    items(
                        items = tiles,
                        key = { it.id }
                    ) { tile ->
                        DashboardTileCard(tile = tile)
                    }
                }
            }
        }
    }
}
