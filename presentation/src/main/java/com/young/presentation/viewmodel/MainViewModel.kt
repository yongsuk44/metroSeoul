package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.usecase.subwayfacilities.remote.RemoteGetSubWayFacilitiesDataUseCase
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.model.UiSubwayFacilities
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber

interface MainViewFunction {
    fun loadSubWayFacilitiesData(key : String)
    suspend fun getCacheAllSubWayFacilitiesData()
    suspend fun getRemoteSubWayFacilitiesData(key : String)
}

class MainViewModel @ViewModelInject constructor(
//    private val getSubWayFacilitiesDataUseCase: RemoteGetSubWayFacilitiesDataUseCase
) : BaseViewModel(), MainViewFunction {

    private val _subWayFacilitiesData = MutableLiveData<List<UiSubwayFacilities>>()
    val subWayFacilitiesData: LiveData<List<UiSubwayFacilities>>
        get() = _subWayFacilitiesData

    override fun loadSubWayFacilitiesData(key: String) {
        viewModelScope.launch(handler) {
//            if (getSubWayTableSizeUseCase.invoke(Unit) == 0) { getRemoteSubWayFacilitiesData(key) }
//            else { getCacheAllSubWayFacilitiesData() }
        }
    }

    val mapper = BaseMapper(DomainSubwayFacilities::class, UiSubwayFacilities::class)

    override suspend fun getCacheAllSubWayFacilitiesData() {
//        getAllDataUseCase(Unit)
//            .map {
//                BaseMapper.setList(mapper).run { this(it) }
//            }
//            .catch { e ->
//                Timber.e(e)
//            }.onCompletion {
//                setLoadingValue(true)
//            }.collect {
//                _subWayFacilitiesData.postValue(it)
//            }
    }

    override suspend fun getRemoteSubWayFacilitiesData(key: String) {
//        getSubWayFacilitiesDataUseCase(key)
//            .map {
//                insertSubWayFacilitiesDataUseCase(it)
//                BaseMapper.setList(mapper).run { this(it) }
//            }.catch { e ->
//                Timber.e(e)
//            }.onCompletion {
//                setLoadingValue(true)
//            }.collect {
//                _subWayFacilitiesData.postValue(it)
//            }
    }
}