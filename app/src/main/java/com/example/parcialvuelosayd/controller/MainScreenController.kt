package com.example.parcialvuelosayd.controller

import android.content.Context
import ayds.observer.Observable
import ayds.observer.Subject
import com.example.parcialvuelosayd.data.VuelosRepositoryImpl
import com.example.parcialvuelosayd.model.VuelosRepository
import kotlin.concurrent.thread

interface MainScreenController{
    val listaDeVuelosObservable: Observable<Pair<String,MutableList<String>>>
    fun fetchVuelos(paisSeleccionado: String)
    fun getPaises(): List<String>
}
class MainScreenControllerImpl(
    val vuelosRepository: VuelosRepository
):MainScreenController{

    override val listaDeVuelosObservable = Subject<Pair<String, MutableList<String>>>()

    override fun fetchVuelos(paisSeleccionado: String) {
        thread {
            val listaDeVuelos = vuelosRepository.fetchVuelos(paisSeleccionado)
            //notifica los vuelos que pasan por sobre el pais seleccionado
            listaDeVuelosObservable.notify(Pair(paisSeleccionado, listaDeVuelos))
        }
    }

    override fun getPaises(): List<String> = vuelosRepository.getPaises()
}