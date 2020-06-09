package com.example.urbandictionary.model.network.remote

import com.example.urbandictionary.model.response.UrbanResponse
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit

interface UrbanRestService {

    companion object {

        val instance: UrbanRestService by lazy {

            //Logging
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            //OkHttp

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                            .addHeader("Accept", "application/json")
//                            .addHeader("Cache-Control", "public, max-age=200" )
                            .addHeader("Request-Type", "Android")
                            .addHeader("Content-Type", "application/json")
                            .addHeader(
                                "x-rapidapi-host",
                                "mashape-community-urban-dictionary.p.rapidapi.com"
                            )
                            .addHeader(
                                "x-rapidapi-key",
                                "a171204d02mshac9ce506da8bf39p13ea7fjsn54442d013ac3"
                            )
                        val request = requestBuilder.build()
                        return chain.proceed(request)
                    }
                }).build()
            //Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://mashape-community-urban-dictionary.p.rapidapi.com")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(UrbanRestService::class.java)
        }
    }

    @GET("/define")
    fun getDefinitions(@Query("term") term: String): Single<UrbanResponse>
}