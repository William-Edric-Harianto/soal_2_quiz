package com.edric.soal_2_quiz.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edric.soal_2_quiz.model.Page
import com.edric.soal_2_quiz.viewmodel.ControllerViewModel

@Composable
fun controllerView(
    viewModel: ControllerViewModel = viewModel()
) {
    val page by viewModel.page.collectAsState()
    val questionAmount by viewModel.questionAmount.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            when (page) {
                Page.START -> startView()
                Page.PLAY -> playView()
                Page.FINISH -> finishView()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun controllerPreview() {
    controllerView()
}