package com.ddmeng.dribbbleclient.utils

import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io

fun <T> observableIoToUi() : ObservableTransformer<T, T> {
    return ObservableTransformer {
        it.subscribeOn(io()).observeOn(mainThread())
    }
}

fun <T> singleIoToUi() : SingleTransformer<T, T> {
    return SingleTransformer {
        it.subscribeOn(io()).observeOn(mainThread())
    }
}
