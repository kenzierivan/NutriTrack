package com.fit2081.kenzierivanwiguna_34726896_a1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.data.GenAI.GenAIViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.data.GenAI.UiState
import com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriCoachTips.NutriCoachTip
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme
import kotlinx.coroutines.runBlocking
import java.util.Date

class ClinicianDashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val currentScreen = "Settings"
            val patientViewModel: PatientViewModel = ViewModelProvider(
                this, PatientViewModel.PatientViewModelFactory(this@ClinicianDashboard)
            )[PatientViewModel::class.java]
            KenzieRivanWiguna_34726896_A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(currentScreen) }) { innerPadding ->
                    ClinicianDashboard(modifier = Modifier.padding(innerPadding), patientViewModel)
                }
            }
        }
    }
}

@Composable
fun ClinicianDashboard(modifier: Modifier = Modifier, patientViewModel: PatientViewModel) {
    var maleHeifaAverageScore by remember { mutableStateOf<Float?>(null) }
    var femaleHeifaAverageScore by remember { mutableStateOf<Float?>(null) }

    LaunchedEffect(Unit) {
        maleHeifaAverageScore = patientViewModel.getMaleAverageHeifaScore()
        femaleHeifaAverageScore = patientViewModel.getFemaleAverageHeifaScore()
    }

    Column(
        modifier = Modifier.padding(24.dp,36.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Clinician Dashboard",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(30.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(20),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = " Average HEIFA (Male)         :",
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(8f)
                    )
                    Text(
                        text = "$maleHeifaAverageScore",
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(3f)
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(30.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(20),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = " Average HEIFA (Female)     :",
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(8f)
                    )
                    Text(
                        text = "$femaleHeifaAverageScore",
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(3f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                color = Color(0xFFF3F3F3),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            var dataPattern = mutableListOf<String>()
            val patients by patientViewModel.allPatients.collectAsState(initial= emptyList())
            val patientsDataset = patients.map { patient ->
                "ID: ${patient.userId}," +
                        "Name: ${patient.userName}" +
                        "Sex: ${patient.sex}" +
                        "HEIFATotalScore: ${patient.heifaTotalScore}/100" +
                        "Discretionary Food Score: ${patient.discretionaryHeifaScore}/10" +
                        "Vegetables Score: ${patient.vegetableHeifaScore}/10" +
                        "Fruit Score: ${patient.fruitHeifaScore}/10" +
                        "Grains & Cereal Score: ${patient.grainsCerealHeifaScore}/5" +
                        "Whole Grains Score: ${patient.wholeGrainsHeifaScore}/5" +
                        "Meat Score: ${patient.meatHeifaScore}/10" +
                        "Dairy Score: ${patient.dairyHeifaScore}/10" +
                        "Sodium Score: ${patient.sodiumHeifaScore}/10" +
                        "Alcohol Score: ${patient.sodiumHeifaScore}/5" +
                        "Water Score: ${patient.waterHeifaScore}/5" +
                        "Sugar Score: ${patient.sugarHeifaScore}/10" +
                        "Saturated Fat Score: ${patient.satFatHeifaScore}/5" +
                        "Unsaturated Fat Score: ${patient.unsatFatHeifaScore}/5"
            }
            Spacer(modifier = Modifier.height(16.dp))
            val genAIViewModel: GenAIViewModel = viewModel()
            var result by rememberSaveable { mutableStateOf("") }
            val uiState by genAIViewModel.uiState.collectAsState()
            val predefinedPrompt = "$patientsDataset. Given the dataset, I want you to identify and return three key data patterns. These insights are then presented in a clear, structured list. Each point should not exceed 55 words. No need to give header"
            Button(
                onClick = {
                    genAIViewModel.sendPrompt(predefinedPrompt)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
            ) {
                Text(text = "Find Data Pattern (AI)")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState is UiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color(0xFF6200EE)
                )
            } else {
                var textColor = MaterialTheme.colorScheme.onSurface

                if (uiState is UiState.Error) {
                    textColor = MaterialTheme.colorScheme.error
                    result = (uiState as UiState.Error).errorMessage
                }
                else if (uiState is UiState.Success) {
                    textColor = MaterialTheme.colorScheme.onSurface
                    result = (uiState as UiState.Success).outputText
                }

                Text(
                    text = result,
                    color = textColor
                )
            }
        }
    }
}

