package com.example.roomdb_mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider

class AddBookActivity : AppCompatActivity(R.layout.activity_add_book) {
    private lateinit var bookViewModel: BookViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(BookViewModel::class.java)
        val editTitleText = findViewById<EditText>(R.id.editBookTitle)
        val editAuthorText = findViewById<EditText>(R.id.editBookAuthor)
        val editPgNo = findViewById<EditText>(R.id.editPageNo)
        val addButton = findViewById<Button>(R.id.addButton)

       
        addButton.setOnClickListener{
            val title=editTitleText.text.toString()
            val author=editAuthorText.text.toString()
            val pgno=editPgNo.text.toString()
            Log.d("title","$title")
            addToDatabase(title,author,pgno)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addToDatabase(title: String, author : String, pgno : String) {
        Log.d("title","$title")
        val book = Book(0,title,author,pgno)
        try {
            bookViewModel.addBook(book)
        }
        catch (e:Exception)
        {
            Log.d("addtodatabase","$e")
        }
    }
}