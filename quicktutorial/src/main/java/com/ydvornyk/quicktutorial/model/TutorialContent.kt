package com.ydvornyk.quicktutorial.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by yuriidvornyk on 4/18/18.
 */
@Parcelize
class TutorialContent(var pages: MutableList<TutorialPage>) : Parcelable {

    constructor() : this(mutableListOf())

    fun addPage(page: TutorialPage) {
        pages.add(page)
    }
}