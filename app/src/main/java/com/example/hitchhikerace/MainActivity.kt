package com.example.hitchhikerace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hitchhikerace.view.MenuScreenFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = MenuScreenFragment()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .add(R.id.mainContainer, fragment)
            .commit()
    }
}
