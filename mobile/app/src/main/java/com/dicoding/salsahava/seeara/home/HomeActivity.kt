package com.dicoding.salsahava.seeara.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.salsahava.seeara.R
import com.dicoding.salsahava.seeara.recorder.RecorderFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_home, RecorderFragment())
            commit()
        }
    }
}