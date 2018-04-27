package com.ydvornyk.quicktutorial.presentation.simple

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.ydvornyk.quicktutorial.R
import com.ydvornyk.quicktutorial.TutorialConfig
import com.ydvornyk.quicktutorial.presentation.control.BaseTutorialControl

/**
 * Created by yuriidvornyk on 4/18/18.
 */

open class SimpleTutorialControl : BaseTutorialControl {

    private var previousLayout: ViewGroup? = null

    private var previousImageButton: ImageView? = null
    private var previousTextButton: TextView? = null
    private var nextLayout: ViewGroup? = null
    private var nextImageButton: ImageView? = null
    private var nextTextButton: TextView? = null
    private var progressLayout: ViewGroup? = null
    private var config: TutorialConfig? = null

    override val layoutId: Int
        get() = R.layout.view_control_simple

    override val nextButtonId: Int
        get() = R.id.button_next

    override val previousButtonId: Int
        get() = R.id.button_previous

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (progressLayout == null) {
            initializeView()
        }
    }

    override fun setUpControl(config: TutorialConfig) {
        this.config = config
        if (progressLayout == null) {
            initializeView()
        }
        setUpButtonContent()
    }

    override fun onContentChanged(currentPage: Int, totalPages: Int) {
        if (progressLayout == null) {
            initializeView()
        }
        progressLayout!!.removeAllViews()
        for (i in 0 until totalPages) {
            val imageView = ImageView(context)
            imageView.setImageDrawable(if (i <= currentPage)
                ContextCompat.getDrawable(context, R.drawable.default_progress_checked)
            else
                ContextCompat.getDrawable(context, R.drawable.default_progress_unchecked))
            progressLayout!!.addView(imageView)
        }

        if (currentPage == 0) {
            if (config!!.shouldHideDismissButton()) {
                previousLayout!!.visibility = View.INVISIBLE
            } else if (config!!.dismissButtonText != null) {
                previousTextButton!!.text = config!!.previousButtonText
                previousTextButton!!.visibility = View.VISIBLE
                previousImageButton!!.visibility = View.INVISIBLE
            } else {
                previousImageButton!!.setImageDrawable(ContextCompat.getDrawable(context, config!!.dismissButtonImage!!))
                previousImageButton!!.visibility = View.VISIBLE
                previousTextButton!!.visibility = View.INVISIBLE
            }
        } else if (currentPage == totalPages - 1) {
            if (config!!.shouldHideCompleteButton()) {
                nextLayout!!.visibility = View.INVISIBLE
            } else if (config!!.completeButtonText != null) {
                nextTextButton!!.text = config!!.nextButtonText
                nextTextButton!!.visibility = View.VISIBLE
                nextImageButton!!.visibility = View.INVISIBLE
            } else {
                nextImageButton!!.setImageDrawable(ContextCompat.getDrawable(context, config!!.completeButtonImage!!))
                nextImageButton!!.visibility = View.VISIBLE
                nextTextButton!!.visibility = View.INVISIBLE
            }
        } else {
            previousLayout!!.visibility = View.VISIBLE
            nextLayout!!.visibility = View.VISIBLE
            setUpButtonContent()
        }
    }

    protected fun initializeView() {
        previousLayout = findViewById(R.id.button_previous)
        previousImageButton = findViewById(R.id.image_previous)
        previousTextButton = findViewById(R.id.text_previous)
        nextLayout = findViewById(R.id.button_next)
        nextImageButton = findViewById(R.id.image_next)
        nextTextButton = findViewById(R.id.text_next)
        progressLayout = findViewById(R.id.layout_progress)
    }

    private fun setUpButtonContent() {
        if (config!!.nextButtonImage != null) {
            nextImageButton!!.setImageDrawable(ContextCompat.getDrawable(context, config!!.nextButtonImage!!))
            nextImageButton!!.visibility = View.VISIBLE
        } else if (config!!.nextButtonText != null) {
            nextTextButton!!.text = config!!.nextButtonText
            nextTextButton!!.visibility = View.VISIBLE
        }
        if (config!!.previousButtonImage != null) {
            previousImageButton!!.setImageDrawable(ContextCompat.getDrawable(context, config!!.previousButtonImage!!))
            previousImageButton!!.visibility = View.VISIBLE
        } else if (config!!.previousButtonText != null) {
            previousTextButton!!.text = config!!.previousButtonText
            previousTextButton!!.visibility = View.VISIBLE
        }
    }
}
