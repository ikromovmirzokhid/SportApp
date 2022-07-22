package com.imb.sportapp.utils

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import java.lang.reflect.Type

const val BUNDLE_DATA = "bundle_data"

fun <T> Bundle.putData(data: T) {
    this.putString(BUNDLE_DATA, Gson().toJson(data))
}

fun <T> Bundle.putData(key: String, data: T) {
    this.putString(key, Gson().toJson(data))
}

fun Bundle.getData(): String? {
    return this.getString(BUNDLE_DATA)
}

fun Bundle.getData(key: String): String? {
    return this.getString(key)
}

inline fun <reified T> Bundle.getData(): T? {
    return Gson().fromJson(getData(), T::class.java)
}

inline fun <reified T> Bundle.getData(key: String): T? {
    return Gson().fromJson(getData(key), T::class.java)
}

inline fun <reified T> Bundle.getDataList(key: String, typeToken: Type): T? {
    return Gson().fromJson(getData(key),typeToken)
}