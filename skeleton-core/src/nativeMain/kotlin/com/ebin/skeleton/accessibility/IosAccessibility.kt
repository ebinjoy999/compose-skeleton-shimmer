package com.ebin.skeleton.accessibility

/**
 * Native (iOS/macOS/etc.) implementation for reduced motion check.
 * This could potentially query UIAccessibility.isReduceMotionEnabled on iOS.
 * For now, returns false by default.
 * 
 * TODO: Implement using UIKit interop:
 * ```kotlin
 * import platform.UIKit.UIAccessibilityIsReduceMotionEnabled
 * actual fun isReduceMotionEnabled(): Boolean = UIAccessibilityIsReduceMotionEnabled()
 * ```
 */
actual fun isReduceMotionEnabled(): Boolean = false
