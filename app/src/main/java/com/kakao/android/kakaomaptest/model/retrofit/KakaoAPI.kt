package com.kakao.android.kakaomaptest.model.retrofit

import com.kakao.android.kakaomaptest.model.data.CategorySearchData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoAPI {
    /*@GET("v2/local/search/keyword.json")
    fun getSearchKeyword(
            @Header("Authorization") key: String,
            @Query("query") query: String
            @Query("category_group_code") category: String

    ): Call<ResultSearchKeyword>*/

    @GET("v2/local/search/category.json")
    suspend fun getSearchCategory(
        @Header("Authorization") token: String?,
        @Query("category_group_code") category_group_code: String?,
        @Query("x") x: String?,
        @Query("y") y: String?,
        @Query("radius") radius: Int,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<CategorySearchData>
}