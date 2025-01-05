package com.example.bin_test.feature.mainScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bin_test.feature.mainScreen.MainScreenViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    val text by viewModel.textValue.collectAsState()
    val cardInfo by viewModel.cardInfo.collectAsState()

    val context = LocalContext.current

    Column {
        // Поле ввода BIN
        TextField(value = text, onValueChange = { viewModel.updateText(it) })
        Button(onClick = { viewModel.getInfo() }) {
            Text(text = "Search")
        }

        // Отображение информации о карте
        if (cardInfo != null) {
            Text(text = "Scheme: ${cardInfo!!.scheme}")
            Text(text = "Type: ${cardInfo!!.type}")

            Text(text = "Country: ${cardInfo!!.country.name}")
            Text(
                text = "Latitude: ${cardInfo!!.country.latitude}",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    viewModel.openMaps(context, cardInfo!!.country.latitude, cardInfo!!.country.longitude)
                }
            )
            Text(
                text = "Longitude: ${cardInfo!!.country.longitude}",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    viewModel.openMaps(context, cardInfo!!.country.latitude, cardInfo!!.country.longitude)
                }
            )

            Text(text = "Bank Name: ${cardInfo!!.bank.name}")
            Text(
                text = "Bank URL: ${cardInfo!!.bank.url}",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    viewModel.openBrowser(context, cardInfo!!.bank.url)
                }
            )
            Text(
                text = "Bank Phone: ${cardInfo!!.bank.phone}",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    viewModel.openDialer(context, cardInfo!!.bank.phone)
                }
            )
            Text(text = "Bank City: ${cardInfo!!.bank.city}")
        }
    }
}
