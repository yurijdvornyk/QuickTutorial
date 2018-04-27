package com.ydvornyk.quicktutorial.presentation

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by yuriidvornyk on 4/17/18.
 */

abstract class BaseTutorialFragment : Fragment() {

    @get:LayoutRes
    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }
}
