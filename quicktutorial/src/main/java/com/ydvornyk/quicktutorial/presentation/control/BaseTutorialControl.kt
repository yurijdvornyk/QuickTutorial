package com.ydvornyk.quicktutorial.presentation.control

import android.content.Context
import android.support.annotation.NonNull
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.ydvornyk.quicktutorial.TutorialConfig
import com.ydvornyk.quicktutorial.model.TutorialContent

/**
 * Created by yuriidvornyk on 4/17/18.
 */

abstract class BaseTutorialControl : FrameLayout, TutorialContentChangeListener {


    protected lateinit var config: TutorialConfig
    protected lateinit var content: TutorialContent
    protected var listener: TutorialControlListener? = null

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    fun setControlListener(listener: TutorialControlListener) {
        this.listener = listener
    }

    open fun setUpControl(@NonNull config: TutorialConfig, @NonNull content: TutorialContent) {
        this.config = config
        this.content = content
    }

    protected fun initialize() {
        val view = View.inflate(context, layoutId, parent as ViewGroup?)
        view.findViewById<View>(nextButtonId).setOnClickListener { v ->
            if (listener != null) {
                listener!!.onNextClicked(v)
            }
        }
        view.findViewById<View>(previousButtonId).setOnClickListener { v ->
            if (listener != null) {
                listener!!.onPreviousClicked(v)
            }
        }
        addView(view)
    }
}
