package com.example.lab3_pm_calculadora

import java.util.Stack

/**
 * Clase para evaluar expresiones matemáticas en notación postfija.
 */
class OperarPostfix {
    /**
     * Evalúa una expresión postfija y devuelve el resultado como un Double.
     * Utiliza una pila para manejar los operandos y realizar las operaciones en el orden correcto.
     *
     * @param expresion La cadena de texto que contiene la expresión postfija a evaluar.
     * @return El resultado de la evaluación de la expresión.
     * @throws ArithmeticException Si ocurre una división por cero.
     * @throws IllegalArgumentException Si se encuentra un operador desconocido o la expresión es inválida.
     */
    fun evaluarPostfix(expresion: String): Double {
        val stack = Stack<Double>() // Pila para almacenar los operandos durante la evaluación.
        val elementos = expresion.split(" ")// Divide la expresión en elementos separados por espacios.

        for (elemento in elementos) {
            when {
                elemento.toDoubleOrNull() != null -> stack.push(elemento.toDouble())
                // Si el elemento es un número, lo convierte a Double y lo empuja a la pila.
                stack.size >= 2 -> {
                    // Asegura que hay al menos dos elementos en la pila para realizar una operación.
                    val b = stack.pop()
                    val a = stack.pop()
                    when (elemento) {
                        // Realiza la operación correspondiente al operador actual y empuja el resultado a la pila.
                        "+" -> stack.push(a + b)
                        "-" -> stack.push(a - b)
                        "*" -> stack.push(a * b)
                        // Lanza una excepción en caso de división por cero.
                        "/" -> if (b == 0.0) throw ArithmeticException("División por cero") else stack.push(a / b)
                        "^" -> stack.push(Math.pow(a, b))
                        else -> throw IllegalArgumentException("Operador desconocido: $elemento")
                    }
                }
                else -> throw IllegalArgumentException("Expresión inválida.")
            }
        }
        // Devuelve el resultado final de la pila, que es el resultado de evaluar toda la expresión.
        return stack.pop()
    }
}
