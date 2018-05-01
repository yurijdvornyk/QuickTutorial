package com.ydvornyk.quicktutorial.presentation.simple

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentTransaction
import android.util.TypedValue
import android.view.View
import com.ydvornyk.quicktutorial.R
import com.ydvornyk.quicktutorial.TutorialConfig
import com.ydvornyk.quicktutorial.model.TutorialContent
import com.ydvornyk.quicktutorial.presentation.BaseTutorialActivity

/**
 * Created by yuriidvornyk on 4/18/18.
 */

class SimpleTutorialActivity : BaseTutorialActivity<SimpleTutorialControl, SimpleTutorialFragment>() {

    private var content: TutorialContent? = null

    private var config: TutorialConfig? = null

    @get:LayoutRes
    override val layoutRes: Int
        get() = R.layout.activiy_quick_tutorial

    @get:IdRes
    override val controlRes: Int
        get() = R.id.layout_control

    override fun onNextClicked(view: View) {
        if (currentPage == pagesCount - 1 && !config!!.shouldHideCompleteButton()) {
            onCompleteClicked(view)
        } else {
            super.onNextClicked(view)
        }
    }

    override fun onPreviousClicked(view: View) {
        if (currentPage == 0 && !config!!.shouldHideDismissButton()) {
            onDismissClicked(view)
        } else {
            super.onPreviousClicked(view)
        }
    }

    override fun onBackPressed() {
        if (config!!.isAllowBackPress) {
            super.onBackPressed()
        }
    }

    public override fun onSwipeLeft() {
        if (config!!.enableScreenSwiping) {
            super.onNextClicked(findViewById(R.id.layout_content))
        }
    }

    public override fun onSwipeRight() {
        if (config!!.enableScreenSwiping) {
            super.onPreviousClicked(findViewById(R.id.layout_content))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra(ARGUMENT_CONTENT)) {
            content = intent.getParcelableExtra(ARGUMENT_CONTENT)
            for (page in content!!.pages) {
                fragments.add(SimpleTutorialFragment.newInstance(page))
            }
        }

        if (intent.hasExtra(ARGUMENT_CONFIG)) {
            config = intent.getParcelableExtra(ARGUMENT_CONFIG)
        }

        if (config != null) {
            // Set content
            if (content != null) {
                control.setUpControl(config!!, content!!)
            }

            // Set orientation lock
            if (config!!.lockOrientation != null) {
                requestedOrientation = config!!.lockOrientation!!.orientation
            }

            // Set theme
            if (config!!.theme > 0) {
                setTheme(config!!.theme)
            } else {
                setTheme(R.style.BaseQuickTutorialTheme)
            }
        }

        goToPage(0)
    }

    override fun initializeControl(): SimpleTutorialControl {
        control = SimpleTutorialControl(this)
        return control
    }

    override fun onFragmentTransactionBeforeReplace(transaction: FragmentTransaction, nextPagePosition: Int) {
        super.onFragmentTransactionBeforeReplace(transaction, nextPagePosition)
        if (config!!.enableAnimation) {
            val fadeInLeftToRightValue = TypedValue()
            val fadeInRightToLeftValue = TypedValue()
            val fadeOutLeftToRightValue = TypedValue()
            val fadeOutRightToLeftValue = TypedValue()
            theme.resolveAttribute(R.attr.screenFadeInFromLeftAnimation, fadeInLeftToRightValue, true)
            theme.resolveAttribute(R.attr.screenFadeInFromRightAnimation, fadeInRightToLeftValue, true)
            theme.resolveAttribute(R.attr.screenFadeOutToLeftAnimation, fadeOutRightToLeftValue, true)
            theme.resolveAttribute(R.attr.screenFadeOutToRightAnimation, fadeOutLeftToRightValue, true)
            if (currentPage < nextPagePosition) {
                transaction.setCustomAnimations(fadeOutRightToLeftValue.resourceId, fadeInRightToLeftValue.resourceId)
            } else {
                transaction.setCustomAnimations(fadeOutLeftToRightValue.resourceId, fadeInLeftToRightValue.resourceId)
            }
        }
    }

    companion object {

        val ARGUMENT_CONTENT = "arg_content"
        val ARGUMENT_CONFIG = "arg_config"

        fun createIntent(context: Context, content: TutorialContent, config: TutorialConfig): Intent {
            val intent = Intent(context, SimpleTutorialActivity::class.java)
            intent.putExtra(ARGUMENT_CONTENT, content)
            intent.putExtra(ARGUMENT_CONFIG, config)
            return intent
        }
    }
}
