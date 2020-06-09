package com.example.urbandictionary.model.network

import com.example.urbandictionary.model.response.UrbanResponse
import io.reactivex.Single

interface UrbanRepository {
    fun getDefinitionListNetwork(term: String): Single<UrbanResponse>
}