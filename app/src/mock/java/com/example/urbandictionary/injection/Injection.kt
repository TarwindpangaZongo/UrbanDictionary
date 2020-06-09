package com.example.urbandictionary.injection

import com.example.urbandictionary.model.network.MockUrbanRestServiceImpl
import com.example.urbandictionary.model.network.UrbanRepository
import com.example.urbandictionary.model.network.UrbanRepositoryImpl
import com.example.urbandictionary.model.network.remote.UrbanRestService

class Injection {
    private var userRestService: UrbanRestService? = null
    fun provideUserRepo(): UrbanRepository {
        return UrbanRepositoryImpl(provideUrbanRestService())
    }

    private fun provideUrbanRestService(): UrbanRestService {
        if (userRestService == null) {
            userRestService = MockUrbanRestServiceImpl()
        }
        return userRestService as UrbanRestService
    }
}