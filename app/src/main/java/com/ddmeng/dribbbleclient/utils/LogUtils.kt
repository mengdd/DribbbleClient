package com.ddmeng.dribbbleclient.utils

import android.util.Log

object LogUtils {
    private const val TAG = "DRIBBBLE_DEBUG"

    fun d(msg: String) {
        Log.d(TAG, msg)
    }

    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun i(msg: String) {
        Log.i(TAG, msg)
    }

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun e(msg: String) {
        Log.e(TAG, msg)
    }

    fun e(msg: String, tr: Throwable) {
        Log.e(TAG, msg, tr)
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        Log.e(tag, msg, tr)
    }
}
