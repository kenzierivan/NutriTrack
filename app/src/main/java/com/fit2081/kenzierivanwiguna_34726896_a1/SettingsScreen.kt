package com.fit2081.kenzierivanwiguna_34726896_a1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.kenzierivanwiguna_34726896_a1.data.AuthManager
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme
import kotlinx.coroutines.runBlocking
import androidx.core.content.edit
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi

class SettingsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val currentScreen = "Settings"
            val patientViewModel: PatientViewModel = ViewModelProvider(
                this, PatientViewModel.PatientViewModelFactory(this@SettingsScreen)
            )[PatientViewModel::class.java]
            KenzieRivanWiguna_34726896_A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(currentScreen) }) { innerPadding ->
                    SettingsScreen(modifier = Modifier.padding(innerPadding), patientViewModel)
                }
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun SettingsScreen(modifier: Modifier = Modifier, patientViewModel: PatientViewModel) {
    Column(
        modifier = Modifier.padding(24.dp,36.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        val currentId = AuthManager.getStudentId().toString()
        var currentPatient: Patient
        runBlocking {
            currentPatient = patientViewModel.getPatientById(currentId)
        }
        val patientName = currentPatient.userName
        val patientNum = currentPatient.phoneNumber
        val patientId = currentPatient.userId
        //Account section
        Column() {
            Text(
                text = "ACCOUNT",
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Name",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "$patientName",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "phone number",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "$patientNum",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "id",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "$patientId",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider(
                color = Color(0xFFF3F3F3),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        //Other settings section
        Spacer(modifier = Modifier.height(24.dp))
        val context = LocalContext.current
        Column() {
            Text(
                text = "OTHER SETTINGS",
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable{
                    val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    sharedPrefs.edit() { putString("idLoggedIn", null) }

                    val intent = Intent(context, LoginScreen::class.java)
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "logout",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Logout",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(214.dp))
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "logout",
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable{
                    val intent = Intent(context, ClinicianLogin::class.java)
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Clinician login",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Clinician Login",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(147.dp))
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Clinician Login",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

