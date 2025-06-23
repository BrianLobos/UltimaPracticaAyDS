package com.example.parcialvuelosayd.data.external

import com.example.parcialvuelosayd.model.BoundingBox

interface OpenSkyApi{
    fun get(pais: BoundingBox): MutableList<String>
}

class OpenSkyApiImpl(
    val apiClient: ApiClient,
    val vuelosParser: VuelosParser
) : OpenSkyApi{

    override fun get(pais: BoundingBox): MutableList<String> {
        val response = apiClient.getJsonFromUrl(getUrl(pais))
        return vuelosParser.parseVuelos(response)
    }

    private fun getUrl(pais: BoundingBox) = "https://opensky-network.org/api/states/all?lamin=${pais.latMin}&lomin=${pais.lonMin}&lamax=${pais.latMax}&lomax=${pais.lonMax}"
}