package com.example.lab3application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var exchangeRates: Map<String, Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fromSpinner = findViewById<Spinner>(R.id.fromCurrencySpinner)
        val toSpinner = findViewById<Spinner>(R.id.toCurrencySpinner)

        val convertButton = findViewById<Button>(R.id.convertButton)
        convertButton.isEnabled = false

        // Загрузка курсов валют
        CoroutineScope(Dispatchers.Main).launch {
            try {
                exchangeRates = fetchExchangeRates()
                val currencies = exchangeRates.keys.toMutableList()

                // Добавление рубля в список валют
                currencies.add(0, "RUB")

                // Заполнение спиннеров списком валют
                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, currencies)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                fromSpinner.adapter = adapter
                toSpinner.adapter = adapter

                convertButton.isEnabled = true
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Ошибка загрузки курсов валют", Toast.LENGTH_SHORT).show()
            }
        }

        convertButton.setOnClickListener {
            val amountEditText = findViewById<EditText>(R.id.amountEditText)
            val resultTextView = findViewById<TextView>(R.id.resultTextView)

            val amount = amountEditText.text.toString().toDouble()
            val fromCurrency = fromSpinner.selectedItem.toString()
            val toCurrency = toSpinner.selectedItem.toString()

            val fromExchangeRate = exchangeRates[fromCurrency] ?: 1.0
            val toExchangeRate = exchangeRates[toCurrency] ?: 1.0

            val result = amount * fromExchangeRate / toExchangeRate
            resultTextView.text = "%.2f".format(result)
        }
    }

    // Получение актуальных курсов валют из API Центробанка России
    private suspend fun fetchExchangeRates(): Map<String, Double> = withContext(Dispatchers.IO) {
        val url = "https://www.cbr-xml-daily.ru/daily_json.js"
        val response = URL(url).readText()

        val exchangeRates = mutableMapOf<String, Double>()
        val json = JSONObject(response)

        val rates = json.getJSONObject("Valute")
        val keys = rates.keys()

        while (keys.hasNext()) {
            val key = keys.next()
            val value = rates.getJSONObject(key).getDouble("Value")
            exchangeRates[key] = value
        }

        exchangeRates
    }
}