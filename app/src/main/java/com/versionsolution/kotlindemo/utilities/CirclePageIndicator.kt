package com.kotlin.kotlinprojectbase.utilities

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewConfigurationCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.kotlin.kotlinprojectbase.contract.PageIndicator
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import com.versionsolution.kotlindemo.R


class CirclePageIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = R.attr.buttonStyle) : View(context, attrs, defStyle), PageIndicator {

    var radius: Float = 0.toFloat()
        set(radius) {
            field = radius
            invalidate()
        }
    private val mPaintPageFill = Paint(ANTI_ALIAS_FLAG)
    private val mPaintStroke = Paint(ANTI_ALIAS_FLAG)
    private val mPaintFill = Paint(ANTI_ALIAS_FLAG)
    private var mViewPager: ViewPager? = null
    private var mListener: ViewPager.OnPageChangeListener? = null
    private var mCurrentPage: Int = 0
    private var mSnapPage: Int = 0
    private var mPageOffset: Float = 0.toFloat()
    private var mScrollState: Int = 0
    var orientation: Int = 0
        set(orientation) = when (orientation) {
            HORIZONTAL, VERTICAL -> {
                field = orientation
                requestLayout()
            }

            else -> throw IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.")
        }
    var isCentered: Boolean = false
        set(centered) {
            field = centered
            invalidate()
        }
    var isSnap: Boolean = false
        set(snap) {
            field = snap
            invalidate()
        }

    var mTouchSlop: Int = 0
    private var mLastMotionX = -1f
    private var mActivePointerId = INVALID_POINTER
    private var mIsDragging: Boolean = false

    var pageColor: Int
        get() = mPaintPageFill.getColor()
        set(pageColor) {
            mPaintPageFill.setColor(pageColor)
            invalidate()
        }

    var fillColor: Int
        get() = mPaintFill.getColor()
        set(fillColor) {
            mPaintFill.setColor(fillColor)
            invalidate()
        }

    var strokeColor: Int
        get() = mPaintStroke.getColor()
        set(strokeColor) {
            mPaintStroke.setColor(strokeColor)
            invalidate()
        }

    var strokeWidth: Float
        get() = mPaintStroke.getStrokeWidth()
        set(strokeWidth) {
            mPaintStroke.setStrokeWidth(strokeWidth)
            invalidate()
        }

    init {
        if (isInEditMode) {

            //Load defaults from resources
            val res = resources
            val defaultPageColor = res.getColor(R.color.gray)
            val defaultFillColor = res.getColor(R.color.change_address_oval)
            // final int defaultOrientation = res.getInteger(R.integer.default_circle_indicator_orientation);
            val defaultStrokeColor = res.getColor(R.color.change_address_oval)
            val a = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator, defStyle, 0)


            mPaintPageFill.setStyle(Paint.Style.STROKE)
            mPaintPageFill.setColor(a.getColor(R.styleable.CirclePageIndicator_pageColor, defaultPageColor))
            mPaintStroke.setStyle(Paint.Style.STROKE)
            mPaintStroke.setColor(a.getColor(R.styleable.CirclePageIndicator_strokeColor, defaultStrokeColor))
            //   mPaintStroke.setStrokeWidth(a.getDimension(R.styleable.CirclePageIndicator_strokeWidth, defaultStrokeWidth));
            mPaintFill.setStyle(Paint.Style.FILL)
            mPaintFill.setColor(a.getColor(R.styleable.CirclePageIndicator_fillColor, defaultFillColor))


            val configuration = ViewConfiguration.get(context)
            mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mViewPager == null) {
            return
        }
        val count = mViewPager!!.adapter!!.count
        if (count == 0) {
            return
        }

        if (mCurrentPage >= count) {
            setCurrentItem(count - 1)
            return
        }

        val longSize: Int
        val longPaddingBefore: Int
        val longPaddingAfter: Int
        val shortPaddingBefore: Int
        if (orientation == HORIZONTAL) {
            longSize = width
            longPaddingBefore = paddingLeft
            longPaddingAfter = paddingRight
            shortPaddingBefore = paddingTop
        } else {
            longSize = height
            longPaddingBefore = paddingTop
            longPaddingAfter = paddingBottom
            shortPaddingBefore = paddingLeft
        }

        val threeRadius = radius * 3
        val shortOffset = shortPaddingBefore + radius
        var longOffset = longPaddingBefore + radius
        if (isCentered) {
            longOffset += (longSize - longPaddingBefore - longPaddingAfter) / 2.0f - count * threeRadius / 2.0f
        }

        var dX: Float
        var dY: Float

        var pageFillRadius = radius
        if (mPaintStroke.getStrokeWidth() > 0) {
            pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f
        }

        //Draw stroked circles
        for (iLoop in 0 until count) {
            val drawLong = longOffset + iLoop * threeRadius
            if (orientation == HORIZONTAL) {
                dX = drawLong
                dY = shortOffset
            } else {
                dX = shortOffset
                dY = drawLong
            }
            // Only paint fill if not completely transparent
            if (mPaintPageFill.getAlpha() > 0) {
                canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill)
            }

            // Only paint stroke if a stroke width was non-zero
            if (pageFillRadius != radius) {
                canvas.drawCircle(dX, dY, radius, mPaintStroke)
            }
        }

        //Draw the filled circle according to the current scroll
        var cx = (if (isSnap) mSnapPage else mCurrentPage) * threeRadius
        if (!isSnap) {
            cx += mPageOffset * threeRadius
        }
        if (orientation == HORIZONTAL) {
            dX = longOffset + cx
            dY = shortOffset
        } else {
            dX = shortOffset
            dY = longOffset + cx
        }
        canvas.drawCircle(dX, dY, radius, mPaintFill)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (super.onTouchEvent(ev)) {
            return true
        }
        if (mViewPager == null || mViewPager!!.adapter!!.count == 0) {
            return false
        }

        val action = ev.action and MotionEventCompat.ACTION_MASK
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0)
                mLastMotionX = ev.x
            }

            MotionEvent.ACTION_MOVE -> {
                val activePointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId)
                val x = MotionEventCompat.getX(ev, activePointerIndex)
                val deltaX = x - mLastMotionX

                if (!mIsDragging) {
                    if (Math.abs(deltaX) > mTouchSlop) {
                        mIsDragging = true
                    }
                }

                if (mIsDragging) {
                    mLastMotionX = x
                    if (mViewPager!!.isFakeDragging || mViewPager!!.beginFakeDrag()) {
                        mViewPager!!.fakeDragBy(deltaX)
                    }
                }
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (!mIsDragging) {
                    val count = mViewPager!!.adapter!!.count
                    val width = width
                    val halfWidth = width / 2f
                    val sixthWidth = width / 6f

                    if (mCurrentPage > 0 && ev.x < halfWidth - sixthWidth) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            mViewPager!!.currentItem = mCurrentPage - 1
                        }
                        return true
                    } else if (mCurrentPage < count - 1 && ev.x > halfWidth + sixthWidth) {
                        if (action != MotionEvent.ACTION_CANCEL) {
                            mViewPager!!.currentItem = mCurrentPage + 1
                        }
                        return true
                    }
                }

                mIsDragging = false
                mActivePointerId = INVALID_POINTER
                if (mViewPager!!.isFakeDragging) mViewPager!!.endFakeDrag()
            }

            MotionEventCompat.ACTION_POINTER_DOWN -> {
                val index = MotionEventCompat.getActionIndex(ev)
                mLastMotionX = MotionEventCompat.getX(ev, index)
                mActivePointerId = MotionEventCompat.getPointerId(ev, index)
            }

            MotionEventCompat.ACTION_POINTER_UP -> {
                val pointerIndex = MotionEventCompat.getActionIndex(ev)
                val pointerId = MotionEventCompat.getPointerId(ev, pointerIndex)
                if (pointerId == mActivePointerId) {
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex)
                }
                mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, mActivePointerId))
            }
        }

        return true
    }

    override fun setViewPager(view: ViewPager) {
        if (mViewPager === view) {
            return
        }
        if (mViewPager != null) {
            mViewPager!!.setOnPageChangeListener(null)
        }
        if (view.adapter == null) {
            throw IllegalStateException("ViewPager does not have adapter instance.")
        }
        mViewPager = view
        mViewPager!!.setOnPageChangeListener(this)
        invalidate()
    }

    override fun setViewPager(view: ViewPager, initialPosition: Int) {
        setViewPager(view)
        setCurrentItem(initialPosition)
    }

    override fun setCurrentItem(item: Int) {
        if (mViewPager == null) {
            throw IllegalStateException("ViewPager has not been bound.")
        }
        mViewPager!!.currentItem = item
        mCurrentPage = item
        invalidate()
    }

    override fun notifyDataSetChanged() {
        invalidate()
    }

    override fun onPageScrollStateChanged(state: Int) {
        mScrollState = state

        if (mListener != null) {
            mListener!!.onPageScrollStateChanged(state)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mCurrentPage = position
        mPageOffset = positionOffset
        invalidate()

        if (mListener != null) {
            mListener!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
    }

    override fun onPageSelected(position: Int) {
        if (isSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentPage = position
            mSnapPage = position
            invalidate()
        }

        if (mListener != null) {
            mListener!!.onPageSelected(position)
        }
    }

    override fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
        mListener = listener
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (orientation == HORIZONTAL) {
            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec))
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec))
        }
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec
     * A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private fun measureLong(measureSpec: Int): Int {
        var result: Int
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        if (specMode == View.MeasureSpec.EXACTLY || mViewPager == null) {
            //We were told how big to be
            result = specSize
        } else {
            //Calculate the width according the views count
            val count = mViewPager!!.adapter!!.count
            result = (paddingLeft.toFloat() + paddingRight.toFloat()
                    + count.toFloat() * 2f * radius + (count - 1) * radius + 1f).toInt()
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec
     * A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private fun measureShort(measureSpec: Int): Int {
        var result: Int
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        if (specMode == View.MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize
        } else {
            //Measure the height
            result = (2 * radius + paddingTop.toFloat() + paddingBottom.toFloat() + 1f).toInt()
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        mCurrentPage = savedState.currentPage
        mSnapPage = savedState.currentPage
        requestLayout()
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.currentPage = mCurrentPage
        return savedState
    }

    internal class SavedState : View.BaseSavedState {
        var currentPage: Int = 0

        constructor(superState: Parcelable) : super(superState) {}

        private constructor(`in`: Parcel) : super(`in`) {
            currentPage = `in`.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(currentPage)
        }

        companion object {

            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {
        private val INVALID_POINTER = -1
    }
}
