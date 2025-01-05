package com.example.bin_test.feature.mainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    onNavigateToListScreen: () -> Unit
) {
    val text by viewModel.textValue.collectAsState()
    val cardInfo by viewModel.cardInfo.collectAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Поле ввода BIN
            OutlinedTextField(
                value = text,
                onValueChange = { viewModel.updateText(it) },
                label = { Text("Enter BIN") },
                singleLine = true,
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = { viewModel.getInfo() }) {
                Icon(imageVector = Icons.Default.Search, contentDescription =null )
            }
        }
        // Отображение информации о карте
        cardInfo?.let { info ->
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Scheme: ${info.scheme}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Type: ${info.type}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Country: ${info.country.name}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Location: ${info.country.latitude}, ${info.country.longitude}",
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        viewModel.openMaps(context, info.country.latitude, info.country.longitude)
                    }
                )
                Text(
                    text = "Bank: ${info.bank.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Website: ${info.bank.url}",
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        viewModel.openBrowser(context, info.bank.url)
                    }
                )
                Text(
                    text = "Phone: ${info.bank.phone}",
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        viewModel.openDialer(context, info.bank.phone)
                    }
                )
            }
        }

        // Кнопка перехода на экран истории
        Button(
            onClick = onNavigateToListScreen,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View BIN History")
        }
    }
}

