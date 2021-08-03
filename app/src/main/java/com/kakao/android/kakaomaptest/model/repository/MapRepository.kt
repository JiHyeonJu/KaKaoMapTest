package com.kakao.android.kakaomaptest.model.repository

import com.kakao.android.kakaomaptest.model.data.CategorySearchData
import com.kakao.android.kakaomaptest.model.retrofit.KakaoApiClient
import retrofit2.Response


class MapRepository {
    //private var placeData: MutableLiveData<ArrayList<Place>> = MutableLiveData()

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


    /*fun searchCategory(category: String, x: String, y: String, radius: Int): MutableLiveData<ArrayList<Place>> {
        KakaoApiClient.apiService.getSearchCategory(
            KakaoApiClient.API_KEY,
            category,
            x,
            y,
            radius,
            "distance"
        ).enqueue(object : Callback<CategorySearchData> {
            override fun onResponse(
                call: Call<CategorySearchData>,
                response: Response<CategorySearchData>
            ) {
                if (response.isSuccessful) {
                    // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                    //Log.d("jjh", "Raw: ${response.raw()}")
                    Log.d("jjh", "Body: ${response.body()}")

                    // .? 이거 확인, null인 경우 값이 뭘로 들어가는지
                    placeData.value = response.body()?.documents
                }
            }

            override fun onFailure(call: Call<CategorySearchData>, t: Throwable) {
                // 통신 실패
                Log.w("jjh", "통신 실패: ${t.message}")
            }
        })

        return placeData
    }*/
}