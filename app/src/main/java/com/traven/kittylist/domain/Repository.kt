package com.traven.kittylist.domain

import com.traven.kittylist.Const
import com.traven.kittylist.model.KittyDTO

class Repository(private val api: Iapi) : Irepo {

    override suspend fun getList(limit: Int, page: Int): List<KittyDTO> =
        api.getImgList(limit, page, Const.API_KEY)



}