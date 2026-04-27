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
                val versionStr = response.body()?.string()?.replace("\"", "") ?: ""
                Result.success(versionStr)
            } else {
                Result.failure(Exception("Error HTTP: ${response.code()}"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun login(): Result<User> {
        return try {
            val response = api.login(request = LoginRequest())
            val body = response.body()
            if (response.isSuccessful && body != null) {

                val usuario = body.usuario ?: "pam.meredy21"

                val identificacion = if (body.identificacion.isNullOrEmpty() && body.idUsuario.isNullOrEmpty()) {
                    "987204545"
                } else {
                    body.identificacion ?: body.idUsuario ?: ""
                }

                val nombre = if (body.nombre.isNullOrEmpty() && body.nombreCompleto.isNullOrEmpty()) {
                    "PAM MEREDY PRUEBAS"
                } else {
                    body.nombre ?: body.nombreCompleto ?: ""
                }

                android.util.Log.d("DEBUG_INTER", "GUARDANDO -> ID: $identificacion, Nombre: $nombre")

                userDao.insertUser(UserEntity(
                    id = 1,
                    usuario = usuario,
                    identificacion = identificacion,
                    nombre = nombre
                ))

                Result.success(User(usuario, identificacion, nombre))
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
                val entities = body.map { SchemaEntity(nombreTabla = it.nombreTabla ?: "") }
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
                    Location(it.abreviacionCiudad ?: "", it.nombreCompleto ?: "")
                } ?: emptyList()
                Result.success(locations)
            } else {
                Result.failure(Exception("Error localidades: ${response.code()}"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun getLocalUser(): User? {
        return userDao.getUser()?.let {
            User(
                usuario = it.usuario ?: "",
                identificacion = it.identificacion ?: "",
                nombre = it.nombre ?: ""
            )
        }
    }

    override suspend fun getLocalSchemas(): List<TableSchema> {
        return schemaDao.getAllSchemas().map { TableSchema(it.nombreTabla) }
    }
}