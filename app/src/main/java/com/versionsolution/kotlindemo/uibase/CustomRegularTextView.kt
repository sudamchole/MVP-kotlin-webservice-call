package com.kotlin.kotlinprojectbase.uibase

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

internal class CustomRegularTextView : TextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        /*uncomment once you add the font*/
//        val tf = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Regular.ttf")
     //   setTypeface(tf, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }
}
