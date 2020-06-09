package com.example.urbandictionary.viewmodel

import androidx.lifecycle.Observer
import com.example.urbandictionary.model.network.UrbanRepositoryImpl
import com.example.urbandictionary.model.response.UrbanResponse
import com.example.urbandictionary.model.response.Word
import com.example.urbandictionary.viewmodels.UrbanViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito

import java.net.UnknownHostException

class UrbanViewModelTest : BaseTest() {

    lateinit var viewModel: UrbanViewModel
    @Mock
    lateinit var repository: UrbanRepositoryImpl

    @Mock
    lateinit var observerState: Observer<UrbanViewModel.AppState>

    @Before
    fun setUp() {
        super.before()

        viewModel = UrbanViewModel(repository)
    }

    @Test
    fun sendTerm_getDefinitions_getListOfDefinitions() {
        val definitionList = mutableListOf(
            Word("word1", 10, 20),
            Word("word2", 20, 30),
            Word("word3", 30, 40))

        val urbanResponse = UrbanResponse(definitionList)
        Mockito.`when`(repository.getDefinitionListNetwork("anyString()") ).thenReturn(Single.just(urbanResponse))

        viewModel.stateLiveData.observeForever(observerState)
        viewModel.getDefinitions("anyString()")

        assertEquals(urbanResponse.list.size, 3)
        assertEquals(viewModel.stateLiveData.value, UrbanViewModel.AppState.SUCCESS(urbanResponse.list))
    }

    /*@Test
    fun sendTerm_getDefinitions_getEmptyListOfDefinitions() {
        every { repository.getDefinitionListNetwork(anyString()) } returns Single
            .just(UrbanResponse(mutableListOf()))

        viewModel.stateLiveData.observeForever(observerState)
        viewModel.getDefinitions(anyString())

        assertEquals(viewModel.stateLiveData.value, UrbanViewModel.AppState.ERROR("No Definitions Retrieved"))
    }

    @Test
    fun sendTermNoInternet_getDefinitions_getUknownHostException() {
        every { repository.getDefinitionListNetwork(anyString()) } returns Single.error(UnknownHostException())

        viewModel.stateLiveData.observeForever(observerState)
        viewModel.getDefinitions(anyString())

        assertEquals(viewModel.stateLiveData.value, UrbanViewModel.AppState.ERROR("No Internet Connection"))
    }*/
}