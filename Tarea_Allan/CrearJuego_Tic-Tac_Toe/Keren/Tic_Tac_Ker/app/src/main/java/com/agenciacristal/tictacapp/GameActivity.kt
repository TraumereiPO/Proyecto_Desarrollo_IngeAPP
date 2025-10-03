package com.agenciacristal.tictacapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject


class GameActivity : AppCompatActivity() {

    private lateinit var socket: Socket

    lateinit var b1:Button
    lateinit var b2:Button
    lateinit var b3:Button
    lateinit var b4:Button
    lateinit var b5:Button
    lateinit var b6:Button
    lateinit var b7:Button
    lateinit var b8:Button
    lateinit var b9:Button
    lateinit var tvPlayer1:TextView
    lateinit var tvPlayer2:TextView
    lateinit var tvScorePlayer1:TextView
    lateinit var tvScorePlayer2:TextView

    var currentPlayer:Int = 1
    var scorePlayer1:Int = 0
    var scorePlayer2:Int = 0
    var gameFinished:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initUI()

        try {
            socket = IO.socket("http://10.0.2.2:3000") // si usas emulador
            socket.connect()
        } catch (e: Exception) {
            Log.e("SocketError", "Error al conectar: ${e.message}")
        }
        socket.on("move") { args ->
            if (args.isNotEmpty()) {
                val data = args[0] as JSONObject
                val cell = data.getInt("cell")
                val player = data.getString("player")

                runOnUiThread {
                    val button = getButtonByCell(cell)
                    if (button.text.isEmpty()) {
                        button.text = player
                    }
                }
            }
        }

    }

    private fun initUI() {
        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b3 = findViewById(R.id.b3)
        b4 = findViewById(R.id.b4)
        b5 = findViewById(R.id.b5)
        b6 = findViewById(R.id.b6)
        b7 = findViewById(R.id.b7)
        b8 = findViewById(R.id.b8)
        b9 = findViewById(R.id.b9)
        tvPlayer1 = findViewById(R.id.tvPlayer1)
        tvPlayer2 = findViewById(R.id.tvPlayer2)
        tvScorePlayer1 = findViewById(R.id.tvScorePlayer1)
        tvScorePlayer2 = findViewById(R.id.tvScorePlayer2)

        tvPlayer1.text = intent?.extras?.getString("player1").toString()
        tvPlayer2.text = intent?.extras?.getString("player2").toString()
        nuevaPartida(tvPlayer1)
    }
    fun play(btn: View) {
        val myBtn: Button = btn as Button
        if (!gameFinished && myBtn.text.toString().isEmpty()) {
            val playerSymbol = if (currentPlayer == 1) "X" else "O"
            myBtn.text = playerSymbol

            val move = JSONObject()
                .put("cell", getCellNumber(myBtn)) // número de la celda
                .put("player", playerSymbol)
            socket.emit("move", move)

            validateWinner(myBtn)

            currentPlayer = if (currentPlayer == 1) 2 else 1
            tvPlayer1.setTextColor(if (currentPlayer == 1) Color.BLACK else Color.GRAY)
            tvPlayer2.setTextColor(if (currentPlayer == 2) Color.BLACK else Color.GRAY)
        }
    }

    private fun validateWinner(btn: View)
    {
        if (validateCards(btn)) {
            if (currentPlayer == 1) {
                scorePlayer1++
                tvScorePlayer1.text = "$scorePlayer1"
                Toast.makeText(applicationContext, "${tvPlayer1.text} Ganaste!!", Toast.LENGTH_LONG)
                    .show()
            } else {
                scorePlayer2++
                tvScorePlayer2.text = "$scorePlayer2"
                Toast.makeText(applicationContext, "${tvPlayer2.text} Ganaste!!", Toast.LENGTH_LONG)
                    .show()
            }
            gameFinished = true
        }
    }
    private fun validateCards(btn: View): Boolean {
        var b1Val = b1.text.toString().trim()
        var b2Val = b2.text.toString().trim()
        var b3Val = b3.text.toString().trim()
        var b4Val = b4.text.toString().trim()
        var b5Val = b5.text.toString().trim()
        var b6Val = b6.text.toString().trim()
        var b7Val = b7.text.toString().trim()
        var b8Val = b8.text.toString().trim()
        var b9Val = b9.text.toString().trim()
        var winner = false

        when(btn.id){
            b1.id-> {
                if (b1Val !="" &&
                    (b1Val == b2Val && b1Val == b3Val)
                    ||
                    (b1Val == b5Val && b1Val == b9Val)
                    ||
                    (b1Val == b4Val && b1Val == b7Val)
                ) {
                    winner = true
                }
            }
            b2.id->{
                    if (b2Val !="" &&
                        (b2Val == b1Val && b2Val == b3Val)
                        ||
                        (b2Val == b5Val && b2Val == b8Val)
                    ) {
                        winner = true
                    }
                }
            b3.id->{
                if (b3Val !="" &&
                    (b3Val == b1Val && b3Val == b2Val)
                    ||
                    (b3Val == b6Val && b3Val == b9Val)
                    ||
                    (b3Val == b5Val && b3Val == b7Val)
                ) {
                    winner = true
                }
            }
            b4.id->{
                if (b4Val !="" &&
                    (b4Val == b1Val && b4Val == b7Val)
                    ||
                    (b4Val == b5Val && b4Val == b6Val)
                ) {
                    winner = true
                }
            }
            b5.id->{
                if (b5Val!="" &&
                    (b5Val == b2Val && b5Val == b8Val)
                    ||
                    (b5Val == b4Val && b5Val == b6Val)
                    ||
                    (b5Val == b1Val && b5Val == b9Val)
                    ||
                    (b5Val == b3Val && b5Val == b7Val)
                ) {
                    winner = true
                }
            }
            b6.id->{
                if (b6Val !="" &&
                    (b6Val == b3Val && b6Val == b9Val)
                    ||
                    (b6Val == b4Val && b6Val == b5Val)
                ) {
                    winner = true
                }
            }
            b7.id->{
                if (b7Val !="" &&
                    (b7Val == b1Val && b7Val == b4Val)
                    ||
                    (b7Val == b3Val && b7Val == b5Val)
                    ||
                    (b7Val == b8Val && b7Val == b9Val)
                ) {
                    winner = true
                }
            }
            b8.id->{
                if (b8Val !=""&&
                    (b8Val == b7Val && b8Val == b9Val)
                    ||
                    (b8Val == b2Val && b8Val == b5Val)
                ) {
                    winner = true
                }
            }
            b9.id->{
                if (b9Val !="" &&
                    (b9Val == b3Val && b9Val == b6Val)
                    ||
                    (b9Val == b1Val && b9Val == b5Val)
                    ||
                    (b9Val == b7Val && b9Val == b8Val)
                ) {
                    winner = true
                }
            }

       }

        return winner
    }

    fun nuevaPartida(v:View){
        b1.text =""
        b2.text =""
        b3.text =""
        b4.text =""
        b5.text =""
        b6.text =""
        b7.text =""
        b8.text =""
        b9.text =""
        gameFinished = false
        currentPlayer = if(currentPlayer==1) 2 else 1
        currentPlayer = if(scorePlayer1==0 && scorePlayer2==0) 1 else currentPlayer

        tvPlayer1.setTextColor(if(currentPlayer==1) Color.BLACK else Color.GRAY)
        tvPlayer2.setTextColor(if(currentPlayer==2) Color.BLACK else Color.GRAY)

    }
    private fun getCellNumber(btn: Button): Int {
        return when (btn.id) {
            R.id.b1 -> 1
            R.id.b2 -> 2
            R.id.b3 -> 3
            R.id.b4 -> 4
            R.id.b5 -> 5
            R.id.b6 -> 6
            R.id.b7 -> 7
            R.id.b8 -> 8
            R.id.b9 -> 9
            else -> -1
        }
    }

    private fun getButtonByCell(cell: Int): Button {
        return when (cell) {
            1 -> b1
            2 -> b2
            3 -> b3
            4 -> b4
            5 -> b5
            6 -> b6
            7 -> b7
            8 -> b8
            9 -> b9
            else -> b1
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (::socket.isInitialized) socket.disconnect()
    }

}