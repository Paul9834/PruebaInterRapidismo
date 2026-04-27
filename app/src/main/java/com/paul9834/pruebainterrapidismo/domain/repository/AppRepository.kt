package com.paul9834.pruebainterrapidismo.domain.repository

import com.paul9834.pruebainterrapidismo.domain.model.*

interface AppRepository {
    suspend fun checkVersion(localVersion: String): Result<String>
    suspend fun login(): Result<User>
    suspend fun syncSchemas(): Result<List<TableSchema>>
    suspend fun getLocalUser(): User?
    suspend fun getLocations(): Result<List<Location>>
    suspend fun getLocalSchemas(): List<TableSchema>
}