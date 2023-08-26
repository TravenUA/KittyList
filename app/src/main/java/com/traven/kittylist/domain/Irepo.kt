package com.traven.kittylist.domain

import com.traven.kittylist.model.KittyDTO

interface Irepo {
    suspend fun getList(limit: Int, page: Int): List<KittyDTO>
}