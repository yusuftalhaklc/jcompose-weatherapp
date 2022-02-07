package com.yusuftalhaklc.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yusuftalhaklc.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    page()
                }
            }
        }
    }
}
@Composable
fun page(){
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

@Composable
fun MainPage(navController: NavController){

    Column(modifier = Modifier.padding(10.dp)) {
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_more_horiz_24),
                    contentDescription = "menu"
                )
            }

        }
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
            backgroundColor = colorResource(id = R.color.Blue),
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
                Column(modifier = Modifier.fillMaxWidth(2f)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        box(R.drawable.wind,"WIND","34.2 km/j")
                        Spacer(modifier = Modifier
                            .padding(bottom = 0.dp, top = 0.dp)
                            .width(1.dp)
                            .height(70.dp)
                            .background(colorResource(id = R.color.LightBlue)))
                        box(R.drawable.temperature,"FEELS LIKE","10°")
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
                        box(R.drawable.sunnyy,"INDEX UV","2")
                        Spacer(modifier = Modifier
                            .padding(bottom = 0.dp, top = 0.dp, start = 5.dp)
                            .width(1.dp)
                            .height(70.dp)
                            .background(colorResource(id = R.color.LightBlue)))
                        box(R.drawable.pulserate,"PRESSURE","1014 mbar")
                    }

                }
            }


        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Today", fontWeight = FontWeight.SemiBold)
            Row(modifier = Modifier.clickable { navController.navigate("sevendays") }) {
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
            }

        }
        sevenDay()
    }
    val systemUiController = rememberSystemUiController()

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setNavigationBarColor(
            color = Color.White
        )
        // setStatusBarsColor() and setNavigationBarColor() also exist
    }
}
@Composable
fun box(pic:Int,title:String,content:String){

    Row(//modifier = Modifier.padding(start =20.dp,bottom=3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            Image(painter = painterResource(id = pic ),
                contentDescription =title,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.W300,
                    fontSize = 14.sp,
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
fun sevenDay(){
    LazyRow(
        content = {
            item{
                sevenDayCard(12,"Now",R.color.white,R.color.white,R.color.Blue)
                sevenDayCard(14,"22°")
                sevenDayCard(16,"26°")
                sevenDayCard(18,"25°")
                sevenDayCard(20,"31°")
                sevenDayCard(22,"27°")
            }
        }
    )
}

@Composable
fun sevenDayCard(
    time:Int,
    temp:String,
    timeColor:Int = R.color.gray,
    tempColor:Int = R.color.black,
    backgroundColor:Int = R.color.white){

    Card(shape = RoundedCornerShape(10.dp),
        backgroundColor = colorResource(id = backgroundColor),
        modifier = Modifier
            .width(70.dp)
            .height(120.dp)
            .padding(10.dp)
    ) {

        Column(verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "${time.toString()}:00",fontSize = 12.sp,color = colorResource(timeColor))
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {

    }
}