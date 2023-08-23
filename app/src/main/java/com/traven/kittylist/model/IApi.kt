package com.traven.kittylist.model

import com.traven.kittylist.model.dto.KittyDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface IApi {

    @GET("search?")
    suspend fun getImgList(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
        ): List<KittyDTO>

}