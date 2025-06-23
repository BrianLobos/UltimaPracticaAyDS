package com.example.parcialvuelosayd.controller

import com.example.parcialvuelosayd.data.VuelosRepositoryImpl
import com.example.parcialvuelosayd.model.VuelosRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Test

class MainScreenControllerImplTest{
    private val vuelosRepository : VuelosRepository = mockk()
    private val mainScreenController = MainScreenControllerImpl(vuelosRepository)

    @Test
    fun `on fetchVuelos notify listaDeVuelosObservable`(){
        val listaDeVuelos = mutableListOf("vuelo1","vuelo2")
        every{ vuelosRepository.fetchVuelos("Argentina")} returns listaDeVuelos

        val vuelosTester: (Pair<String,MutableList<String>>) -> Unit = mockk(relaxed = true)

        mainScreenController.listaDeVuelosObservable.subscribe(vuelosTester)

        mainScreenController.fetchVuelos("Argentina")

        verify { vuelosTester(Pair("Argentina",listaDeVuelos)) }

    }
}