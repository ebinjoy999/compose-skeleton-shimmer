# iOS Shared UI Module

A KMP shared module providing Compose Multiplatform UI that iOS apps can consume.

## Architecture

This module exposes a `SharedUI` framework that contains:
- `SkeletonSampleApp` - Full Compose UI demo screen
- `MainViewController()` - UIKit-compatible view controller factory

## Building the Framework

```bash
# Build for iOS Simulator (arm64)
./gradlew :sample-ios-shared:linkDebugFrameworkIosSimulatorArm64

# Build for iOS Device
./gradlew :sample-ios-shared:linkDebugFrameworkIosArm64

# Build for Xcode integration
./gradlew :sample-ios-shared:embedAndSignAppleFrameworkForXcode
```

## Swift Integration

Add the framework to your Xcode project and use it with SwiftUI:

```swift
import SwiftUI
import SharedUI

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.all)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        SUIMain_iosKt.MainViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
```

## Running the iOS App

1. Build the Kotlin framework first:
   ```bash
   ./gradlew :sample-ios-shared:linkDebugFrameworkIosSimulatorArm64
   ```

2. Open the Xcode project:
   ```bash
   open iosApp/iosApp.xcodeproj
   ```

3. Build and run in Xcode (⌘R)

## Features

The sample app demonstrates:
- Shimmer presets (Default, Subtle, Prominent)
- Skeleton primitives (SkeletonLine, SkeletonCircle, SkeletonBox)
- SkeletonCard component
- SkeletonListItem component  
- SkeletonProfile component
- Toggle to show/hide skeleton loading state
