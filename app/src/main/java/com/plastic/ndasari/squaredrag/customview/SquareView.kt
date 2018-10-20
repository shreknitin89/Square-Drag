package com.plastic.ndasari.squaredrag.customview

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet


/**
 * Created by ndasari on 19 Oct 2018
 */
class SquareView : AppCompatTextView {
    private var squareDim = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val h = this.measuredHeight
        val w = this.measuredWidth
        squareDim = getAbsoluteSize(w, h)
        setMeasuredDimension(squareDim, squareDim)
    }

    fun getAbsoluteSize(widthMeasureSpec: Int, heightMeasureSpec: Int): Int {
        return Math.max(widthMeasureSpec, heightMeasureSpec)
    }
}