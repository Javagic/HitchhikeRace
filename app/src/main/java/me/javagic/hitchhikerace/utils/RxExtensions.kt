package me.javagic.hitchhikerace.utils

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import timber.log.Timber


@CheckReturnValue
fun <T> Single<T>.subscribeWithErrorLogConsumer(onSuccess: (T) -> Unit = { }): Disposable {
    return subscribe(onSuccess, { log(it) })
}

@CheckReturnValue
fun <T> Maybe<T>.subscribeWithErrorLogConsumer(onSuccess: (T) -> Unit = { }): Disposable {
    return subscribe(onSuccess, { log(it) })
}

@CheckReturnValue
fun <T> Observable<T>.subscribeWithErrorLogConsumer(onSuccess: (T) -> Unit = { }): Disposable {
    return subscribe(onSuccess, { log(it) })
}

@CheckReturnValue
fun Completable.subscribeWithErrorLogConsumer(onSuccess: () -> Unit = { }): Disposable {
    return subscribe(onSuccess, { log(it) })
}

private fun log(throwable: Throwable) {
    Timber.e(throwable)
}
