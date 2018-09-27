package com.example.testlebiscuit.testlebiscuit

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import com.xw.repo.supl.ISlidingUpPanel
import com.xw.repo.supl.SlidingUpPanelLayout
import com.xw.repo.supl.SlidingUpPanelLayout.COLLAPSED

abstract class Base(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : CardView(context, attrs, defStyleAttr), ISlidingUpPanel<Base> {

    protected var MAX_RADIUS: Int = 0

    protected var mExpendedHeight: Int = 0
    protected var mFloor: Int = 0 // 由下至上第几层，最高三层
    protected var mPanelHeight: Int = 0
    protected var mRealPanelHeight: Int = 0
    @SlidingUpPanelLayout.SlideState
    protected var mSlideState = COLLAPSED
    protected var mSlope: Float = 0.toFloat() // 斜率

    fun setFloor(floor: Int) {
        mFloor = floor

        mRealPanelHeight = 0
    }

    fun getFloor(): Int {
        return mFloor
    }

    fun setPanelHeight(panelHeight: Int) {
        mPanelHeight = panelHeight
    }

    fun getRealPanelHeight(): Int {
        if (mRealPanelHeight == 0)
            mRealPanelHeight = mFloor * mPanelHeight

        return mRealPanelHeight
    }

    override fun getPanelView(): Base {
        return this
    }

    override fun getPanelExpandedHeight(): Int {
        if (mExpendedHeight == 0) {
            val dm = Resources.getSystem().displayMetrics
            if (Build.VERSION.SDK_INT > 16) {
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager?.defaultDisplay?.getRealMetrics(dm)
            }
            mExpendedHeight = dm.heightPixels
        }
        return mExpendedHeight
    }

    override fun getPanelCollapsedHeight(): Int {
        return getRealPanelHeight()
    }

    @SlidingUpPanelLayout.SlideState
    override fun getSlideState(): Int {
        return mSlideState
    }

    override fun getPanelTopBySlidingState(@SlidingUpPanelLayout.SlideState slideState: Int): Int {
        if (slideState == SlidingUpPanelLayout.EXPANDED) {
            return 0
        } else if (slideState == COLLAPSED) {
            return panelExpandedHeight - panelCollapsedHeight
        } else if (slideState == SlidingUpPanelLayout.HIDDEN) {
            return panelExpandedHeight
        }
        return 0
    }

    override fun setSlideState(@SlidingUpPanelLayout.SlideState slideState: Int) {
        mSlideState = slideState

        if (mSlideState != SlidingUpPanelLayout.EXPANDED) {
            mSlope = 0f
        }
    }

    override fun onSliding(panel: ISlidingUpPanel<*>, top: Int, dy: Int, slidedProgress: Float) {
        if (panel !== this) {
            val myTop = (panelExpandedHeight + getSlope((panel as Base).getRealPanelHeight()) * top).toInt()
            setTop(myTop)
        }
    }

    fun getSlope(slidingViewRealHeight: Int): Float {
        if (mSlope == 0f) {
            mSlope = -1.0f * getRealPanelHeight() / (panelExpandedHeight - slidingViewRealHeight)
        }

        return mSlope
    }


    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()

        val ss = SavedState(superState)
        ss.mSavedSlideState = mSlideState

        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        mSlideState = ss.mSavedSlideState
    }

    class SavedState : View.BaseSavedState {
        internal var mSavedSlideState: Int = 0

        internal constructor(superState: Parcelable) : super(superState) {}

        private constructor(`in`: Parcel) : super(`in`) {
            mSavedSlideState = `in`.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(mSavedSlideState)
        }

        companion object {

            @SuppressLint("ParcelCreator")
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

    internal fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                Resources.getSystem().displayMetrics).toInt()
    }

}