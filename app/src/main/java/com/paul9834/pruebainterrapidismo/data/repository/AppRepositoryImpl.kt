package com.paul9834.pruebainterrapidismo.data.repository

import com.paul9834.pruebainterrapidismo.data.local.*
import com.paul9834.pruebainterrapidismo.data.remote.*
import com.paul9834.pruebainterrapidismo.domain.model.*
import com.paul9834.pruebainterrapidismo.domain.repository.AppRepository

class AppRepositoryImpl(
    private val api: ApiService,
    private val userDao: UserDao,
    private val schemaDao: SchemaDao
) : AppRepository {

    override suspend fun checkVersion(localVersion: String): Result<String> {
        return try {
            val response = api.getVersion()
            if (response.isSuccessful) {
                Result.success(response.body()?.valor ?: "")
            } else {
                Result.failure(Exception("Error HTTP: ${response.code()}"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun login(): Result<User> {
        return try {
            val response = api.login(request = LoginRequest())
            val body = response.body()
            if (response.isSuccessful && response.code() == 200 && body != null) {
                userDao.insertUser(UserEntity(body.usuario, body.identificacion, body.nombre))
                Result.success(User(body.usuario, body.identificacion, body.nombre))
            } else {
                Result.failure(Exception("Alerta: Código ${response.code()}"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun syncSchemas(): Result<List<TableSchema>> {
        return try {
            val response = api.getSchema()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val entities = body.map { SchemaEntity(it.nombreTabla) }
                schemaDao.insertSchemas(entities)
                Result.success(entities.map { TableSchema(it.nombreTabla) })
            } else {
                Result.failure(Exception("Error esquemas: ${response.code()}"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun getLocations(): Result<List<Location>> {
        return try {
            val response = api.getLocations()
            if (response.isSuccessful) {
                val locations = response.body()?.map {
                    Location(it.abreviacionCiudad, it.nombreCompleto)
                } ?: emptyList()
                Result.success(locations)
            } else {
                Result.failure(Exception("Error localidades: ${response.code()}"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun getLocalUser(): User? {
        return userDao.getUser()?.let { User(it.usuario, it.identificacion, it.nombre) }
    }

    override suspend fun getLocalSchemas(): List<TableSchema> {
        return schemaDao.getAllSchemas().map { TableSchema(it.nombreTabla) }
    }
}