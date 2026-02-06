package com.ebin.skeleton.accessibility

import android.content.Context
import android.provider.Settings

/**
 * Android-specific implementation for checking reduced motion preference.
 */
object AndroidAccessibility {
    /**
     * Checks if the user has enabled "Remove Animations" or "Reduce Motion"
     * in system accessibility settings.
     *
     * @param context Android context
     * @return true if animations should be reduced
     */
    fun isReduceMotionEnabled(context: Context): Boolean {
        return try {
            val animatorDuration = Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.ANIMATOR_DURATION_SCALE,
                1f
            )
            val transitionDuration = Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.TRANSITION_ANIMATION_SCALE,
                1f
            )
            val windowDuration = Settings.Global.getFloat(
                context.contentResolver,
                Settings.Global.WINDOW_ANIMATION_SCALE,
                1f
            )
            
            // If any animation scale is 0, user wants reduced motion
            animatorDuration == 0f || transitionDuration == 0f || windowDuration == 0f
        } catch (e: Exception) {
            false
        }
    }
}

/**
 * Actual implementation for Android that checks system accessibility settings.
 * Note: This returns false by default since we can't easily access Context here.
 * Use [rememberReduceMotionEnabledWithContext] for proper Android implementation.
 */
actual fun isReduceMotionEnabled(): Boolean = false
