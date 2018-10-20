package com.plastic.ndasari.squaredrag.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by ndasari on 19 Oct 2018
 */
class ServiceGenerator {

    object Companion {
        private const val sConnectionTimeout: Long = 30
        private const val sReadTimeout: Long = 30
        private val httpClient = OkHttpClient.Builder()
        fun <S> createService(serviceClass: Class<S>, baseUrl: String): S {
            val gson = GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .setVersion(1.0)
                .create()

            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = httpClient.connectTimeout(sConnectionTimeout, TimeUnit.SECONDS)
                .readTimeout(sReadTimeout, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .hostnameVerifier { hostname, _ ->
                    hostname.matches("dateandtimeasjson.appspot.com".toRegex())
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(serviceClass)
        }
    }
}
