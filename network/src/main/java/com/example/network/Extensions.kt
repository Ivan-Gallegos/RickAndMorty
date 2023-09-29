package com.example.network

import android.util.Log
import retrofit2.Response

fun <T> Response<T>.logErrorResponse(tag : String? ) {
    Log.e(
        tag, "${code()}\n" +
                "${errorBody()?.string()}"+
                "${message()}\n" +
                "${raw()}\n"
    )
}
