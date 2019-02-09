package com.satansminion.myhell.therockapp.utilities

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Created by Satans-Minion
 * Fri, 08 February 2019 at 5:53 PM.
 * Project: TheRockApp
 * com.satansminion.myhell.therockapp.utilities
 *
 */
class RecyclerItemClickListener(
    context: Context,
    recyclerView: RecyclerView,
    private val listener: OnRecyclerClickListener
) : RecyclerView.SimpleOnItemTouchListener() {

    private val TAG = "RecyclerItemClickListen"


    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    //    Add gestureDetector
    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
//            return super.onSingleTapUp(e)
            Log.d(TAG, "onSingleTapUp: starts")

            val childView = recyclerView.findChildViewUnder(e.x,e.y)
            Log.d(TAG, "onSingleTapUp calling listener.onItemClick")
            listener.onItemClick(childView!!, recyclerView.getChildAdapterPosition(childView))

            return true
        }

//        If you are using a DoubleTap event then you need to use this function instead of the onSingleTapUp
//        as this one waits to see if the first tap is followed by a double tap.
//        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
//            return super.onSingleTapConfirmed(e)
//        }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG, "onLongPress: starts")
//            super.onLongPress(e)

            val childView = recyclerView.findChildViewUnder(e.x,e.y)
            Log.d(TAG, "onLongPress calling listener.onItemLongClick")
            listener.onItemLongClick(childView!!, recyclerView.getChildAdapterPosition(childView))
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//        return super.onInterceptTouchEvent(rv, e)
        Log.d(TAG, "onInterceptTouchEvent: starts $e")
        val result = gestureDetector.onTouchEvent(e)
        Log.d(TAG, "onInterceptTouchEvent: returning: $result")
//        return super.onInterceptTouchEvent(rv, e)
//        By returning true, we are telling the Android framework that
//        we have dealt with the touch event so it does not need to
//        return true
        return result
    }

//    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent?): Boolean {
//        Log.d(TAG, "onInterceptTouchEvent: starts $e")
//        val result = gestureDetector.onTouchEvent(e)
//        Log.d(TAG, "onInterceptTouchEvent: returning: $result")
////        return super.onInterceptTouchEvent(rv, e)
////        By returning true, we are telling the Android framework that
////        we have dealt with the touch event so it does not need to
////        return true
//        return result
//    }

}