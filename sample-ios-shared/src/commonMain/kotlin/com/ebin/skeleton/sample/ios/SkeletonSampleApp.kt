package com.ebin.skeleton.sample.ios

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ebin.skeleton.shimmer.ShimmerConfig
import com.ebin.skeleton.shimmer.rememberShimmerState
import com.ebin.skeleton.skeleton.*
import com.ebin.skeleton.theme.SkeletonTheme
import com.ebin.skeleton.theme.skeletonColors

/**
 * Main app content that can be embedded in iOS via ComposeUIViewController.
 * Call this from your iOS app using:
 * 
 * ```swift
 * import SharedUI
 * 
 * struct ContentView: View {
 *     var body: some View {
 *         ComposeView()
 *             .ignoresSafeArea()
 *     }
 * }
 * 
 * struct ComposeView: UIViewControllerRepresentable {
 *     func makeUIViewController(context: Context) -> UIViewController {
 *         MainKt.MainViewController()
 *     }
 *     
 *     func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
 * }
 * ```
 */
@Composable
fun SkeletonSampleApp() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        SkeletonSampleAppContent()
    }
}

@Composable
private fun SkeletonSampleAppContent() {
    var isLoading by remember { mutableStateOf(true) }
    var selectedPresetIndex by remember { mutableIntStateOf(0) }
    
    val presets = listOf(
        "Default" to ShimmerConfig.Default,
        "Subtle" to ShimmerConfig.Subtle,
        "Prominent" to ShimmerConfig.Prominent
    )
    
    val config = presets[selectedPresetIndex].second
    val shimmerState = rememberShimmerState(config = config)
    
    MaterialTheme {
        SkeletonTheme(colors = skeletonColors()) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header
                    Text(
                        text = "Skeleton Shimmer",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    
                    // Controls Row
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
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
                            
                            Spacer(Modifier.height(12.dp))
                            
                            Text("Preset", style = MaterialTheme.typography.labelMedium)
                            Spacer(Modifier.height(8.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                presets.forEachIndexed { index, (name, _) ->
                                    Button(
                                        onClick = { selectedPresetIndex = index },
                                        modifier = Modifier.weight(1f),
                                        colors = if (selectedPresetIndex == index) 
                                            ButtonDefaults.buttonColors()
                                        else 
                                            ButtonDefaults.outlinedButtonColors()
                                    ) {
                                        Text(name, maxLines = 1)
                                    }
                                }
                            }
                        }
                    }
                    
                    // Primitives
                    SectionHeader("Primitives")
                    
                    Skeleton(
                        isLoading = isLoading,
                        skeleton = {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                SkeletonLine(modifier = Modifier.fillMaxWidth(0.8f), shimmerState = shimmerState)
                                SkeletonLine(modifier = Modifier.fillMaxWidth(0.6f), shimmerState = shimmerState)
                                SkeletonLine(modifier = Modifier.fillMaxWidth(0.4f), shimmerState = shimmerState)
                            }
                        }
                    ) {
                        Column {
                            Text("First line of text content")
                            Text("Second line")
                            Text("Short")
                        }
                    }
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Skeleton(
                            isLoading = isLoading,
                            skeleton = { SkeletonCircle(size = 40.dp, shimmerState = shimmerState) }
                        ) {
                            Surface(
                                modifier = Modifier.size(40.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.primary
                            ) {}
                        }
                        
                        Skeleton(
                            isLoading = isLoading,
                            skeleton = { SkeletonCircle(size = 56.dp, shimmerState = shimmerState) }
                        ) {
                            Surface(
                                modifier = Modifier.size(56.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.secondary
                            ) {}
                        }
                        
                        Skeleton(
                            isLoading = isLoading,
                            skeleton = { SkeletonCircle(size = 72.dp, shimmerState = shimmerState) }
                        ) {
                            Surface(
                                modifier = Modifier.size(72.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.tertiary
                            ) {}
                        }
                    }
                    
                    // Card
                    SectionHeader("Card")
                    
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
                                        .height(140.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("Image")
                                    }
                                }
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Card Title", style = MaterialTheme.typography.titleMedium)
                                    Text("Description text goes here")
                                }
                            }
                        }
                    }
                    
                    // List Items
                    SectionHeader("List Items")
                    
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            repeat(4) { index ->
                                Skeleton(
                                    isLoading = isLoading,
                                    skeleton = { SkeletonListItem(shimmerState = shimmerState) }
                                ) {
                                    ListItem(
                                        headlineContent = { Text("Item ${index + 1}") },
                                        supportingContent = { Text("Supporting text") },
                                        leadingContent = {
                                            Surface(
                                                modifier = Modifier.size(40.dp),
                                                shape = MaterialTheme.shapes.extraLarge,
                                                color = MaterialTheme.colorScheme.primary
                                            ) {}
                                        }
                                    )
                                }
                                if (index < 3) HorizontalDivider()
                            }
                        }
                    }
                    
                    // Profile
                    SectionHeader("Profile")
                    
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Skeleton(
                            isLoading = isLoading,
                            skeleton = {
                                SkeletonProfile(
                                    avatarSize = 80.dp,
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
                                    modifier = Modifier.size(80.dp),
                                    shape = MaterialTheme.shapes.extraLarge,
                                    color = MaterialTheme.colorScheme.primary
                                ) {}
                                Spacer(Modifier.height(8.dp))
                                Text("Jane Smith", style = MaterialTheme.typography.titleLarge)
                                Text("@janesmith")
                                Spacer(Modifier.height(8.dp))
                                Text("iOS Developer & Kotlin enthusiast")
                                Spacer(Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(onClick = {}) { Text("Follow") }
                                    OutlinedButton(onClick = {}) { Text("Message") }
                                }
                            }
                        }
                    }
                    
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary
    )
}
