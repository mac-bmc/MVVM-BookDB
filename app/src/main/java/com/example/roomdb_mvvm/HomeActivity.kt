package com.example.roomdb_mvvm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.roomdb_mvvm.ui.theme.RoomDBMVVMTheme
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider

import androidx.compose.ui.tooling.preview.PreviewParameter as PreviewParameter

class HomeActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    lateinit var bookViewModel: BookViewModel
    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDBMVVMTheme {
                val lazyListState = rememberLazyListState()
                var isClicked by remember {
                    mutableStateOf(false)
                }
                bookViewModel = ViewModelProvider(
                    this,
                    ViewModelProvider.AndroidViewModelFactory(application)
                ).get(BookViewModel::class.java)
                val bookList:List<Book> by bookViewModel.readAllData.observeAsState(initial = mutableStateListOf())
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = "RooM")},
                            actions = {
                                IconButton(onClick = {
                                    isClicked = true
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_deleteall),
                                        contentDescription = null
                                    )
                                }
                                if (isClicked) {
                                    CustomDialog()
                                }
                            },
                        )
                    },
                    floatingActionButton =
                    {
                        FloatingActionButton(onClick = { intentToAdd() },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add),
                                    contentDescription = null
                                )
                            })
                    },
                    content = { it
                        Column(Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            LazyColumn(
                                state = lazyListState,

                                ) {
                                item {
                                    Box(modifier = Modifier.height(80.dp))
                                }
                                items(bookList) { book ->
                                    Column() {
                                        //Spacer(modifier=Modifier.height(200.dp))
                                        CustomCard(book = book)
                                        Spacer(Modifier.height(20.dp))
                                    }

                                }
                            }
                        }

                    }
                )


            }
        }
    }

    @Composable
    fun CustomCard(book: Book)
    {
        Box(
            modifier = Modifier
                .clickable {
                    Log.d("card", "clicked ${book.bookTitle}")
                    val intent = Intent(this, UpdateToBookActivity::class.java)
                    intent.putExtra("title", book.bookTitle)
                    intent.putExtra("author", book.authorName)
                    intent.putExtra("pgno", book.nofPages)
                    intent.putExtra("id", book.bookId)
                    startActivity(intent)

                }
                .size(300.dp, 150.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(28.dp)),
            contentAlignment = Alignment.Center

        ) {
            Column {
                Text(text = "${book.bookTitle}")
                Text(text = "${book.authorName}")
                Text(text = "${book.nofPages}")
            }
        }
    }


    @Composable
    fun CustomDialog() {
        val openDialog = remember { mutableStateOf(true) }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    ElevatedButton(onClick = {
                        openDialog.value = false
                        deleteAll()
                    }) {
                        Text(text = "Delete")
                    }
                },
                dismissButton = {
                    ElevatedButton(onClick = { openDialog.value = false }) {
                        Text(text = "Cancel")
                    }
                },
                text = {
                    Text(text = "Do you want to clear the database")
                })
        }
    }

    private fun deleteAll() {
        try {
            bookViewModel.deleteAll()
        } catch (e: Exception) {
            Log.d("deletedatabase", "$e")
        }
    }

    private fun intentToAdd() {
        val intent = Intent(this, AddToRoomActivity::class.java)
        startActivity(intent)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomDBMVVMTheme {
    }
}