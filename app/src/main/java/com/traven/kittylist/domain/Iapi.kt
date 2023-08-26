package com.traven.kittylist.domain

import com.traven.kittylist.model.KittyDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface Iapi {

    @GET("search?")
    suspend fun getImgList(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
        ): List<KittyDTO>

}