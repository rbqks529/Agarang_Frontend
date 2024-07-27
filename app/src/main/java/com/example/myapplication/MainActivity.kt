package com.example.myapplication

import android.content.Intent
import com.example.myapplication.Home.HomeFragment
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Diary.Diary.DiaryFragment
import com.example.myapplication.Memory.MemoryMainActivity
import com.example.myapplication.Music.MusicFragment
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
        binding.mainBnv.selectedItemId = R.id.HomeFragment


        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.HomeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.DiaryFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, DiaryFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.MemoryActivity -> {
                    val intent = Intent(this, MemoryMainActivity :: class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }

                R.id.MusicFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MusicFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
        binding.mainBnv.itemIconTintList = null


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = supportFragmentManager.findFragmentById(R.id.main_frm)
                if (fragment is HomeFragment) {
                    finish()
                } else {
                    binding.mainBnv.selectedItemId = R.id.HomeFragment
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

    }

}