package com.fit2081.kenzierivanwiguna_34726896_a1

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import com.fit2081.kenzierivanwiguna_34726896_a1.data.AuthManager
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader

class InsightsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val currentScreen = "Insights"
            val patientViewModel: PatientViewModel = ViewModelProvider(
                this, PatientViewModel.PatientViewModelFactory(this@InsightsScreen)
            )[PatientViewModel::class.java]
            KenzieRivanWiguna_34726896_A1Theme {
                Scaffold( modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(currentScreen) }) { innerPadding ->
                    InsightsScreen(modifier = Modifier.padding(innerPadding), patientViewModel)
                }
            }
        }
    }
}

@Composable
fun InsightsScreen(modifier: Modifier, patientViewModel: PatientViewModel) {
    val currentId = AuthManager.getStudentId().toString()
    var currentPatient: Patient
    runBlocking {
        currentPatient = patientViewModel.getPatientById(currentId)
    }
    val vegetables by remember { mutableStateOf(currentPatient.vegetableHeifaScore) }
    val fruits by remember { mutableStateOf(currentPatient.fruitHeifaScore) }
    val grains by remember { mutableStateOf(currentPatient.grainsCerealHeifaScore) }
    val wholeGrains by remember { mutableStateOf(currentPatient.wholeGrainsHeifaScore) }
    val meat by remember { mutableStateOf(currentPatient.meatHeifaScore) }
    val dairy by remember { mutableStateOf(currentPatient.dairyHeifaScore) }
    val water by remember { mutableStateOf(currentPatient.waterHeifaScore) }
    val unsatFat by remember { mutableStateOf(currentPatient.unsatFatHeifaScore) }
    val satFat by remember { mutableStateOf(currentPatient.satFatHeifaScore) }
    val sodium by remember { mutableStateOf(currentPatient.sodiumHeifaScore) }
    val sugar by remember { mutableStateOf(currentPatient.sugarHeifaScore) }
    val alcohol by remember { mutableStateOf(currentPatient.alcoholHeifaScore) }
    val discFood by remember { mutableStateOf(currentPatient.discretionaryHeifaScore) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Insights: Food Score",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProgressBar("Vegetables", vegetables,10f)

        ProgressBar("Fruits", fruits, 10f)

        ProgressBar("Grains & Cereals", grains, 5f)

        ProgressBar("Whole Grains", wholeGrains, 5f)

        ProgressBar("Meat & Alternatives", meat, 10f)

        ProgressBar("Dairy", dairy, 10f)

        ProgressBar("Water", water, 5f)

        ProgressBar("Unsaturated Fats", unsatFat, 5f)

        ProgressBar("Saturated Fats", satFat, 5f)

        ProgressBar("Sodium", sodium, 10f)

        ProgressBar("Sugar", sugar, 10f)

        ProgressBar("Alcohol", alcohol, 5f)

        ProgressBar("Discretionary Foods", discFood, 10f)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total Food Quality Score",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            fontSize = 18.sp
        )
        val totalScore = currentPatient.heifaTotalScore

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(0.dp,5.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            LinearProgressIndicator(
                progress = {totalScore/100f},
                color = Color(0xFF7F32D5),
                modifier = Modifier.weight(0.75f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${totalScore}/100.0",
                fontSize = 14.sp,
                modifier = Modifier.weight(0.25f)
            )
        }
        val shareText = "My total food quality score is ${totalScore}/100!"
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                val shareIntent = Intent(ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
                context.startActivity(Intent.createChooser(shareIntent, "Share text via"))
            },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF7F32D5))
            ) {
                Text("Share with someone")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    val intent = Intent(context, NutriCoachScreen::class.java)
                    context.startActivity(intent)
                },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(containerColor =  Color(0xFF7F32D5))
            ) {
                Text("Improve my diet!")
            }
        }
    }
}

@Composable
fun ProgressBar(label: String, sliderValue: Float, maxScore: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(0.dp,5.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.3f)
        )

        LinearProgressIndicator(
            progress = {sliderValue/maxScore},
            color = Color(0xFF7F32D5),
            modifier = Modifier.weight(0.45f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "${sliderValue}/${maxScore}",
            fontSize = 14.sp,
            modifier = Modifier.weight(0.175f)
        )
    }
}
