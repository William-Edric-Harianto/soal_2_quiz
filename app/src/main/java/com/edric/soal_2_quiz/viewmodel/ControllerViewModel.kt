package com.edric.soal_2_quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.edric.soal_2_quiz.model.Page
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ControllerViewModel : ViewModel() {
    private val _page = MutableStateFlow(Page.START)
    val page = _page.asStateFlow()

    private val _questionAmount = MutableStateFlow(0)
    val questionAmount = _questionAmount.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText = _inputText.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    fun updateInputText(text: String) {
        _inputText.update { text }
        val amount = text.toIntOrNull()
        _isError.update { amount != null && amount > 5 }
    }

    fun startQuiz() {
        val amount = _inputText.value.toIntOrNull()
        if (amount != null && amount in 1..5) {
            _questionAmount.update { amount }
            _isError.update { false }
            _page.update { Page.PLAY }
        } else {
            _isError.update { true }
        }
    }

    fun restartQuiz() {
        _inputText.update { "" }
        _questionAmount.update { 0 }
        _isError.update { false }
        _page.update { Page.START }
    }

    fun finishQuiz() {
        _page.update { Page.FINISH }
    }
}