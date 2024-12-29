package com.soviatul.projectschool.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.56.1/"

    private fun interceptor(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                // Ambil respons JSON sebagai string
                val responseBody = response.body
                val content = responseBody?.string() ?: "Empty Response"

                // Cetak JSON ke Logcat
                Log.d("JSON_RESPONSE", content)

                // Rekonstruksi respons agar tetap bisa digunakan
                response.newBuilder()
                    .body(ResponseBody.create(responseBody?.contentType(), content))
                    .build()
            }
            .build()
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(interceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
