package com.zaidxme.zesho.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authors: String, // Stored as comma-separated string
    val thumbnail: String,
    val downloadUrl: String,
    val category: String,
    val progress: Int = 0, // 0 to 100
    val lastAccessed: Long = System.currentTimeMillis()
)
