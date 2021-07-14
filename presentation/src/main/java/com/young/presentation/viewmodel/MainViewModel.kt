package com.young.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.young.domain.usecase.subwayfacilities.GetSubWayFacilitiesDataBaseUseCase
import com.young.domain.usecase.subwayfacilities.GetSubWayFacilitiesDataUseCase
import com.young.presentation.viewfunction.MainViewFunction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSubWayFacilitiesDataUseCase : GetSubWayFacilitiesDataUseCase
) : ViewModel() , MainViewFunction {

    override suspend fun loadSubWayFacilitiesData(key : String) {
        getSubWayFacilitiesDataUseCase(key)
    }

}