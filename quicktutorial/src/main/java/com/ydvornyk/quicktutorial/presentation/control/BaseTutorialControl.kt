package com.ydvornyk.quicktutorial.presentation.control

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.ydvornyk.quicktutorial.TutorialConfig

/**
 * Created by yuriidvornyk on 4/17/18.
 */

abstract class BaseTutorialControl : FrameLayout, TutorialContentChangeListener {

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

    open fun setUpControl(config: TutorialConfig) {}

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
