package com.edric.soal_2_quiz.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edric.soal_2_quiz.viewmodel.ControllerViewModel
import com.edric.soal_2_quiz.viewmodel.PlayViewModel

@Composable
fun playView(
    playViewModel: PlayViewModel = viewModel(),
    controllerViewModel: ControllerViewModel = viewModel(),
    importedQuestionAmount: Int
) {
    val currentQuestionIndex by playViewModel.currentQuestionIndex.collectAsState()
    val currentQuestionText by playViewModel.currentQuestionText.collectAsState()
    val currentOptions by playViewModel.currentOptions.collectAsState()
    val selectedAnswer by playViewModel.selectedAnswer.collectAsState()

    // Initialize the quiz safely once when the screen loads
    LaunchedEffect(importedQuestionAmount) {
        playViewModel.initializeQuiz(importedQuestionAmount)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Question ${currentQuestionIndex + 1}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = currentQuestionText,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Render multiple choice options
        currentOptions.forEach { option ->
            val isSelected = option == selectedAnswer
            Button(
                onClick = { playViewModel.selectAnswer(option) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color(0xFF3F51B5) else Color(0xFFE8EAF6),
                    contentColor = if (isSelected) Color.White else Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(option)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Next Button
        Button(
            onClick = {
                playViewModel.submitAnswer {
                    controllerViewModel.finishQuiz()
                }
            },
            enabled = selectedAnswer != null,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3F51B5),
                disabledContainerColor = Color(0xFFEEEEEE),
                disabledContentColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}