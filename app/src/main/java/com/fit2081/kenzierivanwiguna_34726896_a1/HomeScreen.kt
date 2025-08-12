package com.fit2081.kenzierivanwiguna_34726896_a1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint.Align
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.data.AuthManager
import com.fit2081.kenzierivanwiguna_34726896_a1.data.GenAI.GenAIViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.data.GenAI.UiState
import com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake.FoodIntake
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.ceil

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val currentScreen = "Home"
            val patientViewModel: PatientViewModel = ViewModelProvider(
                this, PatientViewModel.PatientViewModelFactory(this@HomeScreen)
            )[PatientViewModel::class.java]
            KenzieRivanWiguna_34726896_A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(currentScreen) }) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding), patientViewModel)
                }
            }
        }
    }
}



@Composable
fun HomeScreen(modifier: Modifier, patientViewModel: PatientViewModel) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeScreenHeader(patientViewModel)
        HomeScreenBody(patientViewModel)
    }
}

@Composable
fun HomeScreenHeader(patientViewModel: PatientViewModel) {
    val context = LocalContext.current
    val currentId = AuthManager.getStudentId().toString()
    var currentPatient: Patient
    runBlocking {
        currentPatient = patientViewModel.getPatientById(currentId)
    }
    Column(
        modifier = Modifier.padding(24.dp,4.dp).fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Hello,",
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            fontSize = 16.sp,
            lineHeight = 10.sp
        )
        Text(
            text = "${currentPatient.userName}",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row() {
            Text(
                text = "You've already filled in your Food Intake\n" +
                        "Questionnaire, but you can change details here:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    val intent = Intent(context, FoodQuestionnaire1::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.height(40.dp).width(120.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F32D5)),
                shape = RoundedCornerShape(25),
                contentPadding = PaddingValues(4.dp)
            ) {
                Text(
                    text = "Edit"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.food_plate),
            contentDescription = "Food Plate",
            modifier = Modifier.fillMaxWidth().height(270.dp)
        )
    }
}

@Composable
fun HomeScreenBody(patientViewModel: PatientViewModel) {
    val context = LocalContext.current
    val currentId = AuthManager.getStudentId().toString()
    var currentPatient: Patient
    runBlocking {
        currentPatient = patientViewModel.getPatientById(currentId)
    }
    val score  = ceil(currentPatient.heifaTotalScore)
    val scoreColor = if (score >= 80) Color(0xFF789E74)
                     else if(score < 80 && score >= 50) Color(0xFFDBD13D)
                     else Color(0xFFD12E4C)

    Column(
        modifier = Modifier.padding(24.dp,0.dp).fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Score",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    val intent = Intent(context, InsightsScreen::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text(
                    text = "See all scores",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Insights",
                    tint = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFEDEDED), shape = RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Arrow up",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Your Food Quality Score",
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${score}/100",
                    fontWeight = FontWeight.Medium,
                    color = scoreColor
                )
            }

        }
        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            color = Color(0xFFF3F3F3),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        Text(
            text = "Daily Fun Fact",
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )
        val genAIViewModel: GenAIViewModel = viewModel()
        var result by rememberSaveable { mutableStateOf("") }
        val uiState by genAIViewModel.uiState.collectAsState()
        val predefinedPrompt = "Generate a fun fact about ${currentPatient.country} food, focusing on the nutritional benefits or health aspects of a traditional dish or ingredient. Keep it under 70 words, highlighting its impact on well-being or how it contributes to a balanced diet."
        LaunchedEffect(Unit) {
            genAIViewModel.sendPrompt(predefinedPrompt)
        }
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

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Optional: adds shadow
                colors = CardDefaults.cardColors(containerColor = Color.White) // Optional: card background color
            ) {
                Text(
                    text = result,
                    color = textColor,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(8.dp) // Padding inside the card
                )
            }
        }
    }
}

@Composable
fun BottomBar(currentScreen: String) {
    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf(currentScreen) }
    Spacer(modifier = Modifier.height(32.dp))
    Divider(
        color = Color(0xFFF3F3F3),
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth()
    )
    BottomAppBar(
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomBarItem(
                label = "Home",
                icon = Icons.Filled.Home,
                isSelected = selectedItem == "Home",
                onClick = {
                    selectedItem = "Home"
                    val intent = Intent(context, HomeScreen::class.java)
                    context.startActivity(intent)
                }
            )
            BottomBarItem(
                label = "Insights",
                icon = Icons.Filled.Info,
                isSelected = selectedItem == "Insights",
                onClick = {
                    selectedItem = "Insights"
                    val intent = Intent(context, InsightsScreen::class.java)
                    context.startActivity(intent)
                }
            )
            BottomBarItem(
                label = "NutriCoach",
                icon = Icons.Filled.Face,
                isSelected = selectedItem == "NutriCoach",
                onClick = {
                    selectedItem = "NutriCoach"
                    val intent = Intent(context, NutriCoachScreen::class.java)
                    context.startActivity(intent)
                }
            )
            BottomBarItem(
                label = "Settings",
                icon = Icons.Filled.Settings,
                isSelected = selectedItem == "Settings",
                onClick = {
                    selectedItem = "Settings"
                    val intent = Intent(context, SettingsScreen::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun BottomBarItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) Color(0xFF6200EE) else Color.Gray

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color
            )
        }
        Text(
            text = label,
            fontSize = 10.sp,
            color = color
        )
    }
}
