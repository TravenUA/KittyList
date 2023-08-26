package com.traven.kittylist

import com.traven.kittylist.domain.Irepo
import com.traven.kittylist.model.KittyDTO
import kotlin.random.Random

class FakeRepository : Irepo {
    override suspend fun getList(limit: Int, page: Int): List<KittyDTO> {
        val fakeList = mutableListOf<KittyDTO>()

        for (i in 1..limit - 2) {
            fakeList.add(fakeAppropriateImage())
        }
        fakeList.add(fakeInappropriateImage())
        fakeList.add(fakeInappropriateImage())

        return fakeList
    }

    private fun fakeAppropriateImage(): KittyDTO =
        KittyDTO(
            id = java.util.UUID.randomUUID().toString(),
            url = java.util.UUID.randomUUID().toString(),
            name = java.util.UUID.randomUUID().toString(),
            width = Random.nextInt(100, 600),
            height = Random.nextInt(200, 800)
        )

    internal fun fakeInappropriateImage(): KittyDTO =
        KittyDTO(
            id = java.util.UUID.randomUUID().toString(),
            url = java.util.UUID.randomUUID().toString() + ".gif",
            name = java.util.UUID.randomUUID().toString(),
            width = Random.nextInt(100, 200),
            height = Random.nextInt(200, 300)
        )
}