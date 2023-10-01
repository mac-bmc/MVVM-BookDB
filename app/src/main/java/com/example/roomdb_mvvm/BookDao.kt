package com.example.roomdb_mvvm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {



    @Insert()
    suspend fun insertBook(book: Book)

    @Query("UPDATE BookDetails SET bookTitle = :title, authorName = :authorName, nofPages = :pageNo WHERE bookId =:id")
    suspend fun updatebyId(title: String, authorName : String, pageNo :String, id:Int)

    @Query("DELETE FROM BookDetails WHERE bookId = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM BookDetails")
    suspend fun deleteAll()

    @Query("SELECT * FROM BookDetails ORDER BY bookId ASC ")
    fun readAllData() : LiveData<List<Book>>



}