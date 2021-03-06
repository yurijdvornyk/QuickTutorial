package com.ydvornyk.quicktutorial.presentation.simple

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

    private lateinit var contentLayout: ViewGroup

    private lateinit var dividerImage: ImageView

    @get:LayoutRes
    override val layoutRes: Int
        get() = R.layout.activiy_quick_tutorial

    @get:IdRes
    override val controlRes: Int
        get() = R.id.layout_control

    public override fun onSwipeLeft() {
        if (config!!.enableScreenSwiping) {
            super.onNextClicked(contentLayout)
        }
    }

    public override fun onSwipeRight() {
        if (config!!.enableScreenSwiping) {
            super.onPreviousClicked(contentLayout)
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
        contentLayout = findViewById(R.id.layout_content)
        dividerImage = findViewById(R.id.divider_control)
        if (config != null) {
            setControl()
            setOrientationLock()
            setTheme()
        }
        setUpBackground()
        val dividerColorValue = TypedValue()
        if (theme.resolveAttribute(R.attr.controlForeground, dividerColorValue, true)) {
            DrawableCompat.setTint(dividerImage.drawable, ContextCompat.getColor(this, dividerColorValue.resourceId))
        }
        goToPage(0)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putParcelable(ARGUMENT_CONFIG, config)
        outState.putParcelable(ARGUMENT_CONTENT, content)
        outState.putInt(ARGUMENT_CURRENT_PAGE, currentPage)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            config = savedInstanceState.getParcelable(ARGUMENT_CONFIG)
            content = savedInstanceState.getParcelable(ARGUMENT_CONTENT)
            currentPage = savedInstanceState.getInt(ARGUMENT_CURRENT_PAGE)
            goToPage(currentPage)
        }
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

    private fun setTheme() {
        if (config!!.theme > 0) {
            setTheme(config!!.theme)
        } else {
            setTheme(R.style.BaseQuickTutorialTheme)
        }
    }

    private fun setOrientationLock() {
        if (config!!.lockOrientation != null) {
            requestedOrientation = config!!.lockOrientation!!.orientation
        }
    }

    private fun setControl() {
        if (content != null) {
            control.setUpControl(config!!, content!!)
        }
    }

    private fun setUpBackground() {
        val layout: ViewGroup = findViewById(R.id.layout_view)
        val themeBackgroundColor = TypedValue()
        val themeBackgroundDrawable = TypedValue()
        when {
            config!!.backgroundDrawableRes != null -> layout.setBackgroundResource(config!!.backgroundDrawableRes!!)
            config!!.backgroundColorRes != null -> layout.setBackgroundColor(ContextCompat.getColor(this, config!!.backgroundColorRes!!))
            theme.resolveAttribute(R.attr.screenBackgroundDrawable, themeBackgroundDrawable, true) -> layout.setBackgroundResource(themeBackgroundDrawable.resourceId)
            theme.resolveAttribute(R.attr.screenBackgroundColor, themeBackgroundColor, true) -> layout.setBackgroundColor(ContextCompat.getColor(this, themeBackgroundColor.resourceId))
        }
    }

    companion object {

        const val ARGUMENT_CONTENT = "arg_content"
        const val ARGUMENT_CONFIG = "arg_config"
        const val ARGUMENT_CURRENT_PAGE = "arg_current_page"

        fun createIntent(context: Context, content: TutorialContent, config: TutorialConfig): Intent {
            val intent = Intent(context, SimpleTutorialActivity::class.java)
            intent.putExtra(ARGUMENT_CONTENT, content)
            intent.putExtra(ARGUMENT_CONFIG, config)
            return intent
        }
    }
}
