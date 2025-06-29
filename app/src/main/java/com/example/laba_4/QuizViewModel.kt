package com.example.laba_4

import androidx.lifecycle.ViewModel

private const val TAG: String = "quizViewModel"

class quizViewModel : ViewModel() {

    var currentIndex = 0
    var isCheat = false
    var numberOfCorrectAnswers: Int = 0
    val questionBank = listOf(
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true)
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext (){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

}