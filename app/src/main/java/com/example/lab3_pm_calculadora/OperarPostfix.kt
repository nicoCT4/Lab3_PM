package com.example.lab3_pm_calculadora

import java.util.Stack

class OperarPostfix {
    fun evaluarPostfix(expresion: String): Double {
        val stack = Stack<Double>()
        val elementos = expresion.split(" ")

        for (elemento in elementos) {
            when {
                elemento.toDoubleOrNull() != null -> stack.push(elemento.toDouble())
                stack.size >= 2 -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    when (elemento) {
                        "+" -> stack.push(a + b)
                        "-" -> stack.push(a - b)
                        "*" -> stack.push(a * b)
                        "/" -> if (b == 0.0) throw ArithmeticException("División por cero") else stack.push(a / b)
                        "^" -> stack.push(Math.pow(a, b))
                        else -> throw IllegalArgumentException("Operador desconocido: $elemento")
                    }
                }
                else -> throw IllegalArgumentException("Expresión inválida.")
            }
        }
        return stack.pop()
    }
}
