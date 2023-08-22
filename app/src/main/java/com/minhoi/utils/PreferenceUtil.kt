package com.minhoi.utils

import android.content.Context
import android.content.SharedPreferences
//    MODE_PRIVATE : 생성한 Application에서만 사용 가능
//    MODE_WORLD_READABLE : 외부 App에서 사용 가능, 하지만 읽기만 가능
//    MODE_WORLD_WRITEABLE : 외부 App에서 사용 가능, 읽기/쓰기 모두 가능

class PreferenceUtil(context : Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getToken(key: String, defValue: String):String{
        return preferences.getString(key,defValue).toString()
    }

    fun setToken(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }
}