package com.angelbustamante.masterquest

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angelbustamante.masterquest.databinding.ActivityDifficultyBinding

class DifficultyActivity : AppCompatActivity() {

    private lateinit var ui: ActivityDifficultyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityDifficultyBinding.inflate(layoutInflater)
        setContentView(ui.root)

        fun startQuiz(level: String) {
            startActivity(
                Intent(this, QuizActivity::class.java).putExtra("level", level)
            )
            finish()
        }

        ui.easyButton.setOnClickListener    { startQuiz("easy") }
        ui.mediumButton.setOnClickListener  { startQuiz("medium") }
        ui.hardButton.setOnClickListener    { startQuiz("hard") }
    }
}