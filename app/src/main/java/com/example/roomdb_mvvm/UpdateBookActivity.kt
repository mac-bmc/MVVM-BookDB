package com.example.roomdb_mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider

class UpdateBookActivity : AppCompatActivity(R.layout.activity_update_book) {
    private lateinit var bookViewModel: BookViewModel
    lateinit var editTitleText : EditText
    lateinit var editAuthorText : EditText
    lateinit var editPgNo : EditText
    lateinit var updateButton : Button
    lateinit var deleteButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(BookViewModel::class.java)
         editTitleText = findViewById(R.id.editBookTitle)
         editAuthorText = findViewById(R.id.editBookAuthor)
         editPgNo = findViewById(R.id.editPageNo)
         updateButton = findViewById(R.id.updateButton)
         deleteButton = findViewById(R.id.deleteButton)
        editTitleText.setText(intent.getStringExtra("title").toString())
        editAuthorText.setText(intent.getStringExtra("author").toString())
        editPgNo.setText(intent.getStringExtra("pgno").toString())
        updateButton.setOnClickListener()
        {
            val title = editTitleText.text.toString()
            val author = editAuthorText.text.toString()
            val pgno = editPgNo.text.toString()
            val id=intent.getIntExtra("id",0)
            UpdateToDatabase(title,author,pgno,id)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }

        deleteButton.setOnClickListener()
        {
            val title=intent.getStringExtra("title").toString()
            val author=intent.getStringExtra("author").toString()
            val pgno=intent.getStringExtra("pgno").toString()
            val id=intent.getIntExtra("id",0)
            DeleteFromDatabase(title,author,pgno,id)
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }




    }

    private fun DeleteFromDatabase(title: String, author: String, pgno: String, id: Int) {
        Log.d("title","$title")
        val book = Book(id,title,author,pgno)
        try {
            bookViewModel.deleteBook(book)
        }
        catch (e:Exception)
        {
            Log.d("deletefromdatabase","$e")
        }
    }
    private fun UpdateToDatabase(title: String, author: String, pgno: String, id: Int) {
        Log.d("title","$title")
        val book = Book(id,title,author,pgno)
        try {
            bookViewModel.updateBook(book)
        }
        catch (e:Exception)
        {
            Log.d("updatetodatabase","$e")
        }
    }

    }


