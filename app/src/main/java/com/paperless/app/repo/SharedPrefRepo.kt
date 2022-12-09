package com.paperless.app.repo

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import timber.log.Timber

class SharedPrefRepo(context: Context) {
    val sharedPref : SharedPreferences
    val moshi: Moshi
    init {
        sharedPref = context.getSharedPreferences("PAPERLESS_REPO", Context.MODE_PRIVATE)
        moshi = Moshi.Builder()
            .build()
    }

    inline fun <reified T>saveSharedPref(key: String, data : T ){
        Timber.d("String save data - ${moshi.objectToJson(data)}")
        sharedPref.edit().putString(key,moshi.objectToJson(data) ).commit()
    }
    inline fun <reified T>getSharedPref(key: String) : T?{
        Timber.d("String data - ${sharedPref.getString(key,null)}")
        return sharedPref.getString(key,null)?.let { moshi.jsonToObject(it) }
    }



    companion object{
        val USER_PROFILE = "user_profile"
    }
}


/*
* [Moshi] extension to transform an object to json
* */
inline fun <reified T> Moshi.objectToJson(data: T): String =
    adapter(T::class.java).toJson(data)

/*
* [Moshi] extension to transform json to an object
* */
inline fun <reified T> Moshi.jsonToObject(json: String): T? =
    adapter(T::class.java).fromJson(json)