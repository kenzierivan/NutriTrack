package com.fit2081.kenzierivanwiguna_34726896_a1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.fit2081.kenzierivanwiguna_34726896_a1.data.AuthManager
import com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake.FoodIntake
import com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake.FoodIntakeViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme
import java.io.BufferedReader
import java.io.InputStreamReader
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KenzieRivanWiguna_34726896_A1Theme {

                val patientViewModel: PatientViewModel = ViewModelProvider(
                    this, PatientViewModel.PatientViewModelFactory(this@MainActivity)
                )[PatientViewModel::class.java]
                val foodIntakeViewModel: FoodIntakeViewModel = ViewModelProvider(
                    this, FoodIntakeViewModel.FoodIntakeViewModelFactory(this@MainActivity)
                )[FoodIntakeViewModel::class.java]

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    WelcomePage(modifier = Modifier.padding(innerPadding), patientViewModel, foodIntakeViewModel)
                }
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun WelcomePage(modifier: Modifier = Modifier, patientViewModel: PatientViewModel, foodIntakeViewModel:FoodIntakeViewModel) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val idLogged = sharedPrefs.getString("idLoggedIn", null)
    if (idLogged != null) {
        Log.d("idLoggedIn", idLogged)
    }
    if (idLogged != null) {
        AuthManager.login(idLogged)
        val intent = Intent(context, HomeScreen::class.java)
        context.startActivity(intent)
    }
    val dataLoaded = sharedPrefs.getBoolean("dataLoaded", false)
//    context.deleteDatabase("nutritrack_database")
//    sharedPrefs.edit().putBoolean("dataLoaded", false).apply()
    if(!dataLoaded) {
        loadPatientData(context, "2081_Dataset.csv", patientViewModel, foodIntakeViewModel)
        sharedPrefs.edit().putBoolean("dataLoaded", true).apply()
    }

    Column(
        modifier = modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(140.dp))
        Image(
            painter = painterResource(id = R.drawable.nutritrack_logo1),
            contentDescription = "Nutritrack Logo",
            modifier = Modifier.fillMaxWidth().height(220.dp).padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This app provides general health and nutrition information\n" +
                    "for educational purposes only. It is not intended as\n" +
                    "medical advice, diagnosis, or treatment. Always consult a\n" +
                    "qualified healthcare professional before making any\n" +
                    " changes to your diet, exercise, or health regimen.\n" +
                    "Use this app at your own risk.\n" +
                    "If you’d like to an Accredited Practicing Dietitian (APD),\n" +
                    "please visit the Monash Nutrition/Dietetics Clinic\n" +
                    " (discounted rates for students):\n" +
                    "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition",

            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))


        Button(onClick = {
            val intent = Intent(context, LoginScreen::class.java)
            context.startActivity(intent)
        },
            modifier = Modifier.width(350.dp).height(50.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F32D5))
        ) {
            Text(
                text = "Login",
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.height(90.dp))

        Text(
            text = "Designed with ❤ by Kenzie Rivan (34726896)",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

fun loadPatientData(context: Context, fileName: String, patientViewModel: PatientViewModel, foodIntakeViewModel: FoodIntakeViewModel) {
    val assets = context.assets

    try {
        val inputStream = assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))

        reader.useLines { lines ->
            val dataLines = lines.drop(1)
            dataLines.forEach { line ->
                val values = line.split(",")
                var sexIncrement = if (values[2] == "Female") 1 else 0

                patientViewModel.insert(
                    Patient(
                        userId = values[1],
                        userName = null,
                        userPass = null,
                        phoneNumber = values[0],
                        country = null,
                        sex = values[2],
                        heifaTotalScore = values[3+sexIncrement].toFloat(),
                        discretionaryHeifaScore = values[5+sexIncrement].toFloat(),
                        vegetableHeifaScore = values[8+sexIncrement].toFloat(),
                        fruitHeifaScore = values[19+sexIncrement].toFloat(),
                        fruitServeSize = values[21].toFloat(),
                        fruitVariationScore = values[22].toFloat(),
                        grainsCerealHeifaScore = values[29+sexIncrement].toFloat(),
                        wholeGrainsHeifaScore = values[33+sexIncrement].toFloat(),
                        meatHeifaScore = values[36+sexIncrement].toFloat(),
                        dairyHeifaScore = values[40+sexIncrement].toFloat(),
                        sodiumHeifaScore = values[43+sexIncrement].toFloat(),
                        alcoholHeifaScore = values[46+sexIncrement].toFloat(),
                        waterHeifaScore = values[49+sexIncrement].toFloat(),
                        sugarHeifaScore = values[54+sexIncrement].toFloat(),
                        satFatHeifaScore = values[57+sexIncrement].toFloat(),
                        unsatFatHeifaScore = values[60+sexIncrement].toFloat()
                    )
                )
                foodIntakeViewModel.insert(
                    FoodIntake(
                        userId = values[1],
                        eatFruits = false,
                        eatVegetables = false,
                        eatGrains = false,
                        eatMeats = false,
                        eatSeafood = false,
                        eatPoultry = false,
                        eatFish = false,
                        eatEggs = false,
                        eatNuts = false,
                        persona = "",
                        mealTime = "00:00",
                        sleepTime = "00:00",
                        wakeTime = "00:00"
                    )
                )
            }
        }
    } catch (e: Exception){
        e.printStackTrace()
    }
}