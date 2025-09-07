package com.example.realtimeweather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realtimeweather.api.NetworkResponse
import com.example.realtimeweather.api.WeatherModel
//asyncimage
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WeatherPage(viewModel: WeatherViewModel){

    var city by remember  {
        mutableStateOf("")
    }

    val weatherResult = viewModel.weatherResult.observeAsState()

    //to hide keyboard when we search
     val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = city,
                onValueChange = {
                    city = it
                },

                label = {
                    Text(text = "Search for any location")
                }
            )

            IconButton(onClick = {
                "TODO"
                viewModel.getData(city)
                keyboardController?.hide()
            }) {
                //hide keyboard called here onClick
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            }
        }

        when(val result = weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data) // ✅ Now type-safe
            }
            null -> {}

            }

        }
    }

@Composable
fun WeatherDetails(data : WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier
                    .size(40.dp)
            )
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
        }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${data.current.temp_c} ℃",
                fontWeight = FontWeight.Bold,
                fontSize = 56.sp,
                textAlign = TextAlign.Center
            )

            AsyncImage(
                modifier = Modifier.size(160.dp),
                model = "https:${data.current.condition.icon}".replace("64*64", "128*128"),
                contentDescription = "Condition icon",
            )

            Text(
                text = "${data.current.condition.text}",
                fontWeight = FontWeight.Bold,
                fontSize = 56.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherKeyVal("Humidity", data.current.humidity)
                        WeatherKeyVal("Wind Speed", data.current.wind_kph + " km/h ")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherKeyVal("UV", data.current.uv)
                        WeatherKeyVal("precipitation", data.current.precip_mm + ",,")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherKeyVal("Local time", data.location.localtime.split("")[1])
                        WeatherKeyVal("Local Date", data.current.precip_mm.split("")[0])
                    }
                }
            }
        }
    }



@Composable
fun WeatherKeyVal(key : String, value :  String){
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value , fontSize = 24.sp,fontWeight = FontWeight.Bold)
        Text(text  = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}