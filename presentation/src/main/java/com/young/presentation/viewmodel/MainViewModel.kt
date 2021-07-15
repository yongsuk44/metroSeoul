package com.young.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.usecase.subwayfacilities.GetSubWayFacilitiesDataBaseUseCase
import com.young.domain.usecase.subwayfacilities.GetSubWayFacilitiesDataUseCase
import com.young.presentation.model.UiSubwayFacilities
import com.young.presentation.viewfunction.MainViewFunction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSubWayFacilitiesDataUseCase: GetSubWayFacilitiesDataUseCase
) : ViewModel(), MainViewFunction {

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("test" , exception.message.toString())
        _loading.value = false
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean>
        get() = _loading

    private val _subWayFacilitiesData = MutableLiveData<List<UiSubwayFacilities>>()
    val subWayFacilitiesData: LiveData<List<UiSubwayFacilities>>
        get() = _subWayFacilitiesData

    fun get(key : String) {
        viewModelScope.launch(handler) {
            loadSubWayFacilitiesData(key)
        }
    }

    override fun loadSubWayFacilitiesData(key: String) {
        viewModelScope.launch(handler) {
            val mapper = BaseMapper(DomainSubwayFacilities::class, UiSubwayFacilities::class)

            getSubWayFacilitiesDataUseCase(key)
                .map {
                    BaseMapper.setList(mapper).run {
                        this(it)
                    }
                }.catch {
                    _loading.value = false
                }.collect {
                    _subWayFacilitiesData.postValue(it)
                    _loading.value = true
                }
        }
    }

}