package com.edric.soal_2_quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.edric.soal_2_quiz.model.Question
import com.edric.soal_2_quiz.model.QuestionSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayViewModel : ViewModel() {

    // Dummy data based on your mockups
    private val allQuestions = listOf(
        Question("Ibu Kota Di Indonesia?", "Jakarta", listOf("Surabaya", "Bandung", "Medan")),
        Question("Ada berapa hari dalam seminggu?", "7", listOf("5", "6", "11")),
        Question("Mata Kuliah VP pakai apa?", "Android Studio", listOf("Word", "Excel", "VS Code")),
        Question("Apakah Mata Kuliah VP susah?", "Sangat Mudah", listOf("Sangat Sulit", "Sulit", "Biasa Saja")),
        Question("Natal dirayakan pada bulan?", "Desember", listOf("Januari", "Agustus", "Oktober"))
    )

    private val _questionAmount = MutableStateFlow(0)
    val questionAmount = _questionAmount.asStateFlow()

    private val _correctAmount = MutableStateFlow(0)
    val correctAmount = _correctAmount.asStateFlow()

    private val _questionSummaries = MutableStateFlow<List<QuestionSummary>>(emptyList())
    val questionSummaries = _questionSummaries.asStateFlow()

    // Active Question States
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    private val _currentQuestionText = MutableStateFlow("")
    val currentQuestionText = _currentQuestionText.asStateFlow()

    private val _currentOptions = MutableStateFlow<List<String>>(emptyList())
    val currentOptions = _currentOptions.asStateFlow()

    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer = _selectedAnswer.asStateFlow()

    fun initializeQuiz(newAmount: Int) {
        if (_questionAmount.value == 0) {
            _questionAmount.update { newAmount }
            _correctAmount.update { 0 }
            _questionSummaries.update { emptyList() }
            _currentQuestionIndex.update { 0 }
            loadQuestion()
        }
    }

    private fun loadQuestion() {
        val index = _currentQuestionIndex.value
        if (index < _questionAmount.value && index < allQuestions.size) {
            val q = allQuestions[index]
            _currentQuestionText.value = q.question
            _currentOptions.value = (q.wrongAnswerList + q.correctAnswer).shuffled()
            _selectedAnswer.value = null
        }
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun submitAnswer(onFinish: () -> Unit) {
        val index = _currentQuestionIndex.value
        val q = allQuestions.getOrNull(index)
        val ans = _selectedAnswer.value

        if (q != null && ans != null) {
            val isCorrect = (ans == q.correctAnswer)
            if (isCorrect) {
                _correctAmount.update { it + 1 }
            }
            _questionSummaries.update {
                it + QuestionSummary(index + 1, q.question, ans, q.correctAnswer, isCorrect)
            }
        }

        if (index + 1 >= _questionAmount.value || index + 1 >= allQuestions.size) {
            onFinish()
        } else {
            _currentQuestionIndex.value += 1
            loadQuestion()
        }
    }

    fun clearQuiz() {
        _questionAmount.update { 0 }
        _correctAmount.update { 0 }
        _questionSummaries.update { emptyList() }
        _currentQuestionIndex.update { 0 }
    }
}