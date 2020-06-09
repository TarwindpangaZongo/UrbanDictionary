package com.example.urbandictionary.cach

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object termLoader
{
    private lateinit var cache: Cache
private var executorService: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
private val uiHandler: Handler = Handler(Looper.getMainLooper())

fun setCache(cache: Cache) {
    termLoader.cache = cache
}

fun displayImage(url: String, termView: ImageView) {
    val cached = cache.get(url)
    if (cached != null) {
        updateImageView(termView, cached)
        return
    }
    termView.tag = url
    executorService.submit {
        val bitmap: Bitmap? = downloadImage(url)
        if (bitmap != null) {
            if (termView.tag == url) {
                updateImageView(termView, bitmap)
            }
            cache.put(url, bitmap)
        }
    }
}

fun clearCache() {
    cache.clear()
}

private fun updateImageView(termView: ImageView, bitmap: Bitmap) {
    uiHandler.post {
        termView.setImageBitmap(bitmap)
    }
}

private fun downloadImage(url: String): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val url = URL(url)
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        bitmap = BitmapFactory.decodeStream(conn.inputStream)
        conn.disconnect()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bitmap
}
}