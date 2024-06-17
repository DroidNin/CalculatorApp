
package com.example.calculatorwithhistory

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput = ""
    private var operand1 = 0.0
    private var operand2 = 0.0
    private var operator = ""
    private var memory: Double? = null
    private val history = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttonIds = listOf(
            R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four,
            R.id.five, R.id.six, R.id.seven, R.id.eight, R.id.nine,
            R.id.add, R.id.sub, R.id.mul, R.id.divide,
            R.id.equals, R.id.allclear, R.id.memsave, R.id.memrecall, R.id.buttonHistory
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onButtonClick(id) }
        }
    }

    private fun onButtonClick(id: Int) {
        when (id) {
            R.id.zero -> appendToCurrentInput("0")
            R.id.one -> appendToCurrentInput("1")
            R.id.two -> appendToCurrentInput("2")
            R.id.three -> appendToCurrentInput("3")
            R.id.four -> appendToCurrentInput("4")
            R.id.five -> appendToCurrentInput("5")
            R.id.six -> appendToCurrentInput("6")
            R.id.seven -> appendToCurrentInput("7")
            R.id.eight -> appendToCurrentInput("8")
            R.id.nine -> appendToCurrentInput("9")
            R.id.add -> setOperator("+")
            R.id.sub -> setOperator("-")
            R.id.mul -> setOperator("*")
            R.id.divide -> setOperator("/")
            R.id.equals -> calculateResult()
            R.id.allclear -> clearInput()
            R.id.memsave -> memorySave()
            R.id.memrecall -> memoryRecall()
            R.id.buttonHistory -> showHistory()
        }
    }

    private fun appendToCurrentInput(value: String) {
        if (currentInput == "0" && value != ".") {
            currentInput = value
        } else {
            currentInput += value
        }
        display.text = currentInput
    }

    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toDouble()
            operator = op
            currentInput = ""
            display.text = "0"
        }
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            operand2 = currentInput.toDouble()
            val result = when (operator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> operand1 / operand2
                else -> 0.0
            }
            display.text = result.toString()
            history.add("$operand1 $operator $operand2 = $result")
            currentInput = result.toString()
            operator = ""
        }
    }

    private fun clearInput() {
        currentInput = ""
        operand1 = 0.0
        operand2 = 0.0
        operator = ""
        display.text = "0"
    }

    private fun memorySave() {
        if (currentInput.isNotEmpty()) {
            memory = currentInput.toDouble()
        }
    }

    private fun memoryRecall() {
        memory?.let {
            currentInput = it.toString()
            display.text = currentInput
        }
    }

    private fun showHistory() {
        val historyDialog = AlertDialog.Builder(this)
        historyDialog.setTitle("Calculation History")
        historyDialog.setItems(
            history.toTypedArray()
        ) { _, _ -> }
        historyDialog.setPositiveButton("OK", null)
        historyDialog.show()
    }
}
