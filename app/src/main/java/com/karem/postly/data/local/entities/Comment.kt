package com.karem.postly.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey()
    val id: Int = 0,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)