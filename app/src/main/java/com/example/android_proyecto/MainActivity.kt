package com.example.android_proyecto

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.android_proyecto.models.User
import com.example.android_proyecto.utils.Constants
import kotlinx.android.synthetic.main.activity_user_profile.*

class MainActivity : AppCompatActivity() {
    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Create an instance of Android SharedPreferences
        val sharedPreferences =
            getSharedPreferences(Constants.MYSHOPAPP_PREFERENCES, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!!
        // Set the result to the tv_main.
        val et_first_name = findViewById<TextView>(R.id.et_first_name)
        et_first_name.setText(mUserDetails.firstName)

        et_first_name.text = "Hello $et_first_name"
        // END


    }


}