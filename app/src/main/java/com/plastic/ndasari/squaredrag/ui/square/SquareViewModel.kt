package com.plastic.ndasari.squaredrag.ui.square

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.plastic.ndasari.squaredrag.model.DateData
import com.plastic.ndasari.squaredrag.network.EndPointInterface
import com.plastic.ndasari.squaredrag.network.ServiceGenerator
import retrofit2.Response
import java.util.*


/**
 * Created by ndasari on 19 Oct 2018
 */
class SquareViewModel : ViewModel() {
    private val apiService =
        ServiceGenerator.Companion.createService(EndPointInterface::class.java, "https://dateandtimeasjson.appspot.com")
    var data: MutableLiveData<DateData> = MutableLiveData()
    private var timer: Timer? = null
    private var task: MyTimerTask? = null

    fun getDateData(): LiveData<DateData> {
        if (timer == null)
            timer = Timer()
        if (task == null)
            task = MyTimerTask()
        timer?.schedule(task, 0, 1)
        return data
    }

    fun loadData() {
        apiService.getDateSeconds().enqueue(object : retrofit2.Callback<DateData> {
            override fun onFailure(call: retrofit2.Call<DateData>, t: Throwable) {
                Log.e("Network Failure", "retrofit fetch fail!")
                data.value = DateData("Test Data")
            }

            override fun onResponse(call: retrofit2.Call<DateData>, response: Response<DateData>) {
                val result = response.body()
                data.value = result
            }
        })
    }

    fun stopDataFetch() {
        task?.cancel()
        timer?.cancel()
        timer?.purge()
    }

    private inner class MyTimerTask : TimerTask() {
        override fun run() {
            loadData()
        }
    }
}
