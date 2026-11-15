
package com.bassobserver.ai

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private var spins = 0
    private var bonuses = 0
    private lateinit var stats: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("BassData", MODE_PRIVATE)
        spins = prefs.getInt("spins", 0)
        bonuses = prefs.getInt("bonuses", 0)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 30, 24, 30)
            setBackgroundColor(Color.rgb(15, 20, 32))
        }

        fun addText(value: String, size: Float, color: Int) {
            layout.addView(TextView(this).apply {
                text = value
                textSize = size
                setTextColor(color)
                gravity = Gravity.CENTER
                setPadding(8, 14, 8, 14)
            })
        }

        addText("BASS OBSERVER AI", 27f, Color.GREEN)
        addText("BIG BASS BONANZA", 16f, Color.WHITE)

        stats = TextView(this).apply {
            textSize = 19f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(12, 30, 12, 30)
        }
        layout.addView(stats)

        fun save() {
            prefs.edit()
                .putInt("spins", spins)
                .putInt("bonuses", bonuses)
                .apply()
            updateStats()
        }

        val spinButton = Button(this).apply {
            text = "DODAJ OBRÓT"
            setOnClickListener {
                spins++
                save()
            }
        }
        layout.addView(spinButton)

        val bonusButton = Button(this).apply {
            text = "ZAREJESTRUJ BONUS"
            setOnClickListener {
                bonuses++
                save()
            }
        }
        layout.addView(bonusButton)

        val resetButton = Button(this).apply {
            text = "RESETUJ STATYSTYKI"
            setOnClickListener {
                spins = 0
                bonuses = 0
                save()
            }
        }
        layout.addView(resetButton)

        setContentView(layout)
        updateStats()
    }

    private fun updateStats() {
        stats.text = """
            HISTORIA SESJI

            Łączna liczba obrotów: $spins

            Zarejestrowane bonusy: $bonuses

            Obroty na jeden bonus:
            ${if (bonuses > 0) "%.1f".format(spins.toFloat() / bonuses) else "Brak danych"}
        """.trimIndent()
    }
}
