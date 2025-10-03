package com.agenciacristal.tictacapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun nextScreen(v:View){
        var player1 = findViewById<EditText>(R.id.etPlayer1)
        var player2 = findViewById<EditText>(R.id.etPlayer2)

        var intent = Intent(applicationContext,GameActivity::class.java)
        intent.putExtra("player1",player1.text.toString())
        intent.putExtra("player2",player2.text.toString())
        startActivity(intent)

    }
}