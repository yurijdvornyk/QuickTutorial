package com.ydvornyk.quicktutorial.presentation

import android.app.Activity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.ydvornyk.quicktutorial.R
import com.ydvornyk.quicktutorial.presentation.control.BaseTutorialControl
import com.ydvornyk.quicktutorial.presentation.control.TutorialControlListener

/**
 * Created by yuriidvornyk on 4/17/18.
 */

abstract class BaseTutorialActivity<C : BaseTutorialControl, F : BaseTutorialFragment> : AppCompatActivity(), TutorialControlListener {
    protected lateinit var fragments: MutableList<F>

    var currentPage: Int = 0
        protected set

    protected lateinit var control: C

    private var x1: Float = 0.toFloat()
    private var x2: Float = 0.toFloat()

    val pagesCount: Int
        get() = fragments.size

    @get:LayoutRes
    protected abstract val layoutRes: Int

    @get:IdRes
    protected abstract val controlRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        fragments = mutableListOf()
        control = initializeControl()
        control.setControlListener(this)
        val controlLayout = findViewById<ViewGroup>(controlRes)
        controlLayout.removeAllViews()
        controlLayout.addView(control)
    }

    override fun onNextClicked(view: View) {
        if (currentPage < pagesCount - 1) {
            goToPage(currentPage + 1)
        }
    }

    override fun onPreviousClicked(view: View) {
        if (currentPage > 0) {
            goToPage(currentPage - 1)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            x1 = event.x
        } else if (event.action == MotionEvent.ACTION_UP) {
            x2 = event.x
            val deltaX = x2 - x1
            if (Math.abs(deltaX) > MIN_DISTANCE) {
                if (x2 > x1) {
                    onSwipeRight()
                } else {
                    onSwipeLeft()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    protected open fun onSwipeLeft() {}

    protected open fun onSwipeRight() {}

    protected abstract fun initializeControl(): C

    protected fun onCompleteClicked(view: View) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    protected fun onDismissClicked(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    protected fun goToPage(position: Int) {
        val fragment = fragments[position]
        val transaction = supportFragmentManager
                .beginTransaction()
        onFragmentTransactionBeforeReplace(transaction, position)
        transaction.replace(R.id.layout_content, fragment)
        onFragmentTransactionAfterReplace(transaction)
        transaction.commit()
        currentPage = position
        control.onContentChanged(currentPage)
    }

    protected open fun onFragmentTransactionBeforeReplace(transaction: FragmentTransaction, nextPagePosition: Int) {}

    protected fun onFragmentTransactionAfterReplace(transaction: FragmentTransaction) {}

    companion object {

        private const val MIN_DISTANCE = 100f
    }
}
