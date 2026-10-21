package com.example.practice

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val day = intent.getStringExtra("day") ?: ""
        findViewById<TextView>(R.id.tvDay).text = day

        val etTime = findViewById<EditText>(R.id.etTime)
        val etSubject = findViewById<EditText>(R.id.etSubject)
        val container = findViewById<LinearLayout>(R.id.llSavedPairs)
        val prefs = getSharedPreferences("SchedulePrefs", Context.MODE_PRIVATE)

        fun updateList() {
            container.removeAllViews()
            val data = prefs.getString("data_$day", "") ?: ""

            if (data.isNotEmpty()) {
                val pairsList = data.split(";")
                for (pair in pairsList) {
                    val card = LinearLayout(this)
                    card.orientation = LinearLayout.HORIZONTAL
                    card.setBackgroundResource(android.R.drawable.editbox_background)
                    card.setPadding(16, 16, 16, 16)
                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 0, 0, 16)
                    card.layoutParams = params

                    val tv = TextView(this)
                    tv.text = "📅 $pair"
                    tv.textSize = 18f
                    tv.setTextColor(getColor(R.color.text_dark))
                    tv.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

                    val btnDelete = Button(this)
                    btnDelete.text = "🗑️"
                    btnDelete.setBackgroundColor(0)
                    btnDelete.textSize = 20f
                    btnDelete.setOnClickListener {
                        val newList = pairsList.filter { it != pair }
                        prefs.edit().putString("data_$day", newList.joinToString(";")).apply()
                        updateList()
                    }

                    card.addView(tv)
                    card.addView(btnDelete)
                    container.addView(card)
                }
            }
        }

        updateList()

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val time = etTime.text.toString()
            val subject = etSubject.text.toString()

            if (time.isEmpty() || subject.isEmpty()) {
                Toast.makeText(this, "Заполните поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val oldData = prefs.getString("data_$day", "") ?: ""
            val newData = if (oldData.isEmpty()) "$time - $subject" else "$oldData;$time - $subject"
            prefs.edit().putString("data_$day", newData).apply()

            etTime.text.clear()
            etSubject.text.clear()
            etTime.requestFocus()
            updateList()
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}