package com.example.project3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project3.R.layout.question_screen
import java.util.Random

class MathQuestionActivity : AppCompatActivity() {

    private lateinit var difficulty: String
    private var operation: Char = '+'
    private var numOfQuestions = 0
    private var questionsAnswered = 0
    private var correctAnswers = 0
    private var currentAnswer = 0
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_screen)

        // Retrieve the extras from the intent
        difficulty = intent.getStringExtra("difficulty") ?: "easy" // default to 'easy' if not found
        operation = intent.getCharExtra("operation", '+') // default to '+' if not found
        numOfQuestions = intent.getIntExtra("numOfQuestions", 10) // default to 10 if not found
        questionsAnswered = intent.getIntExtra("questionsAnswered", 0) // default to 0 if not found
        correctAnswers = intent.getIntExtra("correctAnswers", 0) // default to 0 if not found

        Log.d("MathApp", "Activity started. Operation: $operation, Difficulty: $difficulty, Total Questions: $numOfQuestions")

        generateQuestion()
    }

    @SuppressLint("StringFormatMatches")
    private fun generateQuestion() {
        if (questionsAnswered < numOfQuestions) {
            val operandLimit = when (difficulty) {
                "easy" -> 10
                "medium" -> 25
                "hard" -> 50
                else -> 10
            }

            val operand1 = random.nextInt(operandLimit)
            val operand2 = random.nextInt(operandLimit)

            currentAnswer = when (operation) {
                '+' -> operand1 + operand2
                '-' -> operand1 - operand2
                '*' -> operand1 * operand2
                '/' -> operand1 / operand2
                else -> 0 // Handle invalid operation if any
            }

            //TODO
            val mathProblem = getString(R.string.math_problem_text, operand1, operation, operand2)
            val mathProblemTextView: TextView = findViewById(R.id.mathProblem)
            mathProblemTextView.text = mathProblem

            Log.d("MathApp", "Generated question: $mathProblem. Correct Answer: $currentAnswer")

        } else {
            Log.d("MathApp", "All questions answered. Correct Answers: $correctAnswers out of $numOfQuestions")
            // Show results or navigate to a result screen

        }
    }

    fun onDoneButtonClicked(view: View) {
        val userAnswerEditText: EditText = findViewById(R.id.userAnswer)
        val userAnswer = userAnswerEditText.text.toString().toIntOrNull()

        if (userAnswer == currentAnswer) {
            correctAnswers++
            Log.d("MathApp", "User's answer is correct!")
        } else {
            Log.d("MathApp", "User's answer is incorrect. User's Answer: $userAnswer, Correct Answer: $currentAnswer")
        }

        questionsAnswered++
        generateQuestion()
    }
}
