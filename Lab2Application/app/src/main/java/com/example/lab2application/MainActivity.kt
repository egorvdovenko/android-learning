package com.example.lab2application

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

enum class SHAPES {
    ROCK, PAPER, SCISSORS
}

class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var scoreTextView: TextView

    private lateinit var rockButton: Button
    private lateinit var scissorsButton: Button
    private lateinit var paperButton: Button

    private var userScore = 0
    private var computerScore = 0
    private var maxScore = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.result_text_view)
        scoreTextView = findViewById(R.id.score_text_view)

        rockButton = findViewById(R.id.rock_button)
        scissorsButton = findViewById(R.id.scissors_button)
        paperButton = findViewById(R.id.paper_button)

        rockButton.setOnClickListener { play(SHAPES.ROCK) }
        scissorsButton.setOnClickListener { play(SHAPES.SCISSORS) }
        paperButton.setOnClickListener { play(SHAPES.PAPER) }
    }

    private fun play(userChoice: SHAPES) {
        val computerChoice = SHAPES.values().random()

        when (userChoice) {
            SHAPES.ROCK -> {
                when (computerChoice) {
                    SHAPES.ROCK -> draw()
                    SHAPES.SCISSORS -> win()
                    SHAPES.PAPER -> lose()
                }
            }

            SHAPES.PAPER -> {
                when (computerChoice) {
                    SHAPES.PAPER -> draw()
                    SHAPES.ROCK -> win()
                    SHAPES.SCISSORS -> lose()
                }
            }

            SHAPES.SCISSORS -> {
                when (computerChoice) {
                    SHAPES.SCISSORS -> draw()
                    SHAPES.PAPER -> win()
                    SHAPES.ROCK -> lose()
                }
            }
        }
    }

    private fun win() {
        userScore++

        if (userScore == maxScore) {
            endGame("Вы победили!")
        } else {
            showResult("Вы победили!")
        }
    }

    private fun lose() {
        computerScore++

        if (computerScore == maxScore) {
            endGame("Компьютер победил!")
        } else {
            showResult("Компьютер победил!")
        }
    }

    private fun draw() {
        showResult("Ничья!")
    }

    private fun showResult(result: String) {
        val resultTextView = findViewById<TextView>(R.id.result_text_view)
        resultTextView.text = result

        val scoreTextView = findViewById<TextView>(R.id.score_text_view)
        scoreTextView.text = "Счет: $userScore:$computerScore"
    }

    private fun endGame(result: String) {
        showResult(result)

        val restartButton = Button(this)
        restartButton.text = "Новая игра"
        restartButton.setOnClickListener {
            userScore = 0
            computerScore = 0
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Игра окончена")
            .setMessage(result)
            .setCancelable(false)
            .setView(restartButton)
            .show()
    }
}
