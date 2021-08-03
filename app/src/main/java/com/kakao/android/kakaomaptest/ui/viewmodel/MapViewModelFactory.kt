package com.kakao.android.kakaomaptest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kakao.android.kakaomaptest.model.repository.MapRepository

class MapViewModelFactory(private val repository : MapRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MapRepository::class.java).newInstance(repository)
    }
}