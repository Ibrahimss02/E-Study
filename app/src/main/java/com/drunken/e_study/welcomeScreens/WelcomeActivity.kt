package com.drunken.e_study.welcomeScreens

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.drunken.e_study.R
import com.google.android.material.snackbar.Snackbar

class WelcomeActivity : AppCompatActivity() {

    private lateinit var progressDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        progressDialog = Dialog(this)
    }

    fun showErrorSnackbar(message : String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
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