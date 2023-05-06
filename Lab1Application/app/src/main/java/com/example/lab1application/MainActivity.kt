package com.example.lab1application

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private val positiveButtonClick = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext, android.R.string.ok, Toast.LENGTH_SHORT).show()
    }

    private val negativeButtonClick = { _: DialogInterface, _: Int ->
        Toast.makeText(applicationContext, android.R.string.cancel, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun basicAlert(view: View) {
        val builder = AlertDialog.Builder(this)

        with(builder) {
            setTitle("My Alert")
            setMessage("My Message")
            setPositiveButton(android.R.string.ok, positiveButtonClick)
            setNegativeButton(android.R.string.cancel, negativeButtonClick)
            show()
        }
    }
}