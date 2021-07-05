package com.drunken.e_study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.drunken.e_study.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        val navController = fragmentManager.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.browseCourseSdFragment -> {
                    binding.mainBottomNav.visibility = View.GONE
                }
                else -> {
                    binding.mainBottomNav.visibility = View.VISIBLE
                }
            }
        }

        binding.mainBottomNav.setupWithNavController(navController)
    }

}