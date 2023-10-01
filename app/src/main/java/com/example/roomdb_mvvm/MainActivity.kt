package com.example.roomdb_mvvm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var bookViewModel: BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = BookAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bookViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(BookViewModel::class.java)
        val addButton = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        val deleteAllButton = findViewById<ImageButton>(R.id.deleteAllButton)
        bookViewModel.readAllData.observe(this, Observer { book ->
            adapter.setData(book)
        })


        addButton.setOnClickListener()
        {
            val intent = Intent(this, AddBookActivity::class.java)
            startActivity(intent)
        }

        deleteAllButton.setOnClickListener() {
            val builder= AlertDialog.Builder(this)
            builder.setTitle("Deletion Alert")
            builder.setMessage("Are you sure to delete the data")
            builder.setPositiveButton(R.string.yes){ dialog, which ->
                deleteAll()
            }
            builder.setNegativeButton(R.string.no){ dialog, which ->
                Toast.makeText(this,"Delete Action Suspended",Toast.LENGTH_SHORT).show()

            }
            builder.show()


        }
    }

    private fun deleteAll() {
        try {
            bookViewModel.deleteAll()
        }
        catch (e:Exception)
        {
            Log.d("deletedatabase","$e")
        }
    }
}