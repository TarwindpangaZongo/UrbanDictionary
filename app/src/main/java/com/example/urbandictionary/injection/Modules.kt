package com.example.urbandictionary.injection

import com.example.urbandictionary.model.network.UrbanRepository
import com.example.urbandictionary.model.network.UrbanRepositoryImpl
import com.example.urbandictionary.viewmodels.UrbanViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { UrbanViewModel(get()) }
}

val repositoryModule = module {
    factory { UrbanRepositoryImpl(get()) as UrbanRepository }
}