package com.paul9834.pruebainterrapidismo.domain.model

data class User(
    val usuario: String,
    val identificacion: String,
    val nombre: String
)

data class Location(
    val abreviacionCiudad: String,
    val nombreCompleto: String
)

data class TableSchema(
    val nombreTabla: String
)