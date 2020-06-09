package com.example.urbandictionary.model.network

import com.example.urbandictionary.model.network.remote.UrbanRestService
import com.example.urbandictionary.model.response.UrbanResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class UrbanRepositoryImpl(
    private val urbanRestService: UrbanRestService
) : UrbanRepository {

    override fun getDefinitionListNetwork(term: String): Single<UrbanResponse> {
        return urbanRestService
            .getDefinitions(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
