package com.kakao.android.kakaomaptest.model.repository

import com.kakao.android.kakaomaptest.model.data.CategorySearchData
import com.kakao.android.kakaomaptest.model.retrofit.KakaoApiClient
import retrofit2.Response

class MapRepository {
    suspend fun searchCategory(
        category: String,
        x: String,
        y: String,
        radius: Int,
        sort: String,
        page: Int,
        size: Int
    ): Response<CategorySearchData> {
        return KakaoApiClient.apiService.getSearchCategory(
            KakaoApiClient.API_KEY,
            category,
            x,
            y,
            radius,
            sort,
            page,
            size
        )
    }
}