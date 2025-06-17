package com.example.laba_4

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"ViewModel instance cleared")
    }
}