package com.yusuftalhaklc.weatherapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yusuftalhaklc.weatherapp.data.AppPref
import com.yusuftalhaklc.weatherapp.service.WeatherAPI
import com.yusuftalhaklc.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    lateinit var colorID:MutableState<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavPage()
                }
            }
        }
}

@Composable
fun NavPage(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable(route = "main") {
            MainPage(navController)
        }
        composable(route = "sevendays") {
            NextSevenDays()
        }
    }
}

fun colorApp(colorText:String  ) {

     var color:Int?=null
        when(colorText){
            "Blue" -> color = R.color.Blue
            "Green" -> color = R.color.appGreen
            "Red" -> color = R.color.appRed
            "Orange" -> color = R.color.appOrange
            "Purple" -> color = R.color.appPurple
        }
    colorID.value = color!!
}

@Composable
fun MainPage(navController: NavController){
    var precolor = 0
    colorID = remember {
        mutableStateOf(0)
    }

    val ap = AppPref(LocalContext.current)

    LaunchedEffect(key1 = true ){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            ap.setColor(colorID.value)

            precolor = ap.getColor()!!.toInt()
        }

    }
    Log.e("COLOR",precolor.toString())
    colorID.value = precolor


    ChangeNavigationBarColor(R.color.white)
    Column(modifier = Modifier
        .padding(10.dp)
        .verticalScroll(rememberScrollState())) {
        TopInfoRow()
        CurrentInfo()
        BottomInfoRow(navController)
        HourlyWeatherInfoRow()
    }
    ChangeNavigationBarColor(BarColor = colorID.value)
}

@Composable
fun CurrentInfo(){

    Log.e("COLOR ID CARD",colorID.toString())
    Row(modifier = Modifier.padding(10.dp)){
        Text(
            text = "Istanbul"+",",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(start=4.dp)
        )
        Text(
            text = "Turkey",
            fontWeight = FontWeight.Light,
            fontSize = 25.sp,
            modifier = Modifier.padding(start=3.dp)

        )
    }

    Card (
        shape = RoundedCornerShape(20.dp),
        backgroundColor = colorResource(id = colorID.value),
        contentColor = Color.White,
        modifier =Modifier.padding(15.dp)
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.padding(15.dp))
            Image(
                painter = painterResource(id = R.drawable.sunny),
                contentDescription = "weather image",
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Text(
                text = "Mostly cloudy",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = "Monday, 07 Feb",
                fontWeight = FontWeight.Light,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = " 12"+"°",
                fontWeight = FontWeight.SemiBold,
                fontSize = 100.sp
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Spacer(modifier = Modifier
                .padding(bottom = 0.dp, top = 5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(id = R.color.LightBlue)))
            Column(modifier = Modifier.fillMaxWidth(1f)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {


                    CurrentWeatherDetailBox(R.drawable.wind,"WIND","34.2 km/j")

                    Spacer(modifier = Modifier
                        .padding(bottom = 0.dp, top = 0.dp)
                        .width(1.dp)
                        .height(70.dp)
                        .background(colorResource(id = R.color.LightBlue)))


                    CurrentWeatherDetailBox(R.drawable.temperature,"FEELS LIKE","10°")
                }
                Spacer(modifier = Modifier
                    .padding(bottom = 0.dp, top = 0.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colorResource(id = R.color.LightBlue)))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    CurrentWeatherDetailBox(R.drawable.sunnyy,"INDEX UV","2")
                    Spacer(modifier = Modifier
                        .padding(bottom = 0.dp, top = 0.dp, start = 5.dp)
                        .width(1.dp)
                        .height(70.dp)
                        .background(colorResource(id = R.color.LightBlue)))
                    CurrentWeatherDetailBox(R.drawable.pulserate,"PRESSURE","1014 mbar")
                }

            }
        }


    }
}

@Composable
fun TopInfoRow(){
    val dropDownMenuExpanded = remember {
        mutableStateOf(false)
    }
    val appColorText = remember {
        mutableStateOf("Blue")
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                contentDescription = "menu"
            )
        }
        IconButton(onClick = { 
            dropDownMenuExpanded.value = !dropDownMenuExpanded.value
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_more_horiz_24),
                contentDescription = "menu"
            )

            DropdownMenu(expanded = dropDownMenuExpanded.value,
                onDismissRequest = {
                    dropDownMenuExpanded.value = false
                }) {

                DropdownMenuItem(onClick = {
                    dropDownMenuExpanded.value = false
                    appColorText.value = "Blue"
                }) {
                    Box(modifier = Modifier
                        .size(15.dp, 15.dp)
                        .background(
                            colorResource(id = R.color.Blue),
                            CircleShape
                        ))
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text("Blue")
                }

                DropdownMenuItem(onClick = {
                    dropDownMenuExpanded.value = false
                    appColorText.value = "Green"
                }) {
                    Box(modifier = Modifier
                        .size(15.dp, 15.dp)
                        .background(
                            colorResource(id = R.color.appGreen),
                            CircleShape
                        ))
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text("Green")
                }

                DropdownMenuItem(onClick = {
                    dropDownMenuExpanded.value = false
                    appColorText.value = "Red"
                }) {
                    Box(modifier = Modifier
                        .size(15.dp, 15.dp)
                        .background(
                            colorResource(id = R.color.appRed),
                            CircleShape
                        ))
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text("Red")
                }
                DropdownMenuItem(onClick = {
                    dropDownMenuExpanded.value = false
                    appColorText.value = "Orange"
                }) {
                    Box(modifier = Modifier
                        .size(15.dp, 15.dp)
                        .background(
                            colorResource(id = R.color.appOrange),
                            CircleShape
                        ))
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text("Orange")
                }

                DropdownMenuItem(onClick = {
                    dropDownMenuExpanded.value = false
                    appColorText.value = "Purple"
                }) {
                    Box(modifier = Modifier
                        .size(15.dp, 15.dp)
                        .background(
                            colorResource(id = R.color.appPurple),
                            CircleShape
                        ))
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text("Purple")
                }


            }
        }

    }
    colorApp(colorText = appColorText.value)
}


@Composable
fun BottomInfoRow(navController: NavController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Today", fontWeight = FontWeight.SemiBold)
        /*Row(modifier = Modifier.clickable { navController.navigate("sevendays") }) {
            Text(text = "Next 7 Days ",
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.gray)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .padding(top = 4.dp) ,
                contentDescription = "Next 7 Days")
        }*/
    }
}

@Composable
fun CurrentWeatherDetailBox(pic:Int,title:String,content:String){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            Image(painter = painterResource(id = pic ),
                contentDescription = title,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.W300,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Text(
                    text = content,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )

            }
        }
}

@Composable
fun HourlyWeatherInfoRow(){
    LazyRow(
        content = {
            item{
                HourlyWeatherCard(12,"Now",R.color.white,R.color.white,colorID.value)
                HourlyWeatherCard(14,"22°")
                HourlyWeatherCard(16,"26°")
                HourlyWeatherCard(18,"25°")
                HourlyWeatherCard(20,"31°")
                HourlyWeatherCard(24,"27°")

            }
        }
    )
}

@Composable
fun HourlyWeatherCard(
    time:Int,
    temp:String,
    timeColor:Int = R.color.gray,
    tempColor:Int = R.color.black,
    backgroundColor:Int = R.color.white) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = colorResource(id = backgroundColor),
        modifier = Modifier
            .width(70.dp)
            .height(120.dp)
            .padding(10.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "${time.toString()}:00", fontSize = 12.sp, color = colorResource(timeColor))
            Image(
                painter = painterResource(id = R.drawable.sunny),
                contentDescription = "weather image",
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
            Text(
                text = "${temp}",
                color = colorResource(tempColor),
                fontSize = 14.sp
            )

        }
    }
    }
}
