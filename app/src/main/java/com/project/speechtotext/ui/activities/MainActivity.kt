package com.project.speechtotext.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.project.speechtotext.R
import com.project.speechtotext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var adRequest: AdRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        initBottomBar()
        adRequest = AdRequest.Builder().build()
        binding.bannersAdd.loadAd(adRequest!!)
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) //for version 33+ (android 13)
            initOnBackPressed()
    }



    private fun initBottomBar() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNav, navController)
        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.home_fragment)
                    binding.mainToolbar.setTitle(R.string.home)
                }
                R.id.languages -> {
                    navController.navigate(R.id.language_fragment)
                    binding.mainToolbar.setTitle(R.string.languages)
                }
                R.id.saved_notes -> {
                    navController.navigate(R.id.saved_notes_fragment)
                    binding.mainToolbar.setTitle(R.string.saved_notes)
                }
                R.id.settings -> {
                    navController.navigate(R.id.settings_fragment)
                    binding.mainToolbar.setTitle(R.string.settings)
                }
            }
            true
        }
    }

    //for 13+
    private fun initOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedNavActions()
            }
        })
    }

    private fun onBackPressedNavActions() {
        when (navController.currentDestination?.id) {
            R.id.home_fragment -> binding.bottomNav.menu.getItem(0).isChecked = true
            R.id.language_fragment -> binding.bottomNav.menu.getItem(1).isChecked = true
            R.id.saved_notes_fragment -> binding.bottomNav.menu.getItem(2).isChecked = true
            R.id.settings_fragment -> binding.bottomNav.menu.getItem(3).isChecked = true
            else -> finish()
        }
    }

    //for android versions below then 13
    override fun onBackPressed() {
        super.onBackPressed()
        onBackPressedNavActions()
    }


}