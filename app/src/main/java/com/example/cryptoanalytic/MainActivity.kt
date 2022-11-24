package com.example.cryptoanalytic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbarLayout))


        val host = NavHostFragment.create(R.navigation.main_navigation)
        supportFragmentManager.beginTransaction().replace(R.id.host_container, host).setPrimaryNavigationFragment(host).commitAllowingStateLoss()
    }
}