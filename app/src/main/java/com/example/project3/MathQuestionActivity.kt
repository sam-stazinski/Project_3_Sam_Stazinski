package com.example.project3

import android.annotation.SuppressLint
import android.content.Intent
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


            Log.d("feedback", generateFeedback(correctAnswers, numOfQuestions, operation))
            Log.d("color", determineColor(correctAnswers, numOfQuestions))

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("feedback", generateFeedback(correctAnswers, numOfQuestions, operation))
            intent.putExtra("feedbackColor", determineColor(correctAnswers, numOfQuestions))
            startActivity(intent)

            /*
            Log.d("MathApp", "All questions answered. Correct Answers: $correctAnswers out of $numOfQuestions")
            // Show results or navigate to a result screen
            val intent = Intent(this, ResultsActivity::class.java)
            intent.putExtra("difficulty", difficulty) // 'selectedDifficulty' is the difficulty chosen by the user
            intent.putExtra("operation", operation) // 'selectedOperation' is the operation chosen by the user
            intent.putExtra("numOfQuestions", numOfQuestions)
            intent.putExtra("questionsAnswered", questionsAnswered)
            intent.putExtra("correctAnswers", correctAnswers)
            startActivity(intent)
            */

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

    fun generateFeedback(correct: Int, total: Int, operation: Char): String {
        val operationName = when(operation) {
            '+' -> "addition"
            '-' -> "subtraction"
            '*' -> "multiplication"
            '/' -> "division"
            else -> throw IllegalArgumentException("Invalid operation: $operation")
        }

        val percentage = (correct.toDouble() / total) * 100

        return when {
            percentage == 0.0 -> "You got $correct out of $total correct in $operationName. Did your cat walk on your keyboard or something?"
            percentage <= 10.0 -> "You got $correct out of $total right in $operationName. Did you use a magic 8-ball for answers?"
            percentage <= 20.0 -> "$correct out of $total correct in $operationName? Not bad! Well, I mean... sort of bad. But there's hope!"
            percentage <= 30.0 -> "You got $correct out of $total right in $operationName. Were you guessing by the rhythm of your favorite song? Keep jamming and practicing!"
            percentage <= 40.0 -> "You nailed $correct out of $total in $operationName. Keep going, and soon you might not even need your fingers to count!"
            percentage <= 50.0 -> "$correct out of $total correct in $operationName! Flipping a coin for answers, were we?"
            percentage <= 60.0 -> "You got $correct out of $total right in $operationName! You're over the hump. Just don't roll back down!"
            percentage <= 70.0 -> "$correct out of $total correct in $operationName? You're on a roll! Just not a cinnamon roll, unfortunately."
            percentage <= 80.0 -> "$correct out of $total right in $operationName? You're making math look easy. Show off!"
            percentage <= 90.0 -> "Wow, $correct out of $total correct in $operationName! You're hotter than a calculator in the sun!"
            else -> "You aced it with $correct out of $total in $operationName! Are you secretly a math wizard? Because you've cast a spell on these questions!"
        }
    }

    fun determineColor(correct: Int, total: Int): String {
        return if ((correct.toDouble() / total) * 100 < 80) "#ff0000" else "#000000"
    }

    }
