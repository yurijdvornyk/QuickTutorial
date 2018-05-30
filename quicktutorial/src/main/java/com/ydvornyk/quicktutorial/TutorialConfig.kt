package com.ydvornyk.quicktutorial

import android.content.pm.ActivityInfo
import android.os.Parcelable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import kotlinx.android.parcel.Parcelize

/**
 * Created by yuriidvornyk on 4/20/18.
 */
@Parcelize
class TutorialConfig(var isAllowBackPress: Boolean = false,
                     @StyleRes var theme: Int = -1,
                     @DrawableRes var previousButtonImage: Int? = null,
                     @DrawableRes var nextButtonImage: Int? = null,
                     var previousButtonText: String? = null,
                     var nextButtonText: String? = null,
                     @DrawableRes var completeButtonImage: Int? = null,
                     @DrawableRes var dismissButtonImage: Int? = null,
                     var completeButtonText: String? = null,
                     var dismissButtonText: String? = null,
                     var enableScreenSwiping: Boolean = false,
                     var enableAnimation: Boolean = false,
                     var disableGoBack: Boolean = false,
                     var useNumericProgress: Boolean = false,
                     var numericDivider: String? = null,
                     var lockOrientation: Orientation? = null,
                     @ColorRes var backgroundColorRes: Int? = null,
                     @DrawableRes var backgroundDrawableRes: Int? = null) : Parcelable {

    private constructor() : this(false, -1, null, null,
            null, null, null, null,
            null, null, false, false,
            false, false, null, null,
            null, null)

    fun shouldHideDismissButton(): Boolean {
        return dismissButtonText == null && dismissButtonImage == null
    }

    fun shouldHideCompleteButton(): Boolean {
        return completeButtonText == null && completeButtonImage == null
    }

    enum class Orientation(val orientation: Int) {
        PORTRAIT(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT),
        LANDSCAPE(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }

    companion object {
        val defaultConfig: TutorialConfig
            get() {
                val config = TutorialConfig()
                config.isAllowBackPress = true
                config.theme = R.style.BaseQuickTutorialTheme
                config.nextButtonImage = R.drawable.ic_button_next
                config.previousButtonImage = R.drawable.ic_button_back
                config.completeButtonImage = R.drawable.ic_button_done
                config.enableScreenSwiping = false
                config.enableAnimation = true
                config.disableGoBack = false
                config.useNumericProgress = false
                return config
            }
    }
}
