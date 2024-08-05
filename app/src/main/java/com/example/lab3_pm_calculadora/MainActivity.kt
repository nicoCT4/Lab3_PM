package com.example.lab3_pm_calculadora

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun presionarDigito(view: View){
        val Bnum2: TextView = findViewById(R.id.num2)
        var num2: String = Bnum2.text.toString()

        when(view.id){
            R.id.btn0 -> Bnum2.setText(num2 + "0")
            R.id.btn1 -> Bnum2.setText(num2 + "1")
            R.id.btn2 -> Bnum2.setText(num2 + "2")
            R.id.btn3 -> Bnum2.setText(num2 + "3")
            R.id.btn4 -> Bnum2.setText(num2 + "4")
            R.id.btn5 -> Bnum2.setText(num2 + "5")
            R.id.btn6 -> Bnum2.setText(num2 + "6")
            R.id.btn7 -> Bnum2.setText(num2 + "7")
            R.id.btn8 -> Bnum2.setText(num2 + "8")
            R.id.btn9 -> Bnum2.setText(num2 + "9")
            R.id.btnPunto -> Bnum2.setText(num2 + ".")
        }


    }
}