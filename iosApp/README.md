# iOS App

SwiftUI wrapper app that hosts the Compose Multiplatform UI from `sample-ios-shared`.

## Prerequisites

- Xcode 15.0+
- iOS 15.0+ deployment target

## Building

### 1. Build the Kotlin Framework

First, build the SharedUI framework from the project root:

```bash
./gradlew :sample-ios-shared:linkDebugFrameworkIosSimulatorArm64
```

### 2. Open in Xcode

```bash
open iosApp.xcodeproj
```

### 3. Build & Run

- Select your target simulator or device
- Press ⌘R to build and run

## Structure

```
iosApp/
├── iosApp.xcodeproj/   # Xcode project
└── iosApp/
    ├── App.swift           # App entry point
    ├── ContentView.swift   # SwiftUI view wrapping Compose
    └── Assets.xcassets/    # App assets
```

## How It Works

The Xcode build includes a script phase that:
1. Invokes `./gradlew :sample-ios-shared:embedAndSignAppleFrameworkForXcode`
2. This builds the Kotlin code into a `SharedUI.framework`
3. The framework is automatically linked to the iOS app

The SwiftUI `ContentView` wraps the Compose UI using `UIViewControllerRepresentable`:

```swift
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.MainViewController()
    }
}
```
