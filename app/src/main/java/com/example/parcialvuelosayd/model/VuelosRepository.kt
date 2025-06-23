package com.example.parcialvuelosayd.model

interface VuelosRepository {
    fun getPaises(): List<String>
    fun fetchVuelos(pais: String): MutableList<String>
}