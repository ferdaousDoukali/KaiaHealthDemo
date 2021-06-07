package com.kaiahealth.demo.presentation.home

import com.kaiahealth.demo.core.model.Exercise

sealed class HomeUIState {
    object Loading : HomeUIState()
    data class Success(val exercises: List<Exercise>) : HomeUIState()
    data class Error(val errorMessage: String) : HomeUIState()
}
