package com.kaiahealth.demo.utils

interface RestClientFactory {
    fun <T> makeApiService(apiService: Class<T>): T
}