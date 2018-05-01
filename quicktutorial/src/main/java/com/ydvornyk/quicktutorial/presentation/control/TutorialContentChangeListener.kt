package com.ydvornyk.quicktutorial.presentation.control

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes

/**
 * Created by yuriidvornyk on 4/19/18.
 */

internal interface TutorialContentChangeListener {

    @get:LayoutRes
    val layoutId: Int

    @get:IdRes
    val nextButtonId: Int

    @get:IdRes
    val previousButtonId: Int

    fun onContentChanged(currentPage: Int)
}
