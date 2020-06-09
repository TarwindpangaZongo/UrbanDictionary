package com.example.urbandictionary.util

import androidx.test.espresso.IdlingResource
import com.example.urbandictionary.BuildConfig

/**
 * Contains a static reference to [IdlingResource], only available in the 'mock' build type.
 */
object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"
    private val mCountingIdlingResource =
        SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        if (BuildConfig.DEBUG) {
            mCountingIdlingResource.increment()
        }
    }

    fun decrement() {
        if (BuildConfig.DEBUG) {
            mCountingIdlingResource.decrement()
        }
    }

    val idlingResource: IdlingResource
        get() = mCountingIdlingResource
}