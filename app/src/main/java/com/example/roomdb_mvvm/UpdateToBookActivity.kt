@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.roomdb_mvvm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.roomdb_mvvm.ui.theme.RoomDBMVVMTheme

class UpdateToBookActivity : ComponentActivity() {
    lateinit var bookViewModel : BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDBMVVMTheme {
                var bName by remember { mutableStateOf("") }
                var bAuthor by remember { mutableStateOf("") }
                var bPgNo by remember { mutableStateOf("") }
                bName=intent.getStringExtra("title").toString()
                bAuthor=intent.getStringExtra("author").toString()
                bPgNo=intent.getStringExtra("pgno").toString()
                val id = intent.getIntExtra("id",0)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = bName,
                            onValueChange =
                            {
                                bName = it
                            },
                            label = {
                                Text(text = "Enter book name")
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = bAuthor,
                            onValueChange =
                            {
                                bAuthor = it
                            },
                            label = {
                                Text(text = "Enter book author")
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = bPgNo,
                            onValueChange =
                            {
                                bPgNo = it
                            },
                            label = {
                                Text(text = "Enter book pg no")
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row {
                            ElevatedButton(
                                onClick = { updateToDb(bName, bAuthor, bPgNo,id) },
                            ) {
                                Text(text = "Update to Db")

                            }
                            ElevatedButton(
                                onClick = { deleteFromDb(bName, bAuthor, bPgNo,id) },
                            ) {
                                Text(text = "Delete from Db")

                            }
                        }


                    }

                }
            }
        }
    }

    private fun deleteFromDb(title: String, author: String, pgno: String, id: Int) {
        val book = Book(id,title,author,pgno)
        try {
            bookViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            ).get(BookViewModel::class.java)
            bookViewModel.deleteBook(book)
            startActivity(Intent(this,HomeActivity::class.java))
        }
        catch (e:Exception)
        {
            Log.d("deletefromdatabase","$e")

        }
    }
    private fun updateToDb(title: String, author: String, pgno: String, id: Int) {
        Log.d("title","$title")
        val book = Book(id,title,author,pgno)
        try {
            bookViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            ).get(BookViewModel::class.java)
            bookViewModel.updateBook(book)
            startActivity(Intent(this,HomeActivity::class.java))
        }
        catch (e:Exception)
        {
            Log.d("updatetodatabase","$e")
        }
    }
}

