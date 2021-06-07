package com.kaiahealth.demo.core.api

import com.kaiahealth.demo.core.model.Exercise
import retrofit2.http.GET

interface ExerciseDataService {
    @GET("/api/jsonBlob/027787de-c76e-11eb-ae0a-39a1b8479ec2")
    suspend fun getExercises(): List<Exercise>
}