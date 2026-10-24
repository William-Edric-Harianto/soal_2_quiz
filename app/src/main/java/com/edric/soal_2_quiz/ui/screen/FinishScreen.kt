package com.edric.soal_2_quiz.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun finishView(
    playViewModel: PlayViewModel = viewModel(),
    controllerViewModel: ControllerViewModel = viewModel()
) {
    val correctAmount by playViewModel.correctAmount.collectAsState()
    val questionAmount by playViewModel.questionAmount.collectAsState()
    val questionSummaries by playViewModel.questionSummaries.collectAsState()

    // Filter to only display incorrect answers
    val wrongAnswers = questionSummaries.filter { !it.isCorrect }
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Quiz Finished!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your Score: $correctAmount / $questionAmount",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (wrongAnswers.isEmpty() && questionAmount > 0) {
            Text(
                text = "Perfect! No wrong answers.",
                color = Color(0xFF3F51B5),
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 32.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                // Using a standard for loop
                for (summary in wrongAnswers) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Q${summary.number}: ${summary.question}",
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Your Answer: ${summary.answer}",
                            color = Color(0xFFD32F2F),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Correct Answer: ${summary.correctAnswer}",
                            color = Color(0xFF3F51B5),
                            fontSize = 14.sp
                        )
                        HorizontalDivider(modifier = Modifier.padding(top = 12.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                playViewModel.clearQuiz()
                controllerViewModel.restartQuiz()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Restart Quiz")
        }
    }
}