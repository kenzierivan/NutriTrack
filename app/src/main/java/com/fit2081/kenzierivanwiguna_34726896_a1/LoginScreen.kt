package com.fit2081.kenzierivanwiguna_34726896_a1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.kenzierivanwiguna_34726896_a1.data.AuthManager
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme
import kotlinx.coroutines.runBlocking

class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: PatientViewModel = ViewModelProvider(
                this, PatientViewModel.PatientViewModelFactory(this@LoginScreen)
            )[PatientViewModel::class.java]

            KenzieRivanWiguna_34726896_A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding), viewModel)
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier, viewModel: PatientViewModel) {
    val context = LocalContext.current
    val purpleColor = Color(0xFF7F32D5)
    var idSelected by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoggedIn = remember { mutableStateOf(false) }
    val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = purpleColor
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(4,4,0,0)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(36.dp))
                    Text(
                        text = "Log In",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = idSelected,
                            onValueChange = { idSelected = it },
                            label = {
                                Text(
                                    text = "My ID (Provided by your Clinician)",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            placeholder = { Text("Enter your id") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(30)
                        )
                    }
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(
                            text = "Password",
                            fontWeight = FontWeight.Bold
                        ) },
                        placeholder = { Text("Enter your password") },
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(30)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This is app is only for pre-registered users. Please enter\n" +
                                "your ID and password or Register to claim your " +
                                "account on your first visit",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            isLoggedIn.value = isValidLogin(idSelected, password, viewModel)
                            if(isLoggedIn.value) {
                                AuthManager.login(idSelected)

                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                                sharedPrefs.edit().putString("idLoggedIn", AuthManager.getStudentId()).apply()

                                val intent = Intent(context, FoodQuestionnaire1::class.java)
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(8.dp).height(60.dp),
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(containerColor = purpleColor)
                    ) {
                        Text(
                            text = "Continue",
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val intent = Intent(context, RegisterScreen::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth().padding(8.dp).height(60.dp),
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(containerColor = purpleColor)
                    ) {
                        Text(
                            text = "Register",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

fun isValidLogin(id: String, password: String, viewModel: PatientViewModel): Boolean {
    var patient: Patient? = null

    runBlocking {
        patient = viewModel.getPatientById(id)
    }

    if(patient == null) return false
    if(patient!!.userPass != password) return false

    return true
}