package com.angelbustamante.masterquest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.angelbustamante.masterquest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var ui: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.startButton.setOnClickListener {
            startActivity(Intent(this, DifficultyActivity::class.java))
        }

        ui.exitButton.setOnClickListener {
            finishAffinity()      // cierra todas las activities de la app
        }
    }
}
