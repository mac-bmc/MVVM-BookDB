package com.example.roomdb_mvvm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BookDetails")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int,
    val bookTitle: String,
    val authorName: String,
    val nofPages: String
)