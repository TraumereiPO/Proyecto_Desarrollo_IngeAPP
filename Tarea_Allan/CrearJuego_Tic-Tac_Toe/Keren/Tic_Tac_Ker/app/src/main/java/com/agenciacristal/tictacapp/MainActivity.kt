package com.agenciacristal.tictacapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException

import com.agenciacristal.tictacapp.SocketIO
class MainActivity : AppCompatActivity() {

    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mySocket = SocketIO()
        mySocket.setSocket()
        mySocket.establishConnection()

        val myOtherSocket = mySocket.getSocket()
        myOtherSocket.on("welcome") { args ->
            val data = args[0] as JSONObject
            runOnUiThread {
                Toast.makeText(this, data.getString("msg"), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun nextScreen(v :View){
        val player1 = findViewById<EditText>(R.id.etPlayer1)
        val player2 = findViewById<EditText>(R.id.etPlayer2)

        val intent = Intent(applicationContext,GameActivity::class.java)
        intent.putExtra("player1",player1.text.toString())
        intent.putExtra("player2",player2.text.toString())
        startActivity(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }


}