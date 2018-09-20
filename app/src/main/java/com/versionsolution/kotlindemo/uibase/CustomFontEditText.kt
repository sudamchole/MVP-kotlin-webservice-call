package com.kotlin.kotlinprojectbase.uibase

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.EditText

internal class CustomFontEditText : EditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun init() {

      /* ----------------- uncomment this when you added the font-------------------*/

//        val tf = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Regular.ttf")
 //       setTypeface(tf, 0)
    }


    //    // method to change font settings
    //    void setTypeface(TypeFace tf) {
    //        this.setTypeFace(tf);
    //    }
    //add whatever method you want
}
