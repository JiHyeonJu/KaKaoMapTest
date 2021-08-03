package com.kakao.android.kakaomaptest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.android.kakaomaptest.model.data.CategorySearchData
import com.kakao.android.kakaomaptest.model.data.Place
import com.kakao.android.kakaomaptest.model.repository.MapRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class MapViewModel(private val repository: MapRepository) : ViewModel() {

    // retrofit get 요청으로 서버로부터 받아온 장소 데이터
    private val _responseData = MutableLiveData<Response<CategorySearchData>>()
    val liveData: LiveData<Response<CategorySearchData>> get() = _responseData

    // list로부터 선택된 marker item
    private var _markerItemName = MutableLiveData<String>()
    val liveMarkerItem: LiveData<String> get() = _markerItemName

    private lateinit var x : String
    private lateinit var y : String
    private var categoryCode = "#CE7"

    // 각 카테고리별 data list
    private var dataLists = arrayOf(ArrayList<Place>(), ArrayList(), ArrayList(), ArrayList())
    private var page = arrayOf(1, 1, 1, 1)

    fun searchCategory(categoryCode: String) {
        setCategoryCode(categoryCode)
        searchCategory(false)
    }

    fun setCategoryCode(categoryCode: String) {
        this.categoryCode = categoryCode
    }

    fun getCategoryCode() : String {
        return this.categoryCode
    }

    fun setRefresh() {
        for (dataList in dataLists.withIndex()) {
            dataList.value.clear()
            page[dataList.index] = 1
        }
        searchCategory(false)
    }

    fun setNextPage() {
        searchCategory(true)
    }

    fun setNullToLiveData() {
        _responseData.value = null
    }

    fun getDataList(categoryCode: String): ArrayList<Place> {
        return dataLists[convertCategoryToInt(categoryCode)]
    }

    fun setMarkerItem(url: String) {
        _markerItemName.value = url
    }

    fun setXY(x: String, y: String) {
        this.x = x
        this.y = y
    }

    fun getX(): String {
        return x
    }

    fun getY(): String {
        return y
    }

    private fun searchCategory(isRequestMoreInfo: Boolean) {
        viewModelScope.launch {
            var index = convertCategoryToInt(categoryCode)

            var result =
                repository.searchCategory(categoryCode, x, y, 3000, "distance", page[index], 10)

            if (isFirstPage(categoryCode) || isRequestMoreInfo) {
                result.body()?.documents?.let {
                    dataLists[index].addAll(it)
                }
                page[index]++
            }

            _responseData.value = result
        }
    }

    private fun isFirstPage(categoryCode: String): Boolean {
        return page[convertCategoryToInt(categoryCode)] == 1
    }

    private fun convertCategoryToInt(categoryCode: String): Int {
        return when (categoryCode) {
            "CE7" -> 0
            "HP8" -> 1
            "PM9" -> 2
            "OL7" -> 3
            else -> 0
        }
    }
}