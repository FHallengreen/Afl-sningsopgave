package com.example.quizapplication.ui.completedquiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quizapplication.ui.navigation.QuizScreenRoute

/**
 * Screen for displaying the results of the last quiz
 * @param navController Navigation controller for navigating to other screens
 * @param viewModel ViewModel for the screen
 */
@Composable
fun CompletedQuizScreen(
    navController: NavHostController,
    viewModel: CompletedQuizViewModel = viewModel(factory = CompletedQuizViewModel.Factory)
) {

    val lastQuizResult = viewModel.lastQuizResult.value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        if (lastQuizResult != null) {
            Text(text = "Quiz Completed", style = MaterialTheme.typography.displaySmall.copy
                (fontWeight = FontWeight.Bold))

            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text =
                        "Questions Answered: ${lastQuizResult.totalQuestions}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text =
                "Correct Answers: ${lastQuizResult.correctAnswers}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Green,
                textAlign = TextAlign.Center,
            )
        } else {
            Text(text = "No quiz results available")
        }

        Column {
            Button(
                onClick = { navController.navigate(QuizScreenRoute.CreateGame.routeName) },
                modifier = Modifier
                    .padding(50.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Start new Quiz")
            }
        }
    }
}
