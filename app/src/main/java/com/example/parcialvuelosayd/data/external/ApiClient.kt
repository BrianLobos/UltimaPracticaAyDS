package com.example.parcialvuelosayd.data.external

import java.net.URL

interface ApiClient{
    fun getJsonFromUrl(url: String): String
}

class ApiClientImpl: ApiClient{
    override fun getJsonFromUrl(url: String): String {
        return URL(url).readText()
    }
}