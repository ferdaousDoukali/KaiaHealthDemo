package com.kaiahealth.demo.data

import com.kaiahealth.demo.core.api.ExerciseDataService
import com.kaiahealth.demo.core.local_storage.LocalStorage
import com.kaiahealth.demo.core.model.Exercise

class ExerciseRepository(
    private val exerciseDataService: ExerciseDataService,
    private val localStorage: LocalStorage
) {

    suspend fun getAllExercises(): List<Exercise> {
        return exerciseDataService.getExercises()
    }

    fun getFavoriteExercises(): List<Exercise>? {
        return localStorage.loadFavoriteExercises()
    }

    fun saveFavoriteExercises(exerciseList : List<Exercise>){
        return localStorage.saveFavoriteExercises(exerciseList)
    }
}