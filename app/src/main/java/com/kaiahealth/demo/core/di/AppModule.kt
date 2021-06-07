package com.kaiahealth.demo.core.di

import com.kaiahealth.demo.BuildConfig
import com.kaiahealth.demo.core.api.ExerciseDataService
import com.kaiahealth.demo.core.local_storage.LocalStorage
import com.kaiahealth.demo.data.ExerciseRepository
import com.kaiahealth.demo.presentation.MainViewModel
import com.kaiahealth.demo.utils.RestClientFactory
import com.kaiahealth.demo.utils.RetrofitClientFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<RestClientFactory> {
        RetrofitClientFactory(
            baseUrl = BuildConfig.BASE_URL
        )
    }

    single {
        LocalStorage(context = androidApplication())
    }

    single {
        ExerciseRepository(
            exerciseDataService = get<RestClientFactory>().makeApiService(
                ExerciseDataService::class.java
            ),
            localStorage = get()
        )
    }

    viewModel {
        MainViewModel(
            exerciseRepository = get()
        )
    }
}