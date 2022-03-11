package com.sal7one.musicswitcher.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.compose.ui.theme.AppPrimary_color
import com.sal7one.musicswitcher.compose.ui.theme.primary_gradient_color
import com.sal7one.musicswitcher.controllers.ApplicationViewModel
import com.sal7one.musicswitcher.controllers.MyViewModelFactory
import com.sal7one.musicswitcher.repository.DataStoreProvider
import com.sal7one.musicswitcher.repository.model.MusicProvider
import com.sal7one.musicswitcher.repository.musicProviders


@Composable
fun OptionScreen() {
    val context = LocalContext.current
    val dataStoreProvider = DataStoreProvider(context.applicationContext).getInstance()
    val viewModel: ApplicationViewModel = viewModel(factory = MyViewModelFactory(dataStoreProvider))



    val appleMusicChoice = remember { mutableStateOf(viewModel.appleMusicChoice.value) }
    val spotifyChoice = remember { mutableStateOf(viewModel.spotifyChoice.value) }
    val anghamiChoice = remember { mutableStateOf(viewModel.anghamiChoice.value) }
    val ytMusicChoice = remember { mutableStateOf(viewModel.ytMusicChoice.value) }
    val deezerChoice = remember { mutableStateOf(viewModel.deezerChoice.value) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppPrimary_color,
                        AppPrimary_color,
                        AppPrimary_color,
                        primary_gradient_color
                    )
                )
            )
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.optionsScreen_heading),
            style = TextStyle(color = Color.White, fontSize = 24.sp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(R.string.optionScreen_explain),
                modifier = Modifier
                    .padding(20.dp)
                    .scale(1.1f),
                style = MaterialTheme.typography.h2
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        OptionList(musicProviders)
        Spacer(modifier = Modifier.height(40.dp))
        Box(modifier = Modifier.clickable {

        }) {
            UpdateButton(
                stringResource(R.string.optionScreen_update_Exceptions)
            ) {
                viewModel.saveExceptions(
                    appleMusic = appleMusicChoice.value,
                    spotify = spotifyChoice.value,
                    anghami = anghamiChoice.value,
                    ytMusic = ytMusicChoice.value,
                    deezer = deezerChoice.value
                )
                Toast.makeText(context, "Updated Exceptions", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun  OptionList(musicProviders: List<MusicProvider>) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(start = 40.dp)) {
                Text(
                    text = stringResource(R.string.musicprovider_text),
                    style = TextStyle(fontSize = 20.sp)
                )
            }
            Box(modifier = Modifier.padding(end = 20.dp)) {
                Text(
                    text = stringResource(R.string.exception_status),
                    style = TextStyle(fontSize = 20.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(5.dp))
        musicProviders.forEach { musicProviderOption ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(start = 51.dp)) {
                    Text(
                        text = musicProviderOption.name,
                        style = TextStyle(fontSize = 21.sp)
                    )
                }
                Box(modifier = Modifier.padding(end = 65.dp)) {
                    Switch(musicProviderOption.overrulesPreference){
                        musicProviderOption.overrulesPreference = it
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
        Spacer(modifier = Modifier.height(5.dp))
        Divider()
    }
}