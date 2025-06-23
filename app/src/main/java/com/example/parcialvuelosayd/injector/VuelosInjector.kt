package com.example.parcialvuelosayd.injector

import android.content.Context
import android.content.SharedPreferences
import com.example.parcialvuelosayd.controller.MainScreenController
import com.example.parcialvuelosayd.controller.MainScreenControllerImpl
import com.example.parcialvuelosayd.data.VuelosRepositoryImpl
import com.example.parcialvuelosayd.data.external.ApiClientImpl
import com.example.parcialvuelosayd.data.external.OpenSkyApiImpl
import com.example.parcialvuelosayd.data.external.VuelosParser
import com.example.parcialvuelosayd.data.external.VuelosParserImpl
import com.example.parcialvuelosayd.data.local.LocalStorageImpl
import com.example.parcialvuelosayd.model.VuelosRepository

object VuelosInjector {
    lateinit var controller: MainScreenController

    fun initInjector(context: Context){
        val apiClient = ApiClientImpl()
        val vuelosParser = VuelosParserImpl()
        val openSkyApi = OpenSkyApiImpl(apiClient,vuelosParser)

        val sharedPreferences = context.getSharedPreferences("MY_SHARED_PREFERENCES", Context.MODE_PRIVATE)
        val localStorage = LocalStorageImpl(sharedPreferences)

        val vuelosRepository = VuelosRepositoryImpl(localStorage,openSkyApi)

        controller = MainScreenControllerImpl(vuelosRepository)

    }
}