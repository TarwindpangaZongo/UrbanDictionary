package com.example.urbandictionary.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.urbandictionary.injection.BaseApplication
import com.example.urbandictionary.injection.networkModule
import com.example.urbandictionary.injection.repositoryModule
import com.example.urbandictionary.injection.viewModelModule
import org.junit.After
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
//import org.koin.standalone.StandAloneContext.startKoin
//import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest
import org.mockito.MockitoAnnotations
import java.lang.System.setProperty

abstract class BaseTest : KoinTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var BASE_URL: String
    private lateinit var API_KEY: String
    private lateinit var HOST: String

    open fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin{listOf(viewModelModule, repositoryModule, networkModule)}

        BASE_URL = "https://mock.com"
        API_KEY = "api_key"
        HOST = "host"
        setProperty(BaseApplication.DatasourceProperties.BASE_URL_PROPERTY, BASE_URL)
        setProperty(BaseApplication.DatasourceProperties.API_KEY_PROPERTY, API_KEY)
        setProperty(BaseApplication.DatasourceProperties.HOST_PROPERTY, HOST)
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}