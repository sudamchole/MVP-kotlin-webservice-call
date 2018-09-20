package com.kotlin.kotlinprojectbase.uibase

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.versionsolution.kotlindemo.R

class CustomProgressDialog(context: Context, private val message: String) : ProgressDialog(context) {
    class CustomProgressDialog(context: Context, private val message: String) : ProgressDialog(context) {

        private val animRotate: Animation
        lateinit var imageViewProgress: ImageView

        init {

            animRotate = AnimationUtils.loadAnimation(context, R.anim.custom_progress_dialog)
            isIndeterminate = true
            setCancelable(false)
        }

        override fun onCreate(savedInstanceState: Bundle) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_view_progress)
            imageViewProgress = findViewById(R.id.image_view_progress) as ImageView
        }

        override fun show() {
            super.show()
            imageViewProgress.startAnimation(animRotate)
        }

        override fun dismiss() {
            super.dismiss()
            animRotate.cancel()
        }
    }
}
