package com.example.bin_test.data.remote

import com.example.bin_test.constants.BASE_URL
import com.example.bin_test.data.model.BinResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BinListApi {


    //Получение информации по BIN
    @GET("$BASE_URL/{bin}")
    suspend fun getInfo(
        @Path("bin") bin: String
    ): Response<BinResponse>


}