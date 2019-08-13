package com.jcardama.moviedatabase.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.AbsListView
import android.widget.ListView

class BottomSheetListView(p_context: Context, p_attrs: AttributeSet) : ListView(p_context, p_attrs) {

    override fun onInterceptTouchEvent(p_event: MotionEvent): Boolean {
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(p_event: MotionEvent): Boolean {
        if (canScrollVertically(this)) {
            parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.onTouchEvent(p_event)
    }

    private fun canScrollVertically(view: AbsListView?): Boolean {

        var canScroll = false

        if (view != null && view.childCount > 0) {

            val isOnTop = view.firstVisiblePosition != 0 || view.getChildAt(0).top != 0
            val isAllItemsVisible = isOnTop && lastVisiblePosition == view.childCount

            if (isOnTop || isAllItemsVisible) canScroll = true
        }

        return canScroll
    }
}
