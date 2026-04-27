package com.paul9834.pruebainterrapidismo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val usuario: String,
    val identificacion: String,
    val nombre: String
)
@Entity(tableName = "schema_table")
data class SchemaEntity(
    @PrimaryKey val nombreTabla: String
)