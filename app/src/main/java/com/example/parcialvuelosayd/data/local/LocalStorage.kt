package com.example.parcialvuelosayd.data.local

import androidx.core.content.edit
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface LocalStorage{
    fun saveVuelos(pais:String, vuelos: MutableList<String>?)
    fun getVuelos(pais: String): MutableList<String>?
}

class LocalStorageImpl(
    private val sharedPreferences: SharedPreferences
): LocalStorage{
    override fun saveVuelos(pais: String, vuelos: MutableList<String>?) {
        val gson = Gson()
        sharedPreferences.edit { putString(pais,gson.toJson(vuelos)) }
    }

    override fun getVuelos(pais: String): MutableList<String>? {
        val savedValue = sharedPreferences.getString(pais, null) ?: return null

        val gson = Gson()
        return try {
            gson.fromJson(savedValue,object : TypeToken<MutableList<String>>(){}.type)
        } catch (e: Exception) {
            mutableListOf()
        }
    }

}