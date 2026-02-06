package com.ebin.skeleton.sample.ios

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

/**
 * Creates a UIViewController that hosts the Compose UI.
 * 
 * Usage in Swift:
 * ```swift
 * import SharedUI
 * 
 * let viewController = SUIMain_iosKt.MainViewController()
 * ```
 */
fun MainViewController(): UIViewController =
    ComposeUIViewController {
        SkeletonSampleApp()
    }

