package com.kotlin.kotlinprojectbase.utilities

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.*
import android.widget.LinearLayout
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object AnimationUtils {

    fun expand(v: View, heightInDps: Int, duration: Int, resources: Resources?): Animation {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        val targetHeight: Int
        if (heightInDps <= 0 || resources == null)
            targetHeight = v.measuredHeight
        else
            targetHeight = dpToPx(resources, 200)

        v.layoutParams.height = 0
        v.visibility = View.VISIBLE
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (heightInDps <= 0 || resources == null)
                    v.layoutParams.height = if (interpolatedTime == 1f)
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    else
                        (targetHeight * interpolatedTime).toInt()
                else
                    v.layoutParams.height = targetHeight
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        if (duration <= 0)
            a.duration = ((targetHeight / v.context.resources.displayMetrics.density).toInt() * 10).toLong()
        else
            a.duration = duration.toLong()
        v.startAnimation(a)
        return a
    }

    // Horizontal expand
    fun expandHorizontal(v: View, widthInDps: Int, duration: Int, resources: Resources?): Animation {
        v.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val targetWidth: Int
        if (widthInDps <= 0 || resources == null)
            targetWidth = v.measuredWidth
        else
            targetWidth = dpToPx(resources, 200)

        v.layoutParams.height = 0
        v.visibility = View.VISIBLE
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (widthInDps <= 0 || resources == null)
                    v.layoutParams.width = if (interpolatedTime == 1f)
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    else
                        (targetWidth * interpolatedTime).toInt()
                else
                    v.layoutParams.width = targetWidth
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        if (duration <= 0)
            a.duration = ((targetWidth / v.context.resources.displayMetrics.density).toInt() * 10).toLong()
        else
            a.duration = duration.toLong()
        v.startAnimation(a)
        return a
    }


    fun collapse(v: View, heightInDps: Int, duration: Int, resources: Resources): Animation {
        val initialHeight: Int
        //		if(heightInDps <= 0 || resources == null)
        initialHeight = v.measuredHeight
        //		else
        //			initialHeight = dpToPx(resources, heightInDps);

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        if (duration <= 0)
            a.duration = ((initialHeight / v.context.resources.displayMetrics.density).toInt() * 10).toLong()
        else
            a.duration = duration.toLong()

        v.startAnimation(a)
        return a
    }

    // Collapse horizontal
    fun collapseHorizontal(v: View, widthInDps: Int, duration: Int, resources: Resources): Animation {
        val initialWidth: Int
        //		if(heightInDps <= 0 || resources == null)
        initialWidth = v.measuredWidth
        //		else
        //			initialHeight = dpToPx(resources, heightInDps);

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.width = initialWidth - (initialWidth * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        if (duration <= 0)
            a.duration = ((initialWidth / v.context.resources.displayMetrics.density).toInt() * 10).toLong()
        else
            a.duration = duration.toLong()

        v.startAnimation(a)
        return a
    }

    //    public static void activityExit(Activity activity) {
    //        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    //    }
    //
    //    public static void activityEnter(Activity activity) {
    //        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    //    }
    //
    //    public static void activityExitVertical(Activity activity) {
    //        activity.overridePendingTransition(R.anim.slide_top_middle_full, R.anim.slide_middle_bottom_full);
    //    }
    //
    //    public static void activityEnterVertical(Activity activity) {
    //        activity.overridePendingTransition(R.anim.slide_bottom_middle_full, R.anim.slide_middle_top_full);
    //    }

    // Translate animate Views
    fun animateViewFly(viewToAnimate: View, fromX: Float, toX: Float,
                       fromY: Float, toY: Float, typeRelative: Int, animationDuration: Int,
                       animationRepeatCount: Int): Animation {
        viewToAnimate.visibility = View.VISIBLE
        val animation = TranslateAnimation(typeRelative, fromX, typeRelative, toX, typeRelative, fromY, typeRelative, toY)
        animation.duration = animationDuration.toLong()
        animation.repeatCount = animationRepeatCount
        animation.interpolator = LinearInterpolator()
        viewToAnimate.startAnimation(animation)
        return animation
    }

    // convert dp to px
    fun dpToPx(resources: Resources, dp: Int): Int {
        val displayMetrics = resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    //Class to hide / show bottom bar
    class HideShowBottomBar {

        private var view: View? = null
        var isHiding = false
        private var startHide = false
        private var duration = 300

        constructor(view: View) {
            this.view = view
        }

        constructor(view: View, duration: Int) {
            this.view = view
            this.duration = duration
        }

        // Hide bottom bar with animation
        fun hideBottombar(): Animation? {
            if (view!!.visibility != View.GONE && !isHiding && !startHide) {
                startHide = true
                val hide = AnimationUtils.animateViewFly(view!!, 0f, 0f, 0f, 1f, Animation.RELATIVE_TO_SELF, duration, 0)
                hide.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        view!!.visibility = View.GONE
                        isHiding = true
                        startHide = false
                    }
                })
                return hide
            }
            return null
        }

        // show bottom bar with animation
        fun showBottombar(): Animation? {
            if (view!!.visibility == View.GONE && isHiding) {
                val show = AnimationUtils.animateViewFly(view!!, 0f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, duration, 0)
                show.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        view!!.visibility = View.VISIBLE
                        isHiding = false
                    }
                })
                return show
            }
            return null
        }

    }


    //Class to hide / show top bar
    class HideShowTopBar {

        private var view: View? = null
        var isHiding = false
        private var startHide = false
        private var duration = 300

        constructor(view: View) {
            this.view = view
        }

        constructor(view: View, duration: Int) {
            this.view = view
            this.duration = duration
        }

        // Hide top bar with animation
        fun hideTopbar(): Animation? {
            if (view!!.visibility != View.GONE && !isHiding && !startHide) {
                startHide = true
                val hide = AnimationUtils.animateViewFly(view!!, 0f, 0f, 0f, -2f, Animation.RELATIVE_TO_SELF, duration, 0)
                hide.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        view!!.visibility = View.GONE
                        isHiding = true
                        startHide = false
                    }
                })
                return hide
            }
            return null
        }

        // show Top bar with animation
        fun showTopbar(): Animation? {
            if (view!!.visibility == View.GONE && isHiding) {
                val show = AnimationUtils.animateViewFly(view!!, 0f, 0f, -2f, 0f, Animation.RELATIVE_TO_SELF, duration, 0)
                show.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationRepeat(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        view!!.visibility = View.VISIBLE
                        isHiding = false
                    }
                })
                return show
            }
            return null
        }

    }

    // Bounce animation
    fun bounceView(viewToAnimate: View, fromX: Float, toX: Float,
                   fromY: Float, toY: Float, typeRelative: Int, animationDuration: Int,
                   animationRepeatCount: Int): Animation {
        viewToAnimate.visibility = View.VISIBLE
        val animation = TranslateAnimation(typeRelative, fromX, typeRelative, toX, typeRelative, fromY, typeRelative, toY)
        animation.duration = animationDuration.toLong()
        animation.repeatCount = animationRepeatCount
        animation.repeatMode = Animation.REVERSE
        animation.interpolator = BounceInterpolator()
        viewToAnimate.startAnimation(animation)
        return animation
    }


    fun getScaledBitmap(bitmap: Bitmap?, reqWidth: Int, reqHeight: Int): Bitmap? {
        if (bitmap != null) {
            val m = Matrix()
            m.setRectToRect(RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()), RectF(0f, 0f, reqWidth.toFloat(), reqHeight.toFloat()), Matrix.ScaleToFit.CENTER)
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
        }
        return null
    }

    fun getScaledBitmap(bitmap: Bitmap?, reqWidth: Int, reqHeight: Int, m: Matrix): Bitmap? {
        if (bitmap != null) {
            m.setRectToRect(RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()), RectF(0f, 0f, reqWidth.toFloat(), reqHeight.toFloat()), Matrix.ScaleToFit.CENTER)
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
        }
        return null
    }

    fun decodeSampledBitmapFromResourceMemOpt(
            inputStream: InputStream, reqWidth: Int, reqHeight: Int): Bitmap? {

        var byteArr = ByteArray(0)
        val buffer = ByteArray(1024)
        var len: Int = 0
        var count = 0

        try {
            while (true) {
                val line = inputStream.read(buffer)> -1 ?: break
                if (len != 0) {
                    if (count + len > byteArr.size) {
                        val newbuf = ByteArray((count + len) * 2)
                        System.arraycopy(byteArr, 0, newbuf, 0, count)
                        byteArr = newbuf
                    }

                    System.arraycopy(buffer, 0, byteArr, count, len)
                    count += len
                }
            }

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(byteArr, 0, count, options)

            options.inSampleSize = calculateInSampleSize(options, reqWidth,
                    reqHeight)
            options.inPurgeable = true
            options.inInputShareable = true
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.ARGB_8888

            return BitmapFactory.decodeByteArray(byteArr, 0, count, options)

        } catch (e: Exception) {
            e.printStackTrace()

            return null
        }

    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }


        return inSampleSize
    }


    // Download image from url
    fun downloadImage(img_url: String, filePath: String): File? {

        var casted_image: File? = null
        try {
            casted_image = File(filePath)
            if (casted_image.exists()) {
                casted_image.delete()
            }
            casted_image.createNewFile()

            val fos = FileOutputStream(casted_image)

            val url = URL(img_url)
            val connection = url
                    .openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.doOutput = true
            connection.connect()
            val `in` = connection.inputStream

            val buffer = ByteArray(1024)
            var size = 0
            while (true) {

                val line = `in`.read(buffer) > 0 ?: break
                fos.write(buffer, 0, size)
            }
            fos.close()
            return casted_image

        } catch (e: Exception) {

            print(e)
            // e.printStackTrace();

        }

        return casted_image
    }

    //	public static class GenericCollapseExpandAnimator implements ViewTreeObserver.OnPreDrawListener{
    //
    //		LinearLayout layoutToAnimate;
    //		View layoutHeader;
    //		ValueAnimator mAnimator;
    //
    //		public GenericCollapseExpandAnimator(LinearLayout layoutBody, View layoutHeader){
    //			this.layoutToAnimate = layoutBody;
    //			this.layoutHeader = layoutHeader;
    //			this.layoutHeader.getViewTreeObserver().addOnPreDrawListener(this);
    //			this.layoutHeader.setOnClickListener(new View.OnClickListener() {
    //
    //	            @Override
    //	            public void onClick(View v) {
    //	                if (layoutToAnimate.getVisibility()==View.GONE){
    //	                	expand();
    //
    //	                }else{
    //	                	collapse();
    //	                }
    //	            }
    //	        });
    //
    //
    //		}
    //
    //		public boolean isOpen(){
    //			if(layoutToAnimate.getVisibility()==View.VISIBLE)
    //				return true;
    //			else
    //				return false;
    //		}
    //
    //		@Override
    //		public boolean onPreDraw() {
    //			// TODO Auto-generated method stub
    //			layoutToAnimate.getViewTreeObserver().removeOnPreDrawListener(this);
    //			layoutToAnimate.setVisibility(View.GONE);
    //
    //	        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    //			final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    //			layoutToAnimate.measure(widthSpec, heightSpec);
    //
    //			mAnimator = slideAnimator(0, layoutToAnimate.getMeasuredHeight());
    //	        return true;
    //
    //		}
    //
    //		private void expand() {
    //			//set Visible
    //			mAnimator.start();
    //			layoutToAnimate.setVisibility(View.VISIBLE);
    //		}
    //
    //		@SuppressLint("NewApi")
    //		public void collapse() {
    //			int finalHeight = layoutToAnimate.getHeight();
    //
    //			ValueAnimator mAnimator = slideAnimator(finalHeight, 0);
    //
    //			mAnimator.addListener(new Animator.AnimatorListener() {
    //				@Override
    //				public void onAnimationEnd(Animator animator) {
    //					//Height=0, but it set visibility to GONE
    //					layoutToAnimate.setVisibility(View.GONE);
    //				}
    //
    //				@Override
    //				public void onAnimationStart(Animator animator) {
    //				}
    //
    //				@Override
    //				public void onAnimationCancel(Animator animator) {
    //				}
    //
    //				@Override
    //				public void onAnimationRepeat(Animator animator) {
    //				}
    //			});
    //			mAnimator.start();
    //		}
    //
    //
    //		private ValueAnimator slideAnimator(int start, int end) {
    //
    //			ValueAnimator animator = ValueAnimator.ofInt(start, end);
    //
    //
    //			animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
    //				@Override
    //				public void onAnimationUpdate(ValueAnimator valueAnimator) {
    //					//Update Height
    //					int value = (Integer) valueAnimator.getAnimatedValue();
    //
    //					ViewGroup.LayoutParams layoutParams = layoutToAnimate.getLayoutParams();
    //					layoutParams.height = value;
    //					layoutToAnimate.setLayoutParams(layoutParams);
    //				}
    //			});
    //			return animator;
    //		}
    //
    //
    //	}

}
