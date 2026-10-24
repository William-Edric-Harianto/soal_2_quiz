package com.edric.soal_2_quiz.viewmodel

import androidx.lifecycle.ViewModel
import com.edric.soal_2_quiz.model.Question
import com.edric.soal_2_quiz.model.QuestionSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayViewModel : ViewModel() {

    private val allQuestions = listOf(
        Question("Ibu Kota Di Indonesia?", "Jakarta", listOf("Surabaya", "Bandung", "Medan")),
        Question("Ada berapa hari dalam seminggu?", "7", listOf("5", "6", "11")),
        Question("Mata Kuliah VP pakai apa?", "Android Studio", listOf("Word", "Excel", "VS Code")),
        Question("Apakah Mata Kuliah VP susah?", "Sangat Mudah", listOf("Sangat Sulit", "Sulit", "Biasa Saja")),
        Question("Natal dirayakan pada bulan?", "Desember", listOf("Januari", "Agustus", "Oktober"))
    )

    private var activeQuestions = emptyList<Question>()

    private val _correctAmount = MutableStateFlow(0)
    val correctAmount = _correctAmount.asStateFlow()

    private val _questionSummaries = MutableStateFlow<List<QuestionSummary>>(emptyList())
    val questionSummaries = _questionSummaries.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    private val _currentQuestionText = MutableStateFlow("")
    val currentQuestionText = _currentQuestionText.asStateFlow()

    private val _currentOptions = MutableStateFlow<List<String>>(emptyList())
    val currentOptions = _currentOptions.asStateFlow()

    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer = _selectedAnswer.asStateFlow()

    fun initializeQuiz(amount: Int) {
        if (activeQuestions.isEmpty() && amount > 0) {
            _correctAmount.update { 0 }
            _questionSummaries.update { emptyList() }
            _currentQuestionIndex.update { 0 }

            activeQuestions = allQuestions.shuffled().take(amount)

            loadQuestion()
        }
    }

    private fun loadQuestion() {
        val index = _currentQuestionIndex.value
        if (index < activeQuestions.size) {
            val q = activeQuestions[index]
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
        val q = activeQuestions.getOrNull(index)
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

        if (index + 1 >= activeQuestions.size) {
            onFinish()
        } else {
            _currentQuestionIndex.value += 1
            loadQuestion()
        }
    }

    fun clearQuiz() {
        _correctAmount.update { 0 }
        _questionSummaries.update { emptyList() }
        _currentQuestionIndex.update { 0 }
        activeQuestions = emptyList()
    }
}