package com.fit2081.kenzierivanwiguna_34726896_a1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.fit2081.kenzierivanwiguna_34726896_a1.data.Country.Country
import com.fit2081.kenzierivanwiguna_34726896_a1.data.Country.CountryRepository
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: PatientViewModel = ViewModelProvider(
                this, PatientViewModel.PatientViewModelFactory(this@RegisterScreen)
            )[PatientViewModel::class.java]
            KenzieRivanWiguna_34726896_A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterScreen(modifier = Modifier.padding(innerPadding), viewModel)
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(modifier: Modifier, viewModel: PatientViewModel) {
    val context = LocalContext.current
    val purpleColor = Color(0xFF7F32D5)
    var idSelected by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var countrySelected by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

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
                        text = "Register",
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
                        OutlinedTextField(
                            value = userName,
                            onValueChange = { userName = it },
                            label = {
                                Text(
                                    text = "Name",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            placeholder = { Text("Enter your name") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(30)
                        )
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text(
                                text = "Phone Number",
                                fontWeight = FontWeight.Bold
                            ) },
                            placeholder = { Text("Enter your phone number") },
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(30)
                        )
                        OutlinedTextField(
                            value = countrySelected,
                            onValueChange = { countrySelected = it },
                            label = { Text(
                                text = "Country",
                                fontWeight = FontWeight.Bold
                            ) },
                            placeholder = { Text("Choose your country") },
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(30),
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { expanded = true }) {
                                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                                }
                            }
                        )
                        var repo: CountryRepository = CountryRepository()
                        var countries by remember { mutableStateOf<List<Country>>(emptyList()) }
                        LaunchedEffect(Unit) {
                            countries = repo.getAllCountries().sortedBy() { it.name.common }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            countries.forEach { country ->
                                DropdownMenuItem(
                                    text = { Text(country.name.common) },
                                    onClick = {
                                        countrySelected = country.name.common
                                        expanded = false
                                    }
                                )
                            }
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
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text(
                                text = "Confirm Password",
                                fontWeight = FontWeight.Bold
                            ) },
                            placeholder = { Text("Enter your password again") },
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = PasswordVisualTransformation(),
                            shape = RoundedCornerShape(30)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "This is app is only for pre-registered users. Please enter\n" +
                                "your ID, phone number and password to claim your " +
                                "account.",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    val coroutineScope = rememberCoroutineScope()

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                if (countrySelected.isNotEmpty()) {
                                    if (password == confirmPassword) {
                                        coroutineScope.launch {
                                            val patient = viewModel.getPatientById(idSelected)

                                            if (patient?.userPass != null) {
                                                Toast.makeText(context, "Patient already registered", Toast.LENGTH_LONG).show()
                                            } else {
                                                val success = viewModel.registerUser(
                                                    idSelected,
                                                    userName,
                                                    phoneNumber,
                                                    countrySelected,
                                                    password
                                                )

                                                if (success) {
                                                    Toast.makeText(context, "Registration Successful!", Toast.LENGTH_LONG).show()
                                                    val intent = Intent(context, LoginScreen::class.java)
                                                    context.startActivity(intent)
                                                } else {
                                                    Toast.makeText(context, "Invalid ID or phone number", Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        }
                                    } else {
                                        Toast.makeText(context, "Password does not match", Toast.LENGTH_LONG).show()
                                    }
                                } else {
                                    Toast.makeText(context, "Please select a country", Toast.LENGTH_LONG).show()
                                }
                            },
                            modifier = Modifier.padding(8.dp).height(60.dp).weight(1f),
                            shape = RoundedCornerShape(25),
                            colors = ButtonDefaults.buttonColors(containerColor = purpleColor)
                        ) {
                            Text(
                                text = "Register",
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                val intent = Intent(context, LoginScreen::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(8.dp).height(60.dp).weight(1f),
                            shape = RoundedCornerShape(25),
                            colors = ButtonDefaults.buttonColors(containerColor = purpleColor)
                        ) {
                            Text(
                                text = "Login",
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
