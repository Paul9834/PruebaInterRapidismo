package com.paul9834.pruebainterrapidismo.data.remote

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("Mac") val mac: String = "",
    @SerializedName("NomAplicacion") val nomAplicacion: String = "Controller APP",
    @SerializedName("Password") val password: String = "SW50ZXIyMDIx\n",
    @SerializedName("Path") val path: String = "",
    @SerializedName("Usuario") val usuario: String = "cGFtLm1lcmVkeTIx\n"
)

data class LoginResponse(
    @SerializedName("Usuario") val usuario: String? = null,

    @SerializedName("Identificacion") val identificacion: String? = null,
    @SerializedName("IdUsuario") val idUsuario: String? = null,
    @SerializedName("Documento") val documento: String? = null,

    @SerializedName("Nombre") val nombre: String? = null,
    @SerializedName("NombreCompleto") val nombreCompleto: String? = null,
    @SerializedName("NombreUsuario") val nombreUsuario: String? = null
)
data class LocationDto(
    @SerializedName("AbreviacionCiudad") val abreviacionCiudad: String,
    @SerializedName("NombreCompleto") val nombreCompleto: String
)

data class VersionDto(
    @SerializedName("Valor") val valor: String
)

data class SchemaDto(
    @SerializedName("NombreTabla") val nombreTabla: String
)