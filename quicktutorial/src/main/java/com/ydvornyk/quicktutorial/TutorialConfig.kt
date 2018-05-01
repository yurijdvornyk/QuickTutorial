package com.ydvornyk.quicktutorial

import android.os.Parcelable
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import kotlinx.android.parcel.Parcelize

/**
 * Created by yuriidvornyk on 4/20/18.
 */
@Parcelize
class TutorialConfig(var isAllowBackPress: Boolean = false,
                     @StyleRes var theme: Int = 0,
                     @DrawableRes var previousButtonImage: Int? = null,
                     @DrawableRes var nextButtonImage: Int? = null,
                     var previousButtonText: String? = null,
                     var nextButtonText: String? = null,
                     @DrawableRes var completeButtonImage: Int? = null,
                     @DrawableRes var dismissButtonImage: Int? = null,
                     var completeButtonText: String? = null,
                     var dismissButtonText: String? = null,
                     var enableScreenSwiping: Boolean = false,
                     var lockPortraitOrientation: Boolean = false,
                     var enableAnimation: Boolean = false,
                     var disableGoBack: Boolean = false,
                     var useNumericProgress: Boolean = false,
                     var numericDivider: String? = null)
    : Parcelable {

    private constructor() : this(false, -1, null, null,
            null, null, null, null,
            null, null, false, false,
            false, false, false, null)


    fun shouldHideDismissButton(): Boolean {
        return dismissButtonText == null && dismissButtonImage == null
    }

    fun shouldHideCompleteButton(): Boolean {
        return completeButtonText == null && completeButtonImage == null
    }

    @StyleRes
    fun getThemeConfig(): Int {
        return when {
            theme > 0 -> theme
            else -> R.style.BaseQuickTutorialTheme
        }
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
