package com.example.project3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    // Enums for clarity and safety
    enum class Difficulty {
        EASY, MEDIUM, HARD
    }

    enum class Operation {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
    }

    private var selectedDifficulty: Difficulty? = null
    private var selectedOperation: Operation? = null
    var numberOfQuestions: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateQuestionCountDisplay()

        val difficultyRadioGroup: RadioGroup = findViewById(R.id.difficultyRadioGroup)
        val operationRadioGroup: RadioGroup = findViewById(R.id.operationRadioGroup)

        // Listener for the difficulty radio group
        difficultyRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio.id) {
                R.id.easy -> selectedDifficulty = Difficulty.EASY
                R.id.medium -> selectedDifficulty = Difficulty.MEDIUM
                R.id.hard -> selectedDifficulty = Difficulty.HARD
            }
            Log.d("Difficulty", "$selectedDifficulty")
        }

        // Listener for the operation radio group
        operationRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            when (radio.id) {
                R.id.addition -> selectedOperation = Operation.ADDITION
                R.id.subtraction -> selectedOperation = Operation.SUBTRACTION
                R.id.multiplication -> selectedOperation = Operation.MULTIPLICATION
                R.id.division -> selectedOperation = Operation.DIVISION
            }
            Log.d("Operation", "$selectedOperation")
        }
    }

    fun onStartButtonClicked(view: View) {

        updateQuestionCountDisplay()

        // Check if difficulty is selected
        if (selectedDifficulty == null) {
            Toast.makeText(this, "Please select a difficulty level.", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if operation is selected
        if (selectedOperation == null) {
            Toast.makeText(this, "Please select an operation.", Toast.LENGTH_SHORT).show()
            return
        }

        // Check if number of questions is valid
        if (numberOfQuestions == null || numberOfQuestions <= 0) {
            Toast.makeText(this, "Please enter a valid number of questions (more than zero).", Toast.LENGTH_SHORT).show()
            return
        }

        // If all validations pass, launch the new Activity
        val intent = Intent(this, MathQuestionActivity::class.java)
        intent.putExtra("difficulty", selectedDifficulty) // 'selectedDifficulty' is the difficulty chosen by the user
        intent.putExtra("operation", selectedOperation) // 'selectedOperation' is the operation chosen by the user
        intent.putExtra("numOfQuestions", numberOfQuestions)
        intent.putExtra("questionsAnswered", 0)
        intent.putExtra("correctAnswers", 0)
        startActivity(intent)
    }

    fun increaseQuestions(view: View) {
        numberOfQuestions++
        updateQuestionCountDisplay()
    }

    fun decreaseQuestions(view: View) {
        if (numberOfQuestions > 1) { // Ensure it doesn't go below 1
            numberOfQuestions--
            updateQuestionCountDisplay()
        }
    }

    private fun updateQuestionCountDisplay() {
        val questionsTextView: TextView = findViewById(R.id.number_of_questions)
        val formattedText = getString(R.string.number_of_questions, numberOfQuestions)
        questionsTextView.text = formattedText
    }

}
