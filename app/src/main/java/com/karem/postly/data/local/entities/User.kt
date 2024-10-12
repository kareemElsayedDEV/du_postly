package com.karem.postly.data.local.entities;

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int = 0,
    val name: String,
)
