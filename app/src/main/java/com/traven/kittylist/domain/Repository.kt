package com.traven.kittylist.domain

import com.traven.kittylist.model.Const
import com.traven.kittylist.model.IApi
import com.traven.kittylist.model.dto.InDataDTO
import javax.inject.Inject

class Repository(private val api: IApi) {

    suspend fun getList(limit: Int): List<InDataDTO> = api.getImgList(limit, Const.API_KEY)


}