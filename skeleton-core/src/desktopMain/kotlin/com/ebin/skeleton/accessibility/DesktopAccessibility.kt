package com.ebin.skeleton.accessibility

/**
 * Desktop implementation for reduced motion check.
 * Desktop platforms typically don't have a system-wide reduced motion setting,
 * so we return false by default.
 */
actual fun isReduceMotionEnabled(): Boolean = false
