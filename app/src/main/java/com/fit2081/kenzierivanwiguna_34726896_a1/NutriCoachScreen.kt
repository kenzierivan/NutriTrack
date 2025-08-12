package com.fit2081.kenzierivanwiguna_34726896_a1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit2081.kenzierivanwiguna_34726896_a1.data.AuthManager
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme
import com.fit2081.kenzierivanwiguna_34726896_a1.data.Fruit.Fruit
import com.fit2081.kenzierivanwiguna_34726896_a1.data.Fruit.FruitRepository
import kotlinx.coroutines.launch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.fit2081.kenzierivanwiguna_34726896_a1.data.GenAI.GenAIViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.data.GenAI.UiState
import com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake.FoodIntake
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.Patient
import com.fit2081.kenzierivanwiguna_34726896_a1.data.patients.PatientViewModel
import kotlinx.coroutines.runBlocking
import com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriCoachTips.NutriCoachTip
import com.fit2081.kenzierivanwiguna_34726896_a1.data.NutriCoachTips.NutriCoachTipViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake.FoodIntakeViewModel
import java.util.Date

class NutriCoachScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val currentScreen = "NutriCoach"
            val patientViewModel: PatientViewModel = ViewModelProvider(
                this, PatientViewModel.PatientViewModelFactory(this@NutriCoachScreen)
            )[PatientViewModel::class.java]

            val foodIntakeViewModel: FoodIntakeViewModel = ViewModelProvider(
                this, FoodIntakeViewModel.FoodIntakeViewModelFactory(this@NutriCoachScreen)
            )[FoodIntakeViewModel::class.java]

            val nutriCoachTipViewModel: NutriCoachTipViewModel = ViewModelProvider(
                this, NutriCoachTipViewModel.NutriCoachTipModelFactory(this@NutriCoachScreen)
            )[NutriCoachTipViewModel::class.java]

            KenzieRivanWiguna_34726896_A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(currentScreen) }) { innerPadding ->
                    NutriCoachScreen(
                        modifier = Modifier.padding(innerPadding),
                        patientViewModel,
                        foodIntakeViewModel,
                        nutriCoachTipViewModel)
                }
            }
        }
    }
}

@Composable
fun NutriCoachScreen(
    modifier: Modifier = Modifier,
    patientViewModel: PatientViewModel,
    foodIntakeViewModel: FoodIntakeViewModel,
    nutriCoachTipViewModel: NutriCoachTipViewModel
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(16.dp,36.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NutriCoach",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        //Fruit details retrieval
        var fruitName by remember { mutableStateOf("") }
        var repo: FruitRepository = FruitRepository()
        val coroutineScope = rememberCoroutineScope()
        var fruit by remember { mutableStateOf<Fruit?>(null) }

        val currentId = AuthManager.getStudentId().toString()
        var currentPatient: Patient
        runBlocking {
            currentPatient = patientViewModel.getPatientById(currentId)
        }
        val fruitScore by remember { mutableStateOf(currentPatient.fruitHeifaScore) }
        val fruitServeSize by remember { mutableStateOf(currentPatient.fruitServeSize)}
        val fruitVariationScore by remember { mutableStateOf(currentPatient.fruitVariationScore) }
        var patientName by remember { mutableStateOf(currentPatient.userName)}

        //Check whether fruit intake is optimal(fruitScore >= 5, fruitservesize >= 1, fruitvariationscore >= 1)
        if (fruitScore < 5 && fruitServeSize < 1 && fruitVariationScore < 1) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = fruitName,
                    onValueChange = { fruitName = it },
                    label = {
                        Text(
                            text = "Fruit Name",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    },
                    placeholder = { Text("Apple") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(8.dp).weight(2f),
                    singleLine = true,
                    shape = RoundedCornerShape(30)
                )
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                fruit = repo.getFruit(fruitName.lowercase())
                            } catch (e: Exception) {
                            fruit = null
                            Toast.makeText(context, "Fruit not found!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f).padding(8.dp).height(55.dp),
                    shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F32D5)),
                ) {
                    Text(
                        text = "Search \uD83D\uDD0D",
                        fontSize = 12.sp
                    )
                }
            }
            val fruitFamily = fruit?.family
            val fruitCalories = fruit?.nutritions?.calories.toString()
            val fruitFat = fruit?.nutritions?.fat.toString()
            val fruitSugar = fruit?.nutritions?.sugar.toString()
            val fruitCarbo = fruit?.nutritions?.calories.toString()
            val fruitProtein = fruit?.nutritions?.protein.toString()



            if (fruitFamily != null) {
                fruitDetail("Family", fruitFamily)
                fruitDetail("Calories", fruitCalories)
                fruitDetail("Fat", fruitFat)
                fruitDetail("Sugar", fruitSugar)
                fruitDetail("Carbo", fruitCarbo)
                fruitDetail("Protein", fruitProtein)
            }
            Spacer(modifier = Modifier.height(16.dp))

        } else {
            val randomNumber = remember { (0..100).random() }
            AsyncImage(
                model = "https://picsum.photos/id/$randomNumber/400/200",
                contentDescription = "Random Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            )
        }
        Divider(
            color = Color(0xFFF3F3F3),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )

        //AI-generated motivational tips
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

        var currentFoodIntake: FoodIntake
        runBlocking {
            currentFoodIntake = foodIntakeViewModel.getFoodIntakeById(currentId)
        }
        var eatFruits = remember { mutableStateOf(currentFoodIntake.eatFruits) }
        var eatVegetables = remember { mutableStateOf(currentFoodIntake.eatVegetables) }
        var eatGrains = remember { mutableStateOf(currentFoodIntake.eatGrains) }
        var eatMeats = remember { mutableStateOf(currentFoodIntake.eatMeats) }
        var eatSeafood = remember { mutableStateOf(currentFoodIntake.eatSeafood) }
        var eatPoultry = remember { mutableStateOf(currentFoodIntake.eatPoultry) }
        var eatFish = remember { mutableStateOf(currentFoodIntake.eatFish) }
        var eatEggs = remember { mutableStateOf(currentFoodIntake.eatEggs) }
        var eatNuts = remember { mutableStateOf(currentFoodIntake.eatNuts) }

        var selectedPersona = remember { mutableStateOf(currentFoodIntake.persona)}

        val time1 = remember { mutableStateOf(currentFoodIntake.mealTime) }
        val time2 = remember { mutableStateOf(currentFoodIntake.sleepTime) }
        val time3 = remember { mutableStateOf(currentFoodIntake.wakeTime) }


        val genAIViewModel: GenAIViewModel = viewModel()
        val predefinedPrompt =
                "Patient name is : $patientName" + "Here is the list of the patient's food score:" +
                "- vegetables: $vegetables/10" +
                "- fruits: $fruits/10" +
                "- grains: $grains/5" +
                "- whole grains: $wholeGrains/5" +
                "- meat: $meat/10" +
                "- dairy: $dairy/10" +
                "- water: $water/5" +
                "- unsaturated fat: $unsatFat/5" +
                "- saturated fat: $satFat/5" +
                "- sodium: $sodium/10" +
                "- sugar: $sugar/10" +
                "- alcohol: $alcohol/5" +
                "- discretionary food: $discFood/10" +
                "Here is the list of patient's habits" +
                        "eat fruits: $eatFruits" +
                        "eat vegetables: $eatVegetables" +
                        "eat grains: $eatGrains" +
                        "eat meat: $eatMeats" +
                        "eat seafood: $eatSeafood" +
                        "eat poultry: $eatPoultry" +
                        "eat fish: $eatFish" +
                        "eat eggs: $eatEggs" +
                        "eat nuts/seeds: $eatNuts" +
                        "persona: $selectedPersona" +
                        "time eat biggest meal: $time1" +
                        "sleep time: $time2" +
                        "wakeing time: $time3" +
                        "From the the data I've given you, Generate a short encouraging message up to 70 words to help someone improve their food score. Make it personalized and do not repeat similar messages. add some emojis."

        var result by rememberSaveable { mutableStateOf("") }
        val uiState by genAIViewModel.uiState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            if (uiState is UiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                var textColor = MaterialTheme.colorScheme.onSurface

                if (uiState is UiState.Error) {
                    textColor = MaterialTheme.colorScheme.error
                    result = (uiState as UiState.Error).errorMessage
                }
                else if (uiState is UiState.Success) {
                    textColor = MaterialTheme.colorScheme.onSurface
                    result = (uiState as UiState.Success).outputText
                    LaunchedEffect(result) {
                        nutriCoachTipViewModel.insert(
                            NutriCoachTip(
                                userId = currentId,
                                content = result,
                                time = System.currentTimeMillis()
                            )
                        )
                    }
                }

                Text(
                    text = result,
                    color = textColor
                )
            }
            Button(
                onClick = {
                    genAIViewModel.sendPrompt(predefinedPrompt)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
            ) {
                Text(text = "Fun Food Tip (AI)")
            }
        }

        //Show all tips button
        var showDialog by remember { mutableStateOf(false) }
        val tipsList by nutriCoachTipViewModel.getTips(currentId).collectAsState(initial = emptyList())

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { showDialog = true },
                shape = RoundedCornerShape(30),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                modifier = Modifier.padding(8.dp, 4.dp),
                contentPadding = PaddingValues(14.dp,2.dp)
            ) {
                Text(
                    text = "Show All Tips",
                    fontSize = 11.sp
                )
            }
            if(showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    text = {
                        LazyColumn {
                            items(tipsList) { tip: NutriCoachTip ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(text = tip.content)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Time: ${Date(tip.time)}"
                                        )
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = { showDialog = false },
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F32D5))
                            ) {
                                Text("Dismiss")
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun fruitDetail(name: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(30)),
        shape = RoundedCornerShape(30),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = " $name",
                modifier = Modifier.weight(3f)
            )
            Text(
                text = ":  $value",
                modifier = Modifier.weight(4f)
            )
        }
    }
}


