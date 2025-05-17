package com.angelbustamante.masterquest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.angelbustamante.masterquest.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var ui: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityResultBinding.inflate(layoutInflater)
        setContentView(ui.root)

        // Recogemos extras enviados desde QuizActivity
        val score  = intent.getIntExtra("score", 0)
        val total  = intent.getIntExtra("total", 0)

        ui.scoreText.text = "$score / $total"

        ui.playAgainButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        ui.exitButton.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
            )
            finish()
        }
    }
}