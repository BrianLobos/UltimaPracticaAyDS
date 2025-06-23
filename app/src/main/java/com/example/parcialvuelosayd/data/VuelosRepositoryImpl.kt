package com.example.parcialvuelosayd.data

import com.example.parcialvuelosayd.data.external.OpenSkyApi
import com.example.parcialvuelosayd.data.local.LocalStorage
import com.example.parcialvuelosayd.model.BoundingBox
import com.example.parcialvuelosayd.model.VuelosRepository

class VuelosRepositoryImpl(
    val localStorage: LocalStorage,
    val externalData: OpenSkyApi
): VuelosRepository {
    val paisCoordenadas = mapOf(
        "Argentina" to BoundingBox(-55.0, -20.0, -75.0, -53.0),
        "Chile" to BoundingBox(-56.0, -17.0, -75.0, -66.0),
        "Brasil" to BoundingBox(-35.0, 5.0, -74.0, -35.0),
        "Perú" to BoundingBox(-18.0, 1.0, -82.0, -68.0)
    )

    override fun fetchVuelos(pais: String): MutableList<String> {
        val localValue = localStorage.getVuelos(pais)
        if(localValue != null) {
            return localValue
        } else {
            val result = paisCoordenadas[pais]?.let { externalData.get(it) }
            localStorage.saveVuelos(pais,result)
            return result!!
        }
    }

    override fun getPaises(): List<String> =
        listOf("Argentina", "Chile", "Brasil", "Perú")
}