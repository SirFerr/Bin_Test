package com.example.bin_test.feature.listScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bin_test.feature.listScreen.ListScreenViewModel

@Composable
fun ListScreen(viewModel: ListScreenViewModel = hiltViewModel()) {
    val text by viewModel.textValue.collectAsState()
    val cardInfo by viewModel.cardInfo.collectAsState()


    Column {
        TextField(value = text, onValueChange = { viewModel.updateText(it) })
        Button(onClick = { viewModel.getInfo() }) {
            Text(text = "Search")
        }

        
    }
}