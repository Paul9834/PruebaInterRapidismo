package com.paul9834.pruebainterrapidismo.di

import android.content.Context
import com.paul9834.pruebainterrapidismo.data.local.AppDatabase
import com.paul9834.pruebainterrapidismo.data.remote.ApiService
import com.paul9834.pruebainterrapidismo.data.repository.AppRepositoryImpl
import com.paul9834.pruebainterrapidismo.domain.repository.AppRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val appRepository: AppRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://apitesting.interrapidisimo.co/"

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(context)
    }

    override val appRepository: AppRepository by lazy {
        AppRepositoryImpl(apiService, database.userDao(), database.schemaDao())
    }
}