package com.traven.kittylist.domain

import com.traven.kittylist.Const
import com.traven.kittylist.model.IApi
import com.traven.kittylist.model.dto.KittyDTO

class Repository(private val api: IApi) {

    suspend fun getList(limit: Int, page: Int): List<KittyDTO> =
        api.getImgList(limit, page, Const.API_KEY)
            .filter { !it.url.endsWith(".gif") }
            .filter { it.width > 200 }


}