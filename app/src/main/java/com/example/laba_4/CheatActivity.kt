package com.example.laba_4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val EXTRA_ANSWER_IS_TRUE ="com.example.laba_4.answers_is_true"
private const val REQUEST_CODE_CHEAT = 50
private const val EXTRA_CHEATS_LEFT = "com.example.laba_4.cheats_left"

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue = false
    private lateinit var answerTextView:TextView
    private lateinit var showAnswerButton:Button
    private var cheatsLeft: Int = 0
    private lateinit var versionAPI:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cheat)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        answerIsTrue =  intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton =  findViewById(R.id.show_answer_button)
        cheatsLeft = intent.getIntExtra(EXTRA_CHEATS_LEFT, 3)
        versionAPI = findViewById(R.id.version_API)
        versionAPI.text = getString(R.string.API, android.os.Build.VERSION.SDK_INT)
        showAnswerButton.setOnClickListener{
            var answerText = when{
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }

            if (cheatsLeft == 0)
            {
                answerText  = R.string.cheat_end
            }else
            {
                Toast.makeText(this, getString(R.string.cheats_left) + (cheatsLeft-1).toString(), Toast.LENGTH_SHORT).show()
            }
            answerTextView.setText(answerText)
            setResult(REQUEST_CODE_CHEAT, intent)
        }
    }

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, cheatsLeft: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_CHEATS_LEFT, cheatsLeft)
            }
        }
    }
}