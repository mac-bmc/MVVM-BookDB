@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.roomdb_mvvm.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.roomdb_mvvm.viewmodel.BookViewModel
import com.example.roomdb_mvvm.model.Book
import com.example.roomdb_mvvm.ui.theme.RoomDBMVVMTheme

class AddToRoomActivity : ComponentActivity() {
    private lateinit var bookViewModel: BookViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDBMVVMTheme {
                var bName by remember { mutableStateOf("") }
                var bAuthor by remember { mutableStateOf("") }
                var bPgNo by remember { mutableStateOf("") }
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

                        ElevatedButton(
                            onClick = { addToDb(bName, bAuthor, bPgNo) },
                        ) {
                            Text(text = "Add to Db")

                        }


                    }

                }
            }
        }
    }

    private fun addToDb(title: String, author: String, pgNo: String) {
        this.bookViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(BookViewModel::class.java)
        val book = Book(0, title, author, pgNo)
        try {
            bookViewModel.addBook(book)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Log.d("databased", "$e")
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    RoomDBMVVMTheme {

    }
}