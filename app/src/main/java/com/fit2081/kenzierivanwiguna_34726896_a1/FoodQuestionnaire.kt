package com.fit2081.kenzierivanwiguna_34726896_a1

import androidx.compose.material3.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.fit2081.kenzierivanwiguna_34726896_a1.data.AuthManager
import com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake.FoodIntakeViewModel
import com.fit2081.kenzierivanwiguna_34726896_a1.ui.theme.KenzieRivanWiguna_34726896_A1Theme
import com.fit2081.kenzierivanwiguna_34726896_a1.data.foodIntake.FoodIntake
import kotlinx.coroutines.runBlocking

class FoodQuestionnaire1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val foodIntakeViewModel: FoodIntakeViewModel = ViewModelProvider(
                this, FoodIntakeViewModel.FoodIntakeViewModelFactory(this@FoodQuestionnaire1)
            )[FoodIntakeViewModel::class.java]

            KenzieRivanWiguna_34726896_A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuestionnaireScreen(modifier = Modifier.padding(innerPadding), foodIntakeViewModel)
                }
            }
        }
    }
}



@Composable
fun QuestionnaireScreen(modifier: Modifier = Modifier, foodIntakeViewModel: FoodIntakeViewModel) {
    val context = LocalContext.current
    //Header
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        val intent = Intent(context, LoginScreen::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Food Intake Questionnaire",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Divider(
                color = Color(0xFFF3F3F3),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        //Food categories
        val currentId = AuthManager.getStudentId().toString()
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
        Column (
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = "Tick all the food categories you can eat",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    FoodCheckbox("Fruits", eatFruits)
                    FoodCheckbox("Red Meat", eatMeats)
                    FoodCheckbox("Fish", eatFish)
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    FoodCheckbox("Vegetables", eatVegetables)
                    FoodCheckbox("Seafood", eatSeafood)
                    FoodCheckbox("Eggs", eatEggs)
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    FoodCheckbox("Grains", eatGrains)
                    FoodCheckbox("Poultry", eatPoultry)
                    FoodCheckbox("Nuts/Seeds", eatNuts)
                }
            }
        }


        //Persona selection
        var selectedPersona = remember { mutableStateOf(currentFoodIntake.persona)}
        var expanded by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy((-5).dp)
        ) {
            Text(
                text = "Your Persona",
                modifier = Modifier.padding(12.dp, 4.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "People can be broadly classified into 6 different types based on\n" +
                        "their eating preferences. Click on each button below to find out\n" +
                        "the different types, and select the type that best fit you!",
                modifier = Modifier.padding(12.dp, 0.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp,0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ShowPersonaAndModal(
                    name = "Health Devotee",
                    description = "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.",
                    image = R.drawable.persona_1
                )

                ShowPersonaAndModal(
                    name = "Mindful Eater",
                    description = "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.",
                    image = R.drawable.persona_2
                )

                ShowPersonaAndModal(
                    name = "Wellness Striver",
                    description = "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.",
                    image = R.drawable.persona_3
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp,0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ShowPersonaAndModal(
                    name = "Balance Seeker",
                    description = "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.",
                    image = R.drawable.persona_4
                )

                ShowPersonaAndModal(
                    name = "Health Procrastinator",
                    description = "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.",
                    image = R.drawable.persona_5
                )

                ShowPersonaAndModal(
                    name = "Food Carefree",
                    description = "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat.",
                    image = R.drawable.persona_6
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                color = Color(0xFFF3F3F3),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Which persona best fits you?",
                modifier = Modifier.padding(12.dp,8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            val personas = mutableListOf<String>(
                "Health Devotee",
                "Mindful Eater",
                "Wellness Striver",
                "Balance Seeker",
                "Health Procrastinator",
                "Food Carefree"
            )

            OutlinedTextField(
                value = selectedPersona.value,
                onValueChange = {},
                label = {
                    Text(
                        text = "Select option",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = RoundedCornerShape(30),
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                personas.forEach { persona ->
                    DropdownMenuItem(
                        text = { Text(persona) },
                        onClick = {
                            selectedPersona.value = persona
                            expanded = false
                        }
                    )
                }
            }
        }

        //Time picker
        val time1 = remember { mutableStateOf(currentFoodIntake.mealTime) }
        val time2 = remember { mutableStateOf(currentFoodIntake.sleepTime) }
        val time3 = remember { mutableStateOf(currentFoodIntake.wakeTime) }

        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp, 4.dp),
        ) {
            Text(
                text = "Timings",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "What time of day approx. do you\n" +
                            "normally eat your biggest meal?",
                    fontSize = 14.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.Medium
                )

                OutlinedTextField(
                    value = time1.value,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.width(140.dp).height(50.dp),
                    placeholder = { Text("00:00") },
                    trailingIcon = {
                        IconButton(onClick = {
                            showTimePicker(context, time1)
                        }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Select Time")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "What time of day approx. do you\n" +
                            "go to sleep at night?",
                    fontSize = 14.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.Medium
                )

                OutlinedTextField(
                    value = time2.value,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.width(140.dp).height(50.dp),
                    placeholder = { Text("00:00") },
                    trailingIcon = {
                        IconButton(onClick = {
                            showTimePicker(context, time2)
                        }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Select Time")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "What time of day approx. do you\n" +
                            "wake up in the morning?",
                    fontSize = 14.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.Medium
                )

                OutlinedTextField(
                    value = time3.value,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.width(140.dp).height(50.dp),
                    placeholder = { Text("00:00") },
                    trailingIcon = {
                        IconButton(onClick = {
                            showTimePicker(context, time3)
                        }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Select Time")
                        }
                    }
                )
            }
        }

        //Save Button
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                color = Color(0xFFF3F3F3),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    val currentId = AuthManager.getStudentId()
                    foodIntakeViewModel.update(
                        FoodIntake(
                            userId = currentId.toString(),
                            eatFruits = eatFruits.value,
                            eatVegetables = eatVegetables.value,
                            eatGrains = eatGrains.value,
                            eatMeats = eatMeats.value,
                            eatSeafood = eatSeafood.value,
                            eatPoultry = eatPoultry.value,
                            eatFish = eatFish.value,
                            eatEggs = eatEggs.value,
                            eatNuts = eatNuts.value,
                            persona = selectedPersona.value,
                            mealTime = time1.value,
                            sleepTime = time2.value,
                            wakeTime = time3.value
                        )
                    )
                    val intent = Intent(context, HomeScreen::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.height(40.dp).width(150.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F32D5)),
                shape = RoundedCornerShape(25)
            ) {
                Text(
                    text = "Save",
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun FoodCheckbox(name: String, checkboxState: MutableState<Boolean>): Boolean {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.padding(2.dp).size(36.dp),
            checked = checkboxState.value,
            onCheckedChange = { checkboxState.value = it },
            colors = CheckboxDefaults.colors(Color(0xFF7F32D5))
        )
        Text(
            text = name,
            fontSize = 14.sp
        )
    }
    return checkboxState.value
}


@Composable
fun ShowPersonaAndModal(name: String, description: String, image: Int) {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { showDialog = true },
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F32D5)),
        modifier = Modifier.padding(8.dp, 4.dp),
        contentPadding = PaddingValues(14.dp,2.dp)
    ) {
        Text(
            text = name,
            fontSize = 11.sp
        )
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = "Persona Image",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(8.dp)
                    )

                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = description,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 12.sp,
                        lineHeight = 20.sp
                    )
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

fun showTimePicker(context: Context, time: MutableState<String>) {
    val calendar = Calendar.getInstance()
    val mHour = calendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            time.value = formattedTime
        },
        mHour, mMinute, true
    ).show()
}




