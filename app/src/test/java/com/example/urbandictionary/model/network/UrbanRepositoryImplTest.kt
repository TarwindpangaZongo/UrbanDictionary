package com.example.urbandictionary.model.network

import com.example.urbandictionary.model.network.remote.UrbanRestService
import com.example.urbandictionary.model.response.UrbanResponse
import com.example.urbandictionary.model.response.Word
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UrbanRepositoryImplTest {
    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var urbanRestService: UrbanRestService

    @Test
    fun testTermList() {
        val termList = mutableListOf(
            Word("definition1", 1, 2),
            Word("definition2", 2, 2),
            Word("definition3", 3, 1)
        )
        val response = UrbanResponse(termList)
        Mockito.`when`(urbanRestService.getDefinitions("country")).thenReturn(
            Single.just(response)
        )

        val testObserver: TestObserver<UrbanResponse> =
            urbanRestService.getDefinitions("country").test()

        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
            .assertValue {
                it.list.size == 3

                it.list[0].definition == "definition1"
                it.list[0].thumbs_up == 1
                it.list[0].thumbs_down == 2

                it.list[1].definition == "definition2"
                it.list[1].thumbs_up == 2
                it.list[1].thumbs_down == 2

                it.list[2].definition == "definition3"
                it.list[2].thumbs_up == 3
                it.list[2].thumbs_down == 1
            }
    }
}