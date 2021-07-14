package com.young.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.usecase.subwayfacilities.GetSubWayFacilitiesDataBaseUseCase
import com.young.domain.usecase.subwayfacilities.GetSubWayFacilitiesDataUseCase
import com.young.presentation.model.UiSubwayFacilities
import com.young.presentation.viewfunction.MainViewFunction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSubWayFacilitiesDataUseCase : GetSubWayFacilitiesDataUseCase
) : ViewModel() , MainViewFunction {

    private val subWayFacilitiesData = MutableLiveData<UiSubwayFacilities>()
    val _subWayFacilitiesData : LiveData<UiSubwayFacilities>
        get() = subWayFacilitiesData

    override suspend fun loadSubWayFacilitiesData(key : String) {
        viewModelScope.launch {
            getSubWayFacilitiesDataUseCase(key).collect {
                it.map {
                    BaseMapper(DomainSubwayFacilities::class , UiSubwayFacilities::class).run {
                        this(it)
                    }
                }
            }
        }
    }

}