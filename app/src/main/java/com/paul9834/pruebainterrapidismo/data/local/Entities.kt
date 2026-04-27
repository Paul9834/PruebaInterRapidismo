package com.paul9834.pruebainterrapidismo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: Int = 1, // O el ID que uses
    val usuario: String? = null,
    val identificacion: String? = null, // <- Aquí estaba el problema
    val nombre: String? = null
)
@Entity(tableName = "schema_table")
data class SchemaEntity(
    @PrimaryKey val nombreTabla: String
)