package com.androidcafe.uselectioninfo.remote

import okhttp3.OkHttpClient

class CivicsHttpClient: OkHttpClient() {

    companion object {

        //Place your API Key Here
        const val API_KEY = ""

        fun getClient(): OkHttpClient {
            return Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val url = original
                                .url()
                                .newBuilder()
                                .addQueryParameter("key", API_KEY)
                                .build()
                        val request = original
                                .newBuilder()
                                .url(url)
                                .build()
                        chain.proceed(request)
                    }
                    .build()
        }

    }

}