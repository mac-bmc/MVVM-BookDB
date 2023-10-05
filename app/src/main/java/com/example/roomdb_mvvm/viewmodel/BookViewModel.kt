package com.example.roomdb_mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import androidx.lifecycle.viewModelScope
import com.example.roomdb_mvvm.model.BookRepository
import com.example.roomdb_mvvm.model.Book
import com.example.roomdb_mvvm.model.BookDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application): AndroidViewModel(application) {

    private val repository : BookRepository
    val readAllData : LiveData<List<Book>>

    init {
        val bookDao = BookDatabase.getDatabase(application).userDao()
        repository= BookRepository(bookDao)
        readAllData= repository.readAllData
    }

    fun addBook(book: Book){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
    }

    fun updateBook(book: Book)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteBook(book)
        }
    }

    fun deleteAll()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteAll()
        }
    }


}