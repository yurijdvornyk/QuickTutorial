package com.ydvornyk.quicktutorial.presentation.control

import android.view.View

/**
 * Created by yuriidvornyk on 4/19/18.
 */

interface TutorialControlListener {

    fun onNextClicked(view: View)

    fun onPreviousClicked(view: View)
}
