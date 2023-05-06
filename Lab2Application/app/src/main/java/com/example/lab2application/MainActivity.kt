package com.example.lab2application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

private lateinit var rock_button: Button
private lateinit var scissors_button: Button
private lateinit var paper_button: Button

class MainActivity : AppCompatActivity() {

    private var userScore = 0
    private var computerScore = 0
    private var maxScore = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rock_button = findViewById(R.id.rock_button)
        scissors_button = findViewById(R.id.scissors_button)
        paper_button = findViewById(R.id.paper_button)

        rock_button.setOnClickListener { play(0) }
        scissors_button.setOnClickListener { play(1) }
        paper_button.setOnClickListener { play(2) }
    }

    private fun play(userChoice: Int) {
        val computerChoice = (0..2).random()

        when (userChoice) {
            0 -> {
                when (computerChoice) {
                    0 -> draw()
                    1 -> win()
                    2 -> lose()
                }
            }

            1 -> {
                when (computerChoice) {
                    0 -> lose()
                    1 -> draw()
                    2 -> win()
                }
            }

            2 -> {
                when (computerChoice) {
                    0 -> win()
                    1 -> lose()
                    2 -> draw()
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
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }

    private fun endGame(result: String) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        userScore = 0
        computerScore = 0
    }
}
