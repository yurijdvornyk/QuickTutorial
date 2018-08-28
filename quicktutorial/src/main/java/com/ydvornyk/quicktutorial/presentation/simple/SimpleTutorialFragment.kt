package com.ydvornyk.quicktutorial.presentation.simple

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ydvornyk.quicktutorial.R
import com.ydvornyk.quicktutorial.model.TutorialPage
import com.ydvornyk.quicktutorial.model.content.ContentItem
import com.ydvornyk.quicktutorial.presentation.BaseTutorialFragment


/**
 * Created by yuriidvornyk on 4/18/18.
 */

class SimpleTutorialFragment : BaseTutorialFragment() {

    private var content: TutorialPage? = null

    private lateinit var title: TextView
    private lateinit var layout: LinearLayout

    override val layoutRes: Int
        get() = R.layout.fragment_quick_tutorial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = arguments!!.getParcelable(ARGUMENT_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        title = view!!.findViewById(R.id.text_title)
        layout = view.findViewById(R.id.layout_content)

        setLayout()
        return view
    }

    private fun setLayout() {
        if (content == null) {
            return
        }

        if (content!!.title != null) {
            title.visibility = View.VISIBLE
            title.text = content!!.title
        }

        when (content!!.contentOrientation) {
            TutorialPage.ContentOrientation.HORIZONTAL -> layout.orientation = LinearLayout.HORIZONTAL
            TutorialPage.ContentOrientation.VERTICAL -> layout.orientation = LinearLayout.VERTICAL
        }

        layout.removeAllViews()
        for (item in content!!.content) {
            when (item.type) {
                ContentItem.ContentItemType.TEXT -> addTextLayout(item.content as String)
                ContentItem.ContentItemType.DRAWABLE -> addDrawableLayout(item.content as Int)
                ContentItem.ContentItemType.IMAGE -> addImageLayout(item.content as ByteArray)
                ContentItem.ContentItemType.LAYOUT -> addCustomViewLayout(item.content as Int)
            }
        }
        for (i in 0 until layout.childCount) {
            val layoutParams = layout.getChildAt(i).layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 1f
            layout.getChildAt(i).layoutParams = layoutParams
        }
        layout.weightSum = content!!.content.size.toFloat()
    }

    private fun addTextLayout(value: String) {
        val textView = TextView(context)
        textView.text = value
        layout.addView(textView)
    }

    private fun addDrawableLayout(@DrawableRes value: Int) {
        val imageView = ImageView(context)
        imageView.setImageDrawable(ContextCompat.getDrawable(context!!, value))
        layout.addView(imageView)
    }

    private fun addImageLayout(value: ByteArray) {
        val imageView = ImageView(context)
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(value, 0, value.size))
        layout.addView(imageView)
    }

    private fun addCustomViewLayout(@LayoutRes value: Int) {
        layout.addView(LayoutInflater.from(context).inflate(value, layout, false))
    }

    companion object {

        const val ARGUMENT_PAGE = "argument_page"

        fun newInstance(page: TutorialPage): SimpleTutorialFragment {
            val fragment = SimpleTutorialFragment()
            val args = Bundle()
            args.putParcelable(ARGUMENT_PAGE, page)
            fragment.arguments = args
            return fragment
        }
    }
}
