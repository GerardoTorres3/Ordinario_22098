package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "card_list") {
                        composable("card_list") { CardList(navController) }
                        composable("age_guess") { AgeGuessScreen(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun CardList(navController: NavHostController) {
    val items = listOf(
        CardItem(1, "Adivina la edad", R.drawable.pastel),
        CardItem(2, "Gatos", R.drawable.gato),
        CardItem(3, "NBA", R.drawable.nba),
        CardItem(4, "Chuck Norris", R.drawable.chuck)
    )

    LazyColumn {
        items(items.size) { index ->
            CardItemView(items[index], navController)
        }
    }
}

data class CardItem(val number: Int, val title: String, val imageRes: Int)

@Composable
fun CardItemView(item: CardItem, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                if (item.title == "Adivina la edad") {
                    navController.navigate("age_guess")
                }
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.number.toString(),
                modifier = Modifier.padding(end = 16.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item.title)
            }
        }
    }
}

@Composable
fun AgeGuessScreen(navController: NavHostController) {
    val yearOfBirth = remember { mutableStateOf(TextFieldValue("")) }
    val age = remember { mutableStateOf("") }
    val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = yearOfBirth.value,
            onValueChange = { yearOfBirth.value = it },
            label = { Text("Ingrese su año de nacimiento") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val year = yearOfBirth.value.text.toIntOrNull()
                if (year != null) {
                    age.value = "Edad: ${currentYear - year}"
                } else {
                    age.value = "Por favor ingrese un año válido"
                }
            },
        ) {
            Text(text = "Calcular edad", color = MaterialTheme.colorScheme.onPrimary)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = age.value,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.nba), // Replace with your back icon resource
                contentDescription = "Volver"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "card_list") {
            composable("card_list") { CardList(navController) }
            composable("age_guess") { AgeGuessScreen(navController) }
        }
    }
}


