package com.ddmeng.dribbbleclient.utils

import android.util.Log

object LogUtils {
    private const val TAG = "DRIBBBLE_DEBUG"

    fun d(msg: String) {
        Log.d(TAG, msg)
    }

    fun i(msg: String) {
        Log.i(TAG, msg)
    }
}