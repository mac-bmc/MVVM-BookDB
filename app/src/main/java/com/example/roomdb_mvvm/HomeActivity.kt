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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp

import androidx.compose.ui.tooling.preview.PreviewParameter as PreviewParameter

class HomeActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    lateinit var bookViewModel: BookViewModel
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDBMVVMTheme {
                var isClicked by remember {
                    mutableStateOf(false)
                }
                var bookList = listOf<Book>()
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = "RooM") },
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
                            })
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
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            LazyColumn {
                                items(bookList) {
                                }
                            }
                        }
                    }
                )


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
        val intent = Intent(this, AddBookActivity::class.java)
        startActivity(intent)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoomDBMVVMTheme {
    }
}