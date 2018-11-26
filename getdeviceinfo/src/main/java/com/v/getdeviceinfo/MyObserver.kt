package com.v.getdeviceinfo

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log

/**
 * @author V
 * @since 2018/10/19
 */
open class MyObserver(lifecycleOwner: LifecycleOwner) : LifecycleObserver {
    private var lifecycle: Lifecycle? = null

    init {
        lifecycle = lifecycleOwner.lifecycle
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        Log.d(TAG, "currentState-->${lifecycle!!.currentState.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        Log.d(TAG, "currentState-->${lifecycle!!.currentState.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Log.d(TAG, "currentState-->${lifecycle!!.currentState.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Log.d(TAG, "currentState-->${lifecycle!!.currentState.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        Log.d(TAG, "currentState-->${lifecycle!!.currentState.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        Log.d(TAG, "currentState-->${lifecycle!!.currentState.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun any() {
        Log.d(TAG, "any-->${lifecycle!!.currentState.name}")
    }

}
