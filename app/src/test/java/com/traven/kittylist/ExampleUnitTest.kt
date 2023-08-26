package com.traven.kittylist

import com.traven.kittylist.domain.Repository
import com.traven.kittylist.model.KittyDTO
import com.traven.kittylist.ui.MyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    private var fakeRepository: FakeRepository = FakeRepository()
    private var viewModel: MyViewModel = MyViewModel(fakeRepository)

    @Test
    fun filteringIsCorrect() {
        runBlocking {
            delay(5000L) // guarantees to finish the async loading before assertion

            viewModel.dataList.forEach {
                assert(!it.url.contains(".gif"))
                assert(it.width >= 200 )
            }
        }
    }
    @Test
    fun pagingIsCorrect() {
        runBlocking {
            delay(5000L) // guarantees to finish the async loading before assertion
            for (i in 0..20) {
                viewModel.updateScrollPosition(i)
            }
            val expectedNewListSize = 40
            delay(5000L) // guarantees to finish the async loading before assertion

            assert(viewModel.dataList.size == expectedNewListSize)
            assert(!viewModel.errorState.value)
        }
    }

}