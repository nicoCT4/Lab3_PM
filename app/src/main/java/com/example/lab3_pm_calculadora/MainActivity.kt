package com.example.lab3_pm_calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var tv_num1: TextView
    lateinit var tv_num2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tv_num1 = findViewById(R.id.tv_num1)
        tv_num2 = findViewById(R.id.tv_num2)
        val btnBorrar: Button = findViewById(R.id.btnC)
        val btnIgual: Button = findViewById(R.id.btnIgual)

        btnIgual.setOnClickListener {
            val expresionInfija = tv_num2.text.toString()
            tv_num1.text = expresionInfija  // Mueve la operación completa a tv_num1
            tv_num2.text = ""  // Limpia tv_num2 para el resultado

            val expresionPostfija = Conversor.postFixConversion(expresionInfija)
            if (expresionPostfija == "Error") {
                tv_num2.text = "Expresión inválida"
            } else {
                try {
                    val resultado = OperarPostfix().evaluarPostfix(expresionPostfija)
                    tv_num2.text = resultado.toString()  // Muestra el resultado en tv_num2
                } catch (e: Exception) {
                    tv_num2.text = "Error: ${e.message}"
                }
            }
        }



        btnBorrar.setOnClickListener {
            tv_num1.text = ""
            tv_num2.text = ""
        }

    }
    fun presionarDigito(view: View) {
        var num2: String = tv_num2.text.toString()

        when (view.id) {
            R.id.btn0 -> tv_num2.text = num2 + "0"
            R.id.btn1 -> tv_num2.text = num2 + "1"
            R.id.btn2 -> tv_num2.text = num2 + "2"
            R.id.btn3 -> tv_num2.text = num2 + "3"
            R.id.btn4 -> tv_num2.text = num2 + "4"
            R.id.btn5 -> tv_num2.text = num2 + "5"
            R.id.btn6 -> tv_num2.text = num2 + "6"
            R.id.btn7 -> tv_num2.text = num2 + "7"
            R.id.btn8 -> tv_num2.text = num2 + "8"
            R.id.btn9 -> tv_num2.text = num2 + "9"
            R.id.btnPunto -> if (!num2.contains(".")) tv_num2.text = num2 + "."
            R.id.btnParenIzq -> tv_num2.text = num2 + "("
            R.id.btnParenDer -> tv_num2.text = num2 + ")"
        }
    }

    fun clickOperacion(view: View) {
        val currentText = tv_num2.text.toString()
        val lastChar = if (currentText.isNotEmpty()) currentText.last() else ' '

        if (lastChar.isDigit() || lastChar == ')') {  // Asegura que se pueda añadir un operador
            val operador = when (view.id) {
                R.id.btnSuma -> "+"
                R.id.btnResta -> "-"
                R.id.btnMultiplicacion -> "*"
                R.id.btnDivision -> "/"
                R.id.btnPotencia -> "^"
                else -> ""
            }

            if (operador == "/" && currentText.endsWith("/")) {
                tv_num2.text = "Error: División por cero"
                return
            }

            tv_num2.text = "$currentText $operador "
        }
    }


}

class Conversor {
    companion object {
        // Método para convertir infix a postfix
        fun postFixConversion(string: String): String {
            var resultado = "" // Variable que almacenará el resultado en notación postfix
            val stack = ArrayDeque<Char>() // Pila para manejar operadores y paréntesis
            var i = 0 // Índice para recorrer la cadena de entrada

            while (i < string.length) {
                val s = string[i] // Carácter actual

                if (s.isDigit()) { // Si el carácter es un dígito
                    resultado += s // Agregar el dígito al resultado
                    // Manejar números de múltiples dígitos
                    while (i + 1 < string.length && string[i + 1].isDigit()) {
                        resultado += string[i + 1] // Agregar dígitos adicionales al resultado
                        i++ // Avanzar el índice
                    }
                    resultado += " " // Agregar un espacio después del número
                } else if (s == '(') { // Si el carácter es un paréntesis de apertura
                    stack.push(s) // Empujar el paréntesis en la pila
                } else if (s == ')') { // Si el carácter es un paréntesis de cierre
                    // Desapilar y agregar al resultado hasta encontrar un paréntesis de apertura
                    while (stack.isNotEmpty() && stack.peek() != '(') {
                        resultado += "${stack.pop()} "
                    }
                    if (stack.isNotEmpty()) stack.pop() // Eliminar el paréntesis de apertura
                } else if (notNumeric(s)) { // Si el carácter es un operador
                    // Desapilar y agregar al resultado mientras el operador en la cima de la pila tenga mayor o igual precedencia
                    while (stack.isNotEmpty() && operatorPrecedence(s) <= operatorPrecedence(stack.peek()!!)) {
                        resultado += "${stack.pop()} "
                    }
                    stack.push(s) // Empujar el operador en la pila
                }
                i++ // Avanzar el índice
            }

            // Desapilar y agregar al resultado todos los operadores restantes en la pila
            while (stack.isNotEmpty()) {
                if (stack.peek() == '(') return "Error" // Si queda un paréntesis de apertura, hay un error en la expresión
                resultado += "${stack.pop()} "
            }
            return resultado.trim() // Devolver el resultado sin espacios adicionales al final
        }

        // Método para verificar si un carácter no es un dígito
        fun notNumeric(ch: Char): Boolean = when (ch) {
            '+', '-', '*', '/', '(', ')', '^' -> true // Operadores y paréntesis no son numéricos
            else -> false // Cualquier otro carácter se considera numérico
        }

        // Método para determinar la precedencia de un operador
        fun operatorPrecedence(ch: Char): Int = when (ch) {
            '+', '-' -> 1 // Suma y resta tienen la precedencia más baja
            '*', '/' -> 2 // Multiplicación y división tienen precedencia intermedia
            '^' -> 3 // La exponenciación tiene la precedencia más alta
            else -> -1 // Cualquier otro carácter tiene precedencia inválida
        }

        // Funciónes de extensión
        fun <T> ArrayDeque<T>.push(element: T) = addLast(element)
        fun <T> ArrayDeque<T>.pop() = removeLastOrNull()
        fun <T> ArrayDeque<T>.peek() = lastOrNull()
    }
}