package com.drunken.e_study.ui.mainScreens

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.drunken.e_study.R
import com.drunken.e_study.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var progressDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        val navController = fragmentManager.navController
        progressDialog = Dialog(this)

        binding.mainBottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.browseCourseFragment -> {
                    binding.mainBottomNav.visibility = View.GONE
                }
                R.id.courseDetailFragment -> {
                    binding.mainBottomNav.visibility = View.GONE
                }
                R.id.confirmPaymentFragment -> {
                    binding.mainBottomNav.visibility = View.GONE
                }
                else -> {
                    binding.mainBottomNav.visibility = View.VISIBLE
                }
            }
        }

    }

    fun showErrorSnackbar(message : String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
        snackbar.show()
    }

    fun showSnackbar(message : String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryColor))
        snackbar.show()
    }

    fun showProgressDialog(message: String){
        progressDialog.apply {
            setContentView(R.layout.progress_dialog)
            findViewById<TextView>(R.id.tv_progress_text).text = message
            progressDialog.show()
        }
    }

    fun hideProgressDialog(){
        progressDialog.dismiss()
    }

}