package com.antichaos.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.inject
import hilt.android.HiltActivity

@HiltActivity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Will create layout in next step
    }
}