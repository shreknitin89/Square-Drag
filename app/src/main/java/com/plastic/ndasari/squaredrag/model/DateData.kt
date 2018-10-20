package com.plastic.ndasari.squaredrag.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.plastic.ndasari.squaredrag.exceptions.TimeDataException
import kotlinx.android.parcel.Parcelize
import org.joda.time.format.DateTimeFormat
import java.lang.Exception


/**
 * Created by ndasari on 19 Oct 2018
 */
@Parcelize
data class DateData(
    @SerializedName("datetime")
    val dateTime: String?
) : Parcelable {

    fun secondsData(): String {
        try {
            val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            val displayFormat = DateTimeFormat.forPattern("mm:ss:SS")
            val dt = formatter.parseDateTime(dateTime)
            return displayFormat.print(dt)
        } catch (ex: Exception) {
            throw TimeDataException()
        }
    }
}