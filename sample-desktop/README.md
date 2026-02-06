# Desktop Sample App

A Compose Desktop application demonstrating the skeleton-core library.

## Features

- Interactive sidebar to toggle skeleton loading state
- Multiple shimmer presets (Default, Subtle, Prominent, Pulse, Spotlight, MultiWave)
- Showcases all skeleton components:
  - SkeletonBox, SkeletonCircle, SkeletonLine
  - SkeletonCard
  - SkeletonListItem
  - SkeletonProfile

## Running

```bash
./gradlew :sample-desktop:run
```

## Building Distributions

Create native distribution packages:

```bash
# macOS DMG
./gradlew :sample-desktop:packageDmg

# Windows MSI
./gradlew :sample-desktop:packageMsi

# Linux DEB
./gradlew :sample-desktop:packageDeb
```
