package com.young.presentation.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.usecase.subwayfacilities.local.GetSizeTableDataUseCase
import com.young.domain.usecase.subwayfacilities.local.InsertSubWayFacilitiesDataUseCase
import com.young.domain.usecase.subwayfacilities.local.LocalGetSubWayFacilitiesDataUseCase
import com.young.domain.usecase.subwayfacilities.remote.RemoteGetSubWayFacilitiesDataUseCase
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.model.UiSubwayFacilities
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.HashMap
import javax.inject.Inject

interface MainViewFunction {
    fun loadSubWayFacilitiesData(key : String)
    suspend fun getLocalAllSubWayFacilitiesData()
    suspend fun getRemoteSubWayFacilitiesData(key : String)
}

class MainViewModel @ViewModelInject constructor(
    private val getSubWayFacilitiesDataUseCase: RemoteGetSubWayFacilitiesDataUseCase,
    private val insertSubWayFacilitiesDataUseCase: InsertSubWayFacilitiesDataUseCase,
    private val getSubWayTableSizeUseCase : GetSizeTableDataUseCase,
    private val getAllDataUseCase: LocalGetSubWayFacilitiesDataUseCase
) : BaseViewModel(), MainViewFunction {

    private val _subWayFacilitiesData = MutableLiveData<List<UiSubwayFacilities>>()
    val subWayFacilitiesData: LiveData<List<UiSubwayFacilities>>
        get() = _subWayFacilitiesData

    override fun loadSubWayFacilitiesData(key: String) {
        viewModelScope.launch(handler) {
            if (getSubWayTableSizeUseCase.invoke(Unit) == 0) { getRemoteSubWayFacilitiesData(key) }
            else { getLocalAllSubWayFacilitiesData() }
        }
    }

    val mapper = BaseMapper(DomainSubwayFacilities::class, UiSubwayFacilities::class)

    override suspend fun getLocalAllSubWayFacilitiesData() {
        getAllDataUseCase(Unit)
            .map {
                BaseMapper.setList(mapper).run { this(it) }
            }
            .catch { e ->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(true)
            }.collect {
                _subWayFacilitiesData.postValue(it)
            }
    }

    override suspend fun getRemoteSubWayFacilitiesData(key: String) {
        getSubWayFacilitiesDataUseCase(key)
            .map {
                insertSubWayFacilitiesDataUseCase(it)
                BaseMapper.setList(mapper).run { this(it) }
            }.catch { e ->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(true)
            }.collect {
                _subWayFacilitiesData.postValue(it)
            }
    }
}