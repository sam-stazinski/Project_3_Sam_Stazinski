package com.example.project3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project3.R.layout.question_screen
import java.util.Random

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val difficulty = intent.getStringExtra("difficulty")
        val operation = intent.getCharExtra("operation", '+')
        val correctAnswers = intent.getIntExtra("correctAnswers", 0)
        val questionsAnswered = intent.getIntExtra("questionsAnswered", 0)

        findViewById<TextView>(R.id.difficulty).text = "Difficulty: $difficulty"
        findViewById<TextView>(R.id.operation).text = "Operation: $operation"
        findViewById<TextView>(R.id.result).text = "$correctAnswers/$questionsAnswered"

        findViewById<Button>(R.id.retryButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
