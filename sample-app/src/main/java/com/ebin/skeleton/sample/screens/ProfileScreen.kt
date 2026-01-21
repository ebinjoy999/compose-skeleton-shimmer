package com.ebin.skeleton.sample.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import com.ebin.skeleton.sample.components.ProfileHeader
import com.ebin.skeleton.sample.data.SampleData
import com.ebin.skeleton.sample.data.UserProfile
import com.ebin.skeleton.shimmer.rememberShimmerState
import com.ebin.skeleton.skeleton.Skeleton
import com.ebin.skeleton.skeleton.SkeletonListItem
import com.ebin.skeleton.skeleton.SkeletonProfile
import com.ebin.skeleton.skeleton.SkeletonTransition
import kotlinx.coroutines.delay

/**
 * Profile Screen - Demonstrates skeleton loading for profile UI.
 *
 * Features:
 * - Profile header skeleton with avatar, name, bio
 * - Action button placeholders
 * - List item skeletons for settings/menu items
 * - Crossfade transition to real content
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    var isLoading by remember { mutableStateOf(true) }
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    
    // Simulate loading
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2500)
            profile = SampleData.userProfile
            isLoading = false
        }
    }
    
    val shimmerState = rememberShimmerState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    profile = null
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
                .verticalScroll(rememberScrollState())
        ) {
            // Profile header with skeleton
            Skeleton(
                isLoading = isLoading,
                transition = SkeletonTransition.Crossfade,
                transitionDurationMs = 400,
                skeleton = {
                    SkeletonProfile(
                        avatarSize = 80.dp,
                        showBio = true,
                        bioLines = 2,
                        actionButtonCount = 2,
                        shimmerState = shimmerState
                    )
                }
            ) {
                profile?.let { ProfileHeader(profile = it) }
            }
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            
            // Settings/Menu section
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            
            // Menu items with skeletons
            val menuItems = listOf(
                "Edit Profile" to "Update your information",
                "Notifications" to "Manage alerts",
                "Privacy" to "Control your data",
                "Help & Support" to "Get assistance"
            )
            
            menuItems.forEachIndexed { index, (title, subtitle) ->
                Skeleton(
                    isLoading = isLoading,
                    transition = SkeletonTransition.Crossfade,
                    skeleton = {
                        SkeletonListItem(
                            leadingSize = 40.dp,
                            isLeadingCircle = false,
                            showSubtitle = true,
                            shimmerState = shimmerState
                        )
                    }
                ) {
                    SettingsMenuItem(title = title, subtitle = subtitle)
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun SettingsMenuItem(
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
