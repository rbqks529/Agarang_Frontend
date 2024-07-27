package com.example.myapplication.Memory

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMemoryMainBinding

class MemoryMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMemoryMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_main)

        

        val fragment=SelectGenreFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.memory_frm,fragment)
            .commit()
    }
}