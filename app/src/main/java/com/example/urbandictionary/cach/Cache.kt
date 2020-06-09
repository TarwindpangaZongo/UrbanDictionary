package com.example.urbandictionary.cach

import android.graphics.Bitmap

interface Cache {
    fun put(url: String, bitmap: Bitmap)
    fun get(url: String): Bitmap?
    fun clear()
}