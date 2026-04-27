package com.paul9834.pruebainterrapidismo.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @GET("apicontrollerpruebas/api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl")
    suspend fun getVersion(): Response<VersionDto>

    @Headers(
        "Accept: text/json",
        "Content-Type: application/json"
    )
    @POST("FtEntregaElectronica/MultiCanales/ApiSeguridadPruebas/api/Seguridad/AuthenticaUsuarioApp")
    suspend fun login(
        @Header("Usuario") usernameHeader: String = "pam.meredy21",
        @Header("Identificacion") identification: String = "987204545",
        @Header("IdUsuario") userId: String = "pam.meredy21",
        @Header("IdCentroServicio") serviceCentreId: String = "1295",
        @Header("NombreCentroServicio") centreName: String = "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45",
        @Header("IdAplicativoOrigen") originAppId: String = "9",
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("apicontrollerpruebas/api/SincronizadorDatos/ObtenerEsquema/true")
    suspend fun getSchema(): Response<List<SchemaDto>>

    @GET("apicontrollerpruebas/api/ParametrosFramework/ObtenerLocalidadesRecogidas")
    suspend fun getLocations(): Response<List<LocationDto>>
}