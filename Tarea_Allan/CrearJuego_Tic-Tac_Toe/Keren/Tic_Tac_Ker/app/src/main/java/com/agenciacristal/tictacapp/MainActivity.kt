package com.agenciacristal.tictacapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {

    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            socket = IO.socket("http://10.0.2.2:3000")
        } catch (e: URISyntaxException) {
            Log.e("SocketError", "La URL del socket es inválida: ${e.message}")
            e.printStackTrace()
        }

        socket.on(Socket.EVENT_CONNECT) {
            Log.d("SocketInfo", "Socket conectado correctamente")
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
            Log.e("SocketError", "Error de conexión: ${args[0]}")
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            Log.d("SocketInfo", "Socket desconectado")
        }

        socket.on("welcome") { args ->
            if (args.isNotEmpty()) {
                val data = args[0] as JSONObject
                val mensaje = data.getString("msg")

                runOnUiThread {
                    Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                }
            }
        }
        socket.connect()

    }


    fun nextScreen(v:View){
        val player1 = findViewById<EditText>(R.id.etPlayer1)
        val player2 = findViewById<EditText>(R.id.etPlayer2)

        var intent = Intent(applicationContext,GameActivity::class.java)
        intent.putExtra("player1",player1.text.toString())
        intent.putExtra("player2",player2.text.toString())
        startActivity(intent)

    }
    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }


}