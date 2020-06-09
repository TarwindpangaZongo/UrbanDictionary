package com.example.urbandictionary.model.network

import com.example.urbandictionary.model.network.remote.UrbanRestService
import com.example.urbandictionary.model.response.UrbanResponse
import com.example.urbandictionary.model.response.Term
import io.reactivex.Single

class MockUrbanRestServiceImpl(): UrbanRestService {
    override fun getDefinitions(term: String): Single<UrbanResponse> {
        val term1 = Term("term1", 1, 1)
        val term2 = Term("term2", 2, 2)
        val term3 = Term("term3", 3, 3)
        val terms = mutableListOf(term1, term2, term3)
        val response = UrbanResponse(terms)
        return Single.just(response)
    }
}