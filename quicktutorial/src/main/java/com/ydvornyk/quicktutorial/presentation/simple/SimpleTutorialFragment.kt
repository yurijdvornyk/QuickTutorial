package com.ydvornyk.quicktutorial.presentation.simple

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.ydvornyk.quicktutorial.R
import com.ydvornyk.quicktutorial.model.TutorialPage
import com.ydvornyk.quicktutorial.presentation.BaseTutorialFragment

/**
 * Created by yuriidvornyk on 4/18/18.
 */

class SimpleTutorialFragment : BaseTutorialFragment() {

    private var content: TutorialPage? = null

    override val layoutRes: Int
        get() = R.layout.fragment_quick_tutorial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = arguments!!.getParcelable(ARGUMENT_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val title = view!!.findViewById<TextView>(R.id.text_title)
        val text = view.findViewById<TextView>(R.id.text_content)
        val image = view.findViewById<ImageView>(R.id.image_content)
        val layout = view.findViewById<ViewGroup>(R.id.layout_content)

        if (content!!.title != null) {
            title.visibility = View.VISIBLE
            title.text = content!!.title
        }
        if (content!!.imageRes > 0) {
            image.visibility = View.VISIBLE
            image.setImageDrawable(ContextCompat.getDrawable(context!!, content!!.imageRes))
        }
        if (content!!.text != null) {
            text.visibility = View.VISIBLE
            text.text = content!!.text
        }
        if (content!!.layoutRes > 0) {
            layout.removeAllViews()
            View.inflate(context, content!!.layoutRes, layout)
        }

        return view
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
