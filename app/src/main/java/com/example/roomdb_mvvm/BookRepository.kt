package com.example.roomdb_mvvm

import android.util.Log
import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    val readAllData: LiveData<List<Book>> = bookDao.readAllData()

    suspend fun addBook(book: Book)
    {
        bookDao.insertBook(book)
    }
    suspend fun updateBook(book: Book)
    {
        val title = book.bookTitle
        val authorName = book.authorName
        val pageNo=book.nofPages
        val id = book.bookId
        Log.d("updated id","$id")
        Log.d("updated title","$title")
        Log.d("updated pageno","$pageNo")
        Log.d("updated author","$authorName")
        bookDao.updatebyId(title, authorName,pageNo,id)
    }

    suspend fun deleteBook(book: Book)
    {
        val id = book.bookId
        bookDao.deleteById(id)
    }

    suspend fun deleteAll()
    {
        bookDao.deleteAll()
    }


}