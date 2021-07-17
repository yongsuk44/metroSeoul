package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubWayTel
import com.young.domain.usecase.subwayfacilities.remote.RemoteGetSubWayTelUseCase
import com.young.presentation.model.UiSubWayTel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

interface SubWayTelCase {
    fun loadSubWayTelData(key : String)
    suspend fun getSubWayTelData(key : String)
}

class SubWayTelViewModel @ViewModelInject constructor(
    private val telUseCase: RemoteGetSubWayTelUseCase
) : BaseViewModel() , SubWayTelCase {

    private val _subWayTelData = MutableLiveData<List<UiSubWayTel>>()
    val subWayTelData : LiveData<List<UiSubWayTel>>
        get() = _subWayTelData

    override fun loadSubWayTelData(key: String) {
        viewModelScope.launch(handler) {
            getSubWayTelData(key)
        }
    }

    override suspend fun getSubWayTelData(key: String) {
        val mapper = BaseMapper(DomainSubWayTel::class , UiSubWayTel::class)
        telUseCase(key)
            .map {
                BaseMapper.setList(mapper).run {
                    this(it)
                }
            }.catch {
                setLoadingValue(false)
            }.onCompletion {
                setLoadingValue(true)
            }.collect {
                _subWayTelData.postValue(it)
            }
    }
}