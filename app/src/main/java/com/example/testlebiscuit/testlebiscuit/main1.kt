package com.example.testlebiscuit.testlebiscuit

import android.content.Context
import android.util.AttributeSet
import com.xw.repo.supl.ISlidingUpPanel


class main1(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : Base(context, attrs, defStyleAttr) {

    fun onSliding(panel: ISlidingUpPanel<*>, top: Int, dy: Int, slidedProgress: Float) {
        super.onSliding(panel, top, dy, slidedProgress)

        if (dy < 0) { // 向上
            val radius = radius
            if (radius > 0 && MAX_RADIUS >= top) {
                setRadius(top)
            }

            var alpha = mCollapseLayout.getAlpha()
            if (alpha > 0f && top < 200) {
                alpha += dy / 200.0f
                mCollapseLayout.setAlpha(if (alpha < 0) 0 else alpha) // 逐隐
            }

            alpha = mMenuLayout.getAlpha()
            if (alpha < 1f && top < 100) {
                alpha -= dy / 100.0f
                mMenuLayout.setAlpha(if (alpha > 1) 1 else alpha) // 逐显
            }

            alpha = mExpendLayout.getAlpha()
            if (alpha < 1f) {
                alpha -= dy / 1000.0f
                mExpendLayout.setAlpha(if (alpha > 1) 1 else alpha) // 逐显
            }
        } else { // 向下
            var radius = radius
            if (radius < MAX_RADIUS) {
                radius += top.toFloat()
                setRadius(if (radius > MAX_RADIUS) MAX_RADIUS else radius)
            }

            var alpha = mCollapseLayout.getAlpha()
            if (alpha < 1f) {
                alpha += dy / 800.0f
                mCollapseLayout.setAlpha(if (alpha > 1) 1 else alpha) // 逐显
            }

            alpha = mMenuLayout.getAlpha()
            if (alpha > 0f) {
                alpha -= dy / 100.0f
                mMenuLayout.setAlpha(if (alpha < 0) 0 else alpha) // 逐隐
            }

            alpha = mExpendLayout.getAlpha()
            if (alpha > 0f) {
                alpha -= dy / 1000.0f
                mExpendLayout.setAlpha(if (alpha < 0) 0 else alpha) // 逐隐
            }
        }
    }

}

