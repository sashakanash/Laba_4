package com.example.laba_4

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private  const val KEY_INDEX = "index"
private  const val ANSWER_INDEX = "answers"
private const val BUTTON_STATE = "buttons"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private  lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView
    private val quizViewModel : quizViewModel by lazy { ViewModelProvider(this)[quizViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentIndex =  savedInstanceState?.getInt(KEY_INDEX) ?:0
        val buttonState = savedInstanceState?.getBoolean(BUTTON_STATE) ?: false
        quizViewModel.currentIndex = currentIndex
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener{_: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener{_: View ->
            checkAnswer( false)
        }

        nextButton.setOnClickListener {_: View ->
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener{
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent =  CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivity(intent)
        }
        updateQuestion()
        if (buttonState)
        {
            trueButton.visibility = View.INVISIBLE
            falseButton.visibility = View.INVISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "Saved instance")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        outState.putInt(ANSWER_INDEX, quizViewModel.numberOfCorrectAnswers)
        outState.putBoolean(BUTTON_STATE, trueButton.isInvisible)
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        var numberOfCorrectAnswers = quizViewModel.numberOfCorrectAnswers
        val messageResId: Int
        if(userAnswer == correctAnswer){
            messageResId = R.string.correct_toast
            numberOfCorrectAnswers += 1
        }else
        {
            messageResId = R.string.incorrect_toast
            numberOfCorrectAnswers += 0
        }
        quizViewModel.numberOfCorrectAnswers = numberOfCorrectAnswers
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
        if(quizViewModel.currentIndex == quizViewModel.questionBank.size - 1 ) {
            nextButton.visibility = View.GONE
            endDialog()
        }
    }
    private fun endDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Количество правильных ответов = ${quizViewModel.numberOfCorrectAnswers}")
        builder.setTitle("Результат")

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}