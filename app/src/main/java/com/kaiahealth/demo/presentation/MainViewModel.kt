package com.kaiahealth.demo.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaiahealth.demo.core.model.Exercise
import com.kaiahealth.demo.data.ExerciseRepository
import com.kaiahealth.demo.presentation.home.HomeUIState
import com.kaiahealth.demo.utils.SuspendCallResult
import com.kaiahealth.demo.utils.safeSuspendCall
import kotlinx.coroutines.launch

class MainViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    private val mutableHomeExercisesLiveData = MutableLiveData<HomeUIState>(HomeUIState.Loading)
    val homeExercisesLiveData: LiveData<HomeUIState> = mutableHomeExercisesLiveData

    val favoriteExercises = mutableListOf<Exercise>()
    val doneExercises = mutableListOf<Exercise>()

    private var currentExerciseIndex = 0

    init {
        loadFavoritesExercises()
        loadAllExercises()
    }

    fun loadAllExercises() {
        viewModelScope.launch {
            when (val response = safeSuspendCall { exerciseRepository.getAllExercises() }) {
                is SuspendCallResult.Success -> {
                    Log.d(TAG, "Successfully load exercises")
                    mutableHomeExercisesLiveData.postValue(HomeUIState.Success(response.value))
                }
                is SuspendCallResult.Error -> {
                    Log.e(
                        TAG,
                        "Unable to load exercises : ${response.throwable}"
                    )
                    val errorMessage = response.throwable.message ?: "Unknown Error"
                    mutableHomeExercisesLiveData.postValue(HomeUIState.Error(errorMessage))
                }
            }
        }
    }

    fun onItemSelected(exercise: Exercise) {
        if (favoriteExercises.contains(exercise)) {
            favoriteExercises.remove(exercise)
        } else {
            favoriteExercises.add(exercise)
        }
    }

    fun getCurrentExercise(): Exercise? {
        val status = mutableHomeExercisesLiveData.value as HomeUIState.Success
        val exercises = status.exercises
        if (currentExerciseIndex in exercises.indices) {
            return exercises[currentExerciseIndex]
        }
        return null
    }

    private fun loadFavoritesExercises() {
        val storedItems = exerciseRepository.getFavoriteExercises()
        storedItems?.let {
            favoriteExercises.addAll(storedItems)
        }
    }

    fun saveFavoriteExercises() {
        exerciseRepository.saveFavoriteExercises(exerciseList = favoriteExercises)
    }

    fun onExerciseCompleted(){
        val currentExercise = getCurrentExercise()
        currentExercise?.let {
            doneExercises.add(it)
        }
    }

    fun moveToNextExercise(){
        currentExerciseIndex++
    }

    fun skipExercise(){
        currentExerciseIndex += 2
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}