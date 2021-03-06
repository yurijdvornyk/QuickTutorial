package com.ydvornyk.quicktutorial

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import com.ydvornyk.quicktutorial.model.TutorialContent
import com.ydvornyk.quicktutorial.presentation.BaseTutorialActivity
import com.ydvornyk.quicktutorial.presentation.simple.SimpleTutorialActivity

/**
 * Created by yuriidvornyk on 4/16/18.
 */

class QuickTutorial private constructor() {

    private var activityClass: Class<*>? = null

    val config: TutorialConfig

    private var content: TutorialContent? = null

    init {
        config = TutorialConfig.defaultConfig
        content = TutorialContent()
    }

    fun start(context: Context) {
        if (activityClass == null) {
            if (context is Activity) {
                context.startActivityForResult(SimpleTutorialActivity.createIntent(context, content!!, config), REQUEST_CODE)
            } else {
                context.startActivity(SimpleTutorialActivity.createIntent(context, content!!, config))
            }
        } else {
            if (context is Activity) {
                context.startActivityForResult(Intent(context, activityClass), REQUEST_CODE)
            } else {
                context.startActivity(Intent(context, activityClass))
            }
        }
    }

    companion object {

        val REQUEST_CODE: Int = QuickTutorial::class.hashCode() % 65535
    }

    class Builder {

        private val instance: QuickTutorial = QuickTutorial()

        fun <A : BaseTutorialActivity<*, *>> withCustomActivity(activityClass: Class<A>): Builder {
            instance.activityClass = activityClass
            return this
        }

        fun withContent(content: TutorialContent): Builder {
            instance.content = content
            return this
        }

        fun allowBackPress(allowBackPress: Boolean): Builder {
            instance.config.isAllowBackPress = allowBackPress
            return this
        }

        fun withTheme(@StyleRes theme: Int): Builder {
            instance.config.theme = theme
            return this
        }

        fun previousButton(@DrawableRes imageRes: Int): Builder {
            instance.config.previousButtonImage = imageRes
            return this
        }

        fun nextButton(@DrawableRes imageRes: Int): Builder {
            instance.config.nextButtonImage = imageRes
            return this
        }

        fun previousButton(text: String): Builder {
            instance.config.previousButtonText = text
            return this
        }

        fun nextButton(text: String): Builder {
            instance.config.nextButtonText = text
            return this
        }

        fun completeButton(@DrawableRes imageRes: Int): Builder {
            instance.config.completeButtonImage = imageRes
            return this
        }

        fun dismissButton(@DrawableRes imageRes: Int): Builder {
            instance.config.dismissButtonImage = imageRes
            return this
        }

        fun completeButton(text: String): Builder {
            instance.config.completeButtonText = text
            return this
        }

        fun dismissButton(text: String): Builder {
            instance.config.dismissButtonText = text
            return this
        }

        fun allowSwipeNavigation(allow: Boolean): Builder {
            instance.config.enableScreenSwiping = allow
            return this
        }

        fun lockOrientation(orientation: TutorialConfig.Orientation): Builder {
            instance.config.lockOrientation = orientation
            return this
        }

        fun useNumericProgress(divider: String? = null): Builder {
            instance.config.useNumericProgress = true
            instance.config.numericDivider = divider
            return this
        }

        fun backgroundColorRes(@ColorRes color: Int?): Builder {
            instance.config.backgroundColorRes = color
            return this
        }

        fun backgroundDrawableRes(@DrawableRes drawable: Int?): Builder {
            instance.config.backgroundDrawableRes = drawable
            return this
        }

        fun build(): QuickTutorial {
            return instance
        }
    }
}
