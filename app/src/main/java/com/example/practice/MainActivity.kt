package com.example.practice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val days = mapOf(
            R.id.btnMonday to "Понедельник",
            R.id.btnTuesday to "Вторник",
            R.id.btnWednesday to "Среда",
            R.id.btnThursday to "Четверг",
            R.id.btnFriday to "Пятница",
            R.id.btnSaturday to "Суббота"
        )

        for ((buttonId, dayName) in days) {
            findViewById<Button>(buttonId).setOnClickListener {
                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra("day", dayName)
                startActivity(intent)
            }
        }
    }
}