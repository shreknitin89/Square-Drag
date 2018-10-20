package com.plastic.ndasari.squaredrag.network

import com.plastic.ndasari.squaredrag.model.DateData
import retrofit2.Call
import retrofit2.http.GET


/**
 * Created by ndasari on 19 Oct 2018
 */
interface EndPointInterface {
    @GET("/")
    fun getDateSeconds(): Call<DateData>
}