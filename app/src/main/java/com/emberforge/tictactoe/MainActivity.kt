package com.emberforge.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private lateinit var resetButton: Button
    private var isPlayerOneTurn = true
    private lateinit var playerOneWins: TextView
    private lateinit var playerTwoWins: TextView
    private var gameOver = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = getButtons()
        assignButtonCallbacks()

        resetButton = findViewById(R.id.reset_button)
        resetButtons()
        resetButton.setOnClickListener{
            resetButtons()
        }

        playerOneWins = findViewById(R.id.player_one_score)
        playerTwoWins = findViewById(R.id.player_two_score)
    }

    private fun getButtons(): Array<Button> {
            return arrayOf(
                findViewById(R.id.button_1),
                findViewById(R.id.button_2),
                findViewById(R.id.button_3),
                findViewById(R.id.button_4),
                findViewById(R.id.button_5),
                findViewById(R.id.button_6),
                findViewById(R.id.button_7),
                findViewById(R.id.button_8),
                findViewById(R.id.button_9)
        )
    }

    private fun assignButtonCallbacks() {
        for (button in buttons) {
            button.setOnClickListener {
                if (!gameOver) {
                    if (button.text == "X" || button.text == "O") {
                        Snackbar.make(
                            it,
                            "Square is already occupied.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        if (isPlayerOneTurn) {
                            button.text = "X"
                        } else {
                            button.text = "O"
                        }
                        checkForWin(it)
                        isPlayerOneTurn = !isPlayerOneTurn
                    }
                } else {
                    Snackbar.make(it, "Game Over. Hit 'Reset'!", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkForWin(view: View) {
        var playerWon = false
        if (buttons[0].text != " " && buttons[0].text == buttons[1].text && buttons[1].text == buttons[2].text) {
            playerWon = true
        } else if (buttons[3].text != " " && buttons[3].text == buttons[4].text && buttons[4].text == buttons[5].text) {
            playerWon = true
        } else if (buttons[6].text != " " && buttons[6].text == buttons[7].text && buttons[7].text == buttons[8].text) {
            playerWon = true
        } else if (buttons[0].text != " " && buttons[0].text == buttons[3].text && buttons[3].text == buttons[6].text) {
            playerWon = true
        } else if (buttons[1].text != " " && buttons[1].text == buttons[4].text && buttons[4].text == buttons[7].text) {
            playerWon = true
        } else if (buttons[2].text != " " && buttons[2].text == buttons[5].text && buttons[5].text == buttons[8].text) {
            playerWon = true
        } else if (buttons[0].text != " " && buttons[0].text == buttons[4].text && buttons[4].text == buttons[8].text) {
            playerWon = true
        } else if (buttons[2].text != " " && buttons[2].text == buttons[4].text && buttons[4].text == buttons[6].text) {
            playerWon = true
        }

        if (playerWon && isPlayerOneTurn) {
            playerOneWins.text = (Integer.parseInt(playerOneWins.text.toString()) + 1).toString()
            gameOver = true
            Snackbar.make(view, "Player One wins! Hit 'reset' to play again.", Snackbar.LENGTH_LONG).show()
        } else if (playerWon && !isPlayerOneTurn) {
            playerTwoWins.text = (Integer.parseInt(playerTwoWins.text.toString()) + 1).toString()
            gameOver = true
            Snackbar.make(view, "Player Two wins! Hit 'reset' to play again.", Snackbar.LENGTH_LONG).show()
        }

        if (!playerWon) {
            for (button in buttons) {
                if (button.text == " ") {
                    return
                }
            }
            gameOver = true
            Snackbar.make(view, "Tie! Hit 'reset' to play again.", Snackbar.LENGTH_LONG).show()
        }

    }

    private fun resetButtons() {
        for (button in buttons) {
            button.text = " "
        }
        isPlayerOneTurn = true
        gameOver = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("isPlayerOneTurn", isPlayerOneTurn)
        outState.putCharSequence("playerOneWins", playerOneWins.text)
        outState.putCharSequence("playerTwoWins", playerTwoWins.text)
        outState.putBoolean("gameOver", gameOver)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        isPlayerOneTurn = savedInstanceState.getBoolean("isPlayerOneTurn")
        playerOneWins.text = savedInstanceState.getCharSequence("playerOneWins")
        playerTwoWins.text = savedInstanceState.getCharSequence("playerTwoWins")
        gameOver = savedInstanceState.getBoolean("gameOver")
    }
}
