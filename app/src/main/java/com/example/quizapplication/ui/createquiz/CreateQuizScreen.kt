package com.example.quizapplication.ui.createquiz

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quizapplication.model.Category
import com.example.quizapplication.model.Difficulty
import com.example.quizapplication.ui.navigation.QuizScreenRoute

/**
 * Create quiz screen composable
 * Create a new game screen
 * This screen is used to select the category,
 * amount of question and difficulty level.
 * When the user clicks on the start quiz button
 * @param navController the nav controller used to navigate to the quiz screen
 */
@Composable
fun CreateGame(
    navController: NavHostController,
    viewModel: CreateQuizViewModel = viewModel(factory = CreateQuizViewModel.Factory)
) {
    var selectedCategory by remember { mutableStateOf(Category.GeneralKnowledge) }
    var selectedQuestions by remember { mutableIntStateOf(5) }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.Easy) }

    val quizApiState = viewModel.quizApiState

    LaunchedEffect(quizApiState) {
        when (quizApiState) {
            is QuizApiState.Success -> {
                navController.navigate(QuizScreenRoute.QuizScreen.routeName)
            }
            is QuizApiState.Error -> {
                Toast.makeText(
                    null, "Error creating quiz", Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Create a new quiz",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Select amount of question:",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))


            Slider(
                value = selectedQuestions.toFloat(),
                onValueChange = { selectedQuestions = it.toInt() },
                valueRange = 5f..50f,
                steps = 8,
            )
            Text("${selectedQuestions} questions")

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Select a category:",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Dropdown(items = Category.entries,
                selectedItem = selectedCategory,
                onItemSelected = { selectedCategory = it },
                labelName = { it.displayName })

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Select a difficulty:",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Dropdown(items = Difficulty.entries,
                selectedItem = selectedDifficulty,
                onItemSelected = { selectedDifficulty = it },
                labelName = { it.level })

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { navController.navigateUp() }) {
                    Text("Back")
                }

                Spacer(modifier = Modifier.width(50.dp))

                Button(onClick = {
                    viewModel.startQuiz(
                        selectedCategory, selectedQuestions, selectedDifficulty
                    )
                }) {
                    Text("Start Quiz")
                }
            }
        }
        if (quizApiState is QuizApiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .pointerInput(Unit) { detectTapGestures { } },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

/// Creates a dropdown list with a textfield and a dropdown menu.
/// The dropdown menu is displayed when the textfield is clicked.
/// It is type T in order to be able to use it for different types of data.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Dropdown(
    items: List<T>, selectedItem: T, onItemSelected: (T) -> Unit, labelName: (T) -> String
) {
    var isExpanded by remember { mutableStateOf(false) }
    val selectedItemLabel = labelName(selectedItem)

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = !isExpanded }) {
        TextField(value = selectedItemLabel,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .menuAnchor()
                .clickable { isExpanded = true })

        DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            items.forEach { item ->
                val itemLabel = labelName(item)
                DropdownMenuItem(text = { Text(text = itemLabel) }, onClick = {
                    onItemSelected(item)
                    isExpanded = false
                })
            }
        }
    }
}