package com.edric.soal_2_quiz.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edric.soal_2_quiz.viewmodel.ControllerViewModel

@Composable
fun startView(
    viewModel: ControllerViewModel = viewModel()
) {
    val inputText by viewModel.inputText.collectAsState()
    val isError by viewModel.isError.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Text(
            text = "Mini Quiz",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "How many questions do you want? (1 - 5)",
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = inputText,
            onValueChange = { viewModel.updateInputText(it) },
            label = { Text("Number of questions") },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            supportingText = {
                if (isError) {
                    Text("Maximum is 5 questions")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.startQuiz() },
            enabled = inputText.isNotBlank() && !isError,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4C5B84),
                disabledContainerColor = Color(0xFFEEEEEE),
                disabledContentColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Quiz")
        }
    }
}