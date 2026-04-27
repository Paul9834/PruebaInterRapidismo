package com.paul9834.pruebainterrapidismo.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.paul9834.pruebainterrapidismo.PruebaApp
import com.paul9834.pruebainterrapidismo.domain.model.*
import com.paul9834.pruebainterrapidismo.domain.repository.AppRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    var user by mutableStateOf<User?>(null)
    var tables by mutableStateOf<List<TableSchema>>(emptyList())
    var locations by mutableStateOf<List<Location>>(emptyList())
    var statusMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)


    fun initAppFlow(localVersion: String) {
        viewModelScope.launch {
            isLoading = true
            statusMessage = "Validando versión..."

            // 1. Control de Versiones
            repository.checkVersion(localVersion).onSuccess { apiVersion ->
                statusMessage = when {
                    localVersion < apiVersion -> "Versión local desactualizada (API: $apiVersion)"
                    localVersion > apiVersion -> "Versión local es superior a la del API"
                    else -> "Aplicativo actualizado"
                }
            }.onFailure {
                statusMessage = "Error Versión: ${it.message}"
            }

            // 2. Login y Sincronización
            repository.login().onSuccess {
                // En lugar de confiar solo en el objeto 'it', traemos lo que quedó en DB
                getLocalData()
                statusMessage += "\nLogin exitoso."

                repository.syncSchemas().onSuccess {
                    tables = it
                    statusMessage += "\nTablas sincronizadas."
                }.onFailure {
                    statusMessage += "\nError Tablas: ${it.message}"
                }
            }.onFailure {
                statusMessage += "\nError de Autenticación: ${it.message}"
            }
            isLoading = false
        }
    }


    fun getLocalData() {
        viewModelScope.launch {
            user = repository.getLocalUser()
            tables = repository.getLocalSchemas()
        }
    }

    fun fetchLocations() {
        viewModelScope.launch {
            isLoading = true
            statusMessage = "Cargando localidades..."
            repository.getLocations().onSuccess {
                locations = it
                statusMessage = "Localidades listas."
            }.onFailure {
                statusMessage = "Error localidades: ${it.message}"
            }
            isLoading = false
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PruebaApp)
                MainViewModel(app.container.appRepository)
            }
        }
    }
}