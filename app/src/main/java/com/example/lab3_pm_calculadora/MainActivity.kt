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
    //0->Nada, 1->Suma, 2-> resta, 3-> multiplicacion, 4-> Divison
    var operacion: Int = 0
    var numero1: Double= 0.0
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

        btnIgual.setOnClickListener{
            var numero2:Double = tv_num2.text.toString().toDouble()
            var resp: Double = 0.0

            when (operacion){
                1 -> resp = numero1 + numero2
                2 -> resp = numero1 - numero2
                3 -> resp = numero1 * numero2
                4 -> resp = numero1 / numero2

            }

            tv_num2.setText(resp.toString())
            tv_num1.setText("")
        }

        btnBorrar.setOnClickListener{
            tv_num1.setText("")
            tv_num2.setText("")
            numero1 = 0.0
            operacion=0
        }
    }
    fun presionarDigito(view: View){

        var num2: String = tv_num2.text.toString()

        when(view.id){
            R.id.btn0 -> {
                if (operacion == 4 && num2.isEmpty()) {  // Específicamente para división después de seleccionar la operación
                    tv_num2.text = "Error: División por cero"
                } else {
                    tv_num2.setText(num2 + "0")
                }
            }
            R.id.btn1 -> tv_num2.setText(num2 + "1")
            R.id.btn2 -> tv_num2.setText(num2 + "2")
            R.id.btn3 -> tv_num2.setText(num2 + "3")
            R.id.btn4 -> tv_num2.setText(num2 + "4")
            R.id.btn5 -> tv_num2.setText(num2 + "5")
            R.id.btn6 -> tv_num2.setText(num2 + "6")
            R.id.btn7 -> tv_num2.setText(num2 + "7")
            R.id.btn8 -> tv_num2.setText(num2 + "8")
            R.id.btn9 -> tv_num2.setText(num2 + "9")
            R.id.btnPunto -> tv_num2.setText(num2 + ".")
        }
    }

    fun clickOperacion(view: View){
        numero1= tv_num2.text.toString().toDouble()
        var num2_text: String = tv_num2.text.toString()
        tv_num2.setText("")

        when(view.id){
            R.id.btnSuma ->{
                tv_num1.setText(num2_text + "+")
                operacion = 1
            }
            R.id.btnResta ->{
                tv_num1.setText(num2_text + "-")
                operacion = 2
            }
            R.id.btnMultiplicacion ->{
                tv_num1.setText(num2_text + "*")
                operacion = 3
            }
            R.id.btnDivision ->{
                if (numero1 == 0.0) {
                    tv_num2.text = "Error"
                    tv_num1.text = ""
                } else {
                    tv_num1.text = "${tv_num1.text} / "
                    operacion = 4
                }
            }

        }
    }
}