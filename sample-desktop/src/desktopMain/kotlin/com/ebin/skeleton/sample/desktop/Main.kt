package com.ebin.skeleton.sample.desktop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.ebin.skeleton.shimmer.ShimmerConfig
import com.ebin.skeleton.shimmer.rememberShimmerState
import com.ebin.skeleton.skeleton.*
import com.ebin.skeleton.theme.SkeletonTheme
import com.ebin.skeleton.theme.skeletonColors

fun main() = application {
    val windowState = rememberWindowState(size = DpSize(800.dp, 900.dp))
    
    Window(
        onCloseRequest = ::exitApplication,
        title = "Skeleton Shimmer - Desktop Sample",
        state = windowState
    ) {
        SkeletonSampleApp()
    }
}

@Composable
fun SkeletonSampleApp() {
    var isLoading by remember { mutableStateOf(true) }
    var selectedPreset by remember { mutableStateOf("Default") }
    
    val presets = listOf("Default", "Subtle", "Prominent", "Pulse", "Spotlight", "MultiWave")
    
    val config = when (selectedPreset) {
        "Subtle" -> ShimmerConfig.Subtle
        "Prominent" -> ShimmerConfig.Prominent
        "Pulse" -> ShimmerConfig.Pulse
        "Spotlight" -> ShimmerConfig.Spotlight
        "MultiWave" -> ShimmerConfig.MultiWave
        else -> ShimmerConfig.Default
    }
    
    val shimmerState = rememberShimmerState(config = config)
    
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        SkeletonTheme(colors = skeletonColors()) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    // Sidebar Controls
                    Surface(
                        modifier = Modifier
                            .width(250.dp)
                            .fillMaxHeight(),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Controls",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            
                            HorizontalDivider()
                            
                            // Loading Toggle
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Show Skeleton")
                                Switch(
                                    checked = isLoading,
                                    onCheckedChange = { isLoading = it }
                                )
                            }
                            
                            HorizontalDivider()
                            
                            // Preset Selector
                            Text(
                                text = "Shimmer Preset",
                                style = MaterialTheme.typography.titleMedium
                            )
                            
                            presets.forEach { preset ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = selectedPreset == preset,
                                        onClick = { selectedPreset = preset }
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(preset)
                                }
                            }
                            
                            Spacer(Modifier.weight(1f))
                            
                            Text(
                                text = "Current: $selectedPreset",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    // Main Content
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Text(
                            text = "Skeleton Components Demo",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        
                        // Primitives Section
                        SectionCard(title = "Primitives") {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text("SkeletonBox", style = MaterialTheme.typography.labelMedium)
                                Skeleton(
                                    isLoading = isLoading,
                                    skeleton = {
                                        SkeletonBox(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(80.dp),
                                            shimmerState = shimmerState
                                        )
                                    }
                                ) {
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text("Actual Content")
                                        }
                                    }
                                }
                                
                                Text("SkeletonCircle", style = MaterialTheme.typography.labelMedium)
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    Skeleton(
                                        isLoading = isLoading,
                                        skeleton = { SkeletonCircle(size = 48.dp, shimmerState = shimmerState) }
                                    ) {
                                        Surface(
                                            modifier = Modifier.size(48.dp),
                                            shape = MaterialTheme.shapes.extraLarge,
                                            color = MaterialTheme.colorScheme.primary
                                        ) {}
                                    }
                                    Skeleton(
                                        isLoading = isLoading,
                                        skeleton = { SkeletonCircle(size = 64.dp, shimmerState = shimmerState) }
                                    ) {
                                        Surface(
                                            modifier = Modifier.size(64.dp),
                                            shape = MaterialTheme.shapes.extraLarge,
                                            color = MaterialTheme.colorScheme.secondary
                                        ) {}
                                    }
                                    Skeleton(
                                        isLoading = isLoading,
                                        skeleton = { SkeletonCircle(size = 80.dp, shimmerState = shimmerState) }
                                    ) {
                                        Surface(
                                            modifier = Modifier.size(80.dp),
                                            shape = MaterialTheme.shapes.extraLarge,
                                            color = MaterialTheme.colorScheme.tertiary
                                        ) {}
                                    }
                                }
                                
                                Text("SkeletonLine", style = MaterialTheme.typography.labelMedium)
                                Skeleton(
                                    isLoading = isLoading,
                                    skeleton = {
                                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            SkeletonLine(modifier = Modifier.fillMaxWidth(0.9f), shimmerState = shimmerState)
                                            SkeletonLine(modifier = Modifier.fillMaxWidth(0.7f), shimmerState = shimmerState)
                                            SkeletonLine(modifier = Modifier.fillMaxWidth(0.5f), shimmerState = shimmerState)
                                        }
                                    }
                                ) {
                                    Column {
                                        Text("This is the first line of content")
                                        Text("Second line here")
                                        Text("Short line")
                                    }
                                }
                            }
                        }
                        
                        // Card Section
                        SectionCard(title = "SkeletonCard") {
                            Skeleton(
                                isLoading = isLoading,
                                skeleton = {
                                    SkeletonCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        shimmerState = shimmerState
                                    )
                                }
                            ) {
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Column {
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(160.dp),
                                            color = MaterialTheme.colorScheme.primaryContainer
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text("Image Placeholder")
                                            }
                                        }
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Text("Card Title", style = MaterialTheme.typography.titleLarge)
                                            Text("This is the card description with some details about the content.")
                                        }
                                    }
                                }
                            }
                        }
                        
                        // List Items Section
                        SectionCard(title = "SkeletonListItem") {
                            Column {
                                repeat(3) { index ->
                                    Skeleton(
                                        isLoading = isLoading,
                                        skeleton = {
                                            SkeletonListItem(shimmerState = shimmerState)
                                        }
                                    ) {
                                        ListItem(
                                            headlineContent = { Text("List Item ${index + 1}") },
                                            supportingContent = { Text("Supporting text for item ${index + 1}") },
                                            leadingContent = {
                                                Surface(
                                                    modifier = Modifier.size(48.dp),
                                                    shape = MaterialTheme.shapes.extraLarge,
                                                    color = MaterialTheme.colorScheme.primary
                                                ) {}
                                            }
                                        )
                                    }
                                    if (index < 2) HorizontalDivider()
                                }
                            }
                        }
                        
                        // Profile Section
                        SectionCard(title = "SkeletonProfile") {
                            Skeleton(
                                isLoading = isLoading,
                                skeleton = {
                                    SkeletonProfile(
                                        avatarSize = 100.dp,
                                        showBio = true,
                                        actionButtonCount = 2,
                                        shimmerState = shimmerState
                                    )
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Surface(
                                        modifier = Modifier.size(100.dp),
                                        shape = MaterialTheme.shapes.extraLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    ) {}
                                    Spacer(Modifier.height(12.dp))
                                    Text("John Doe", style = MaterialTheme.typography.titleLarge)
                                    Text("@johndoe", style = MaterialTheme.typography.bodyMedium)
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        "Software developer passionate about Kotlin and Compose Multiplatform.",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(Modifier.height(12.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                        Button(onClick = {}) { Text("Follow") }
                                        OutlinedButton(onClick = {}) { Text("Message") }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}
