package com.paul9834.pruebainterrapidismo.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @GET("apicontrollerpruebas/api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl")
    suspend fun getVersion(): Response<ResponseBody>

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

    @Headers(
        "Usuario: pam.meredy21",
        "Identificacion: 987204545",
        "Accept: text/json",
        "IdUsuario: pam.meredy21",
        "IdCentroServicio: 1295",
        "NombreCentroServicio: PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45",
        "IdAplicativoOrigen: 9"
    )
    @GET("apicontrollerpruebas/api/SincronizadorDatos/ObtenerEsquema/true")
    suspend fun getSchema(): Response<List<SchemaDto>>

    @Headers(
        "Usuario: pam.meredy21",
        "Identificacion: 987204545",
        "Accept: text/json",
        "IdUsuario: pam.meredy21",
        "IdCentroServicio: 1295",
        "NombreCentroServicio: PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45",
        "IdAplicativoOrigen: 9"
    )
    @GET("apicontrollerpruebas/api/ParametrosFramework/ObtenerLocalidadesRecogidas")
    suspend fun getLocations(): Response<List<LocationDto>>
}