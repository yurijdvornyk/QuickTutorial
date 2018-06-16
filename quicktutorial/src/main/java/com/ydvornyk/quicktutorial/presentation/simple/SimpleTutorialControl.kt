package com.ydvornyk.quicktutorial.presentation.simple

import android.content.Context
import android.graphics.PorterDuff
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ydvornyk.quicktutorial.R
import com.ydvornyk.quicktutorial.TutorialConfig
import com.ydvornyk.quicktutorial.model.TutorialContent
import com.ydvornyk.quicktutorial.presentation.control.BaseTutorialControl
import com.ydvornyk.quicktutorial.util.ThemeUtil
import com.ydvornyk.quicktutorial.util.VersionUtil


/**
 * Created by yuriidvornyk on 4/18/18.
 */

open class SimpleTutorialControl : BaseTutorialControl {

    private lateinit var previousLayout: ViewGroup
    private lateinit var previousImageButton: ImageView
    private lateinit var previousTextButton: TextView
    private lateinit var nextLayout: ViewGroup
    private lateinit var nextImageButton: ImageView
    private lateinit var nextTextButton: TextView
    private var progressLayout: ViewGroup? = null
    private var currentPageText: TextView? = null
    private var pageDividerText: TextView? = null
    private var pagesCountText: TextView? = null

    override val layoutId: Int
        get() = R.layout.view_control_simple

    override val nextButtonId: Int
        get() = R.id.button_next

    override val previousButtonId: Int
        get() = R.id.button_previous

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (progressLayout == null) {
            initializeView()
        }
        setUpRippleEffect()
    }

    override fun setUpControl(@NonNull config: TutorialConfig, @NonNull content: TutorialContent) {
        super.setUpControl(config, content)
        if (progressLayout == null) {
            initializeView()
        }
        setUpButtonContent()
        setUpProgress()
    }

    protected open fun initializeView() {
        previousLayout = findViewById(R.id.button_previous)
        previousImageButton = findViewById(R.id.image_previous)
        previousTextButton = findViewById(R.id.text_previous)
        nextLayout = findViewById(R.id.button_next)
        nextImageButton = findViewById(R.id.image_next)
        nextTextButton = findViewById(R.id.text_next)
        progressLayout = findViewById(R.id.layout_progress)
    }

    private fun setUpButtonContent() {
        setUpButtonContent(nextImageButton, nextTextButton, config.nextButtonImage, config.nextButtonText)
        setUpButtonContent(previousImageButton, previousTextButton, config.previousButtonImage, config.previousButtonText)
    }

    private fun setUpButtonContent(imageButton: ImageView, textButton: TextView, @DrawableRes drawable: Int?, text: String?) {
        if (drawable != null) {
            imageButton.setImageDrawable(ContextCompat.getDrawable(context, drawable))
            imageButton.visibility = View.VISIBLE
            setImageForegroundIfPossible(imageButton)
        } else if (text != null) {
            textButton.text = text
            textButton.visibility = View.VISIBLE
        }
    }

    private fun setUpRippleEffect() {
        if (VersionUtil.isLollipopOrHigher()) {
            previousLayout.background = ThemeUtil.getStyleableDrawable(context, R.attr.selectableItemBackgroundBorderless)
            nextLayout.background = ThemeUtil.getStyleableDrawable(context, R.attr.selectableItemBackgroundBorderless)
        }
    }

    private fun setUpProgress() {
        // https://stackoverflow.com/questions/32163918/programmatically-change-color-of-shape-in-layer-list
        if (config.useNumericProgress) {
            currentPageText = findViewById(R.id.text_current_page)
            pageDividerText = findViewById(R.id.text_page_divider)
            pagesCountText = findViewById(R.id.text_pages_count)

            pagesCountText!!.text = content.pages.size.toString()
            if (config.numericDivider != null) {
                pageDividerText!!.text = config.numericDivider
            } else {
                pageDividerText!!.text = context.getString(R.string.progress_divider)
            }
        } else {
            progressLayout!!.removeAllViews()
            for (i in 0 until content.pages.size) {
                val imageView = ImageView(context)
                imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_progress_unchecked))
                setImageForegroundIfPossible(imageView)
                progressLayout!!.addView(imageView)
            }
        }
    }

    override fun onContentChanged(currentPage: Int) {
        if (config.useNumericProgress) {
            currentPageText!!.text = (currentPage + 1).toString()
        } else {
            for (i in 0 until content.pages.size) {
                val imageView = progressLayout!!.getChildAt(i) as ImageView
                if (i <= currentPage) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_progress_checked))
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_progress_unchecked))
                }
                setImageForegroundIfPossible(imageView)
            }
        }

        when (currentPage) {
            0 -> {
                setUpFirstPageButton()
                if (content.pages.size == 1) {
                    setUpLastPageButton()
                }
            }
            content.pages.size - 1 -> setUpLastPageButton()
            else -> {
                previousLayout.visibility = View.VISIBLE
                nextLayout.visibility = View.VISIBLE
                setUpButtonContent()
            }
        }
    }

    private fun setUpLastPageButton() {
        when {
            config.shouldHideCompleteButton() -> nextLayout.visibility = View.INVISIBLE
            config.completeButtonText != null -> {
                nextTextButton.text = config.nextButtonText
                nextTextButton.visibility = View.VISIBLE
                nextImageButton.visibility = View.INVISIBLE
            }
            config.completeButtonImage != null -> {
                nextImageButton.setImageDrawable(ContextCompat.getDrawable(context, config.completeButtonImage!!))
                nextImageButton.visibility = View.VISIBLE
                nextTextButton.visibility = View.INVISIBLE
            }
        }
    }

    private fun setUpFirstPageButton() {
        when {
            config.shouldHideDismissButton() -> previousLayout.visibility = View.INVISIBLE
            config.dismissButtonText != null -> {
                previousTextButton.text = config.previousButtonText
                previousTextButton.visibility = View.VISIBLE
                previousImageButton.visibility = View.INVISIBLE
            }
            config.dismissButtonImage != null -> {
                previousImageButton.setImageDrawable(ContextCompat.getDrawable(context, config.dismissButtonImage!!))
                previousImageButton.visibility = View.VISIBLE
                previousTextButton.visibility = View.INVISIBLE
            }
        }
        if (config.nextButtonImage != null) {
            setImageForegroundIfPossible(nextImageButton)
        }
    }

    private fun setImageForegroundIfPossible(imageButton: ImageView) {
        val colorValue = TypedValue()
        if (context.theme.resolveAttribute(R.attr.controlForeground, colorValue, true)) {
            imageButton.setColorFilter(ContextCompat.getColor(context, colorValue.resourceId), PorterDuff.Mode.MULTIPLY)
        }
    }
}