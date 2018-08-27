package com.ydvornyk.quicktutorial.model.content

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
open class ContentItem protected constructor(
        var content: Serializable,
        val type: ContentItemType
) : Parcelable {

    enum class ContentItemType(val valueType: Class<Any>) {
        TEXT(String.javaClass),
        DRAWABLE(Int.javaClass),
        LAYOUT(Int.javaClass),
        IMAGE(ByteArray::javaClass.javaClass)
    }
}