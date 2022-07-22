package com.imb.sportapp.models

sealed class Resource<T>(
    val data : T?,
    val errMsg : String?
){
    class SUCCESS<T>(val d : T) : Resource<T>(d, null)
    class ERROR<T>(val msg : String) : Resource<T>(null, msg)
}
