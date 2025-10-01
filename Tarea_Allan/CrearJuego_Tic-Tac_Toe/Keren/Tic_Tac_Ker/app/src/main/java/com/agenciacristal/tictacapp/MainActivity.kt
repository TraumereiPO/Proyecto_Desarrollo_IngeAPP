package com.agenciacristal.tictacapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {

    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            socket = IO.socket("http://10.0.2.2:3000")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        socket.connect()

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