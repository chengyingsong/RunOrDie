package com.example.runordie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val groupButton = findViewById<Button>(R.id.toGroup)
        val mapButton = findViewById<Button>(R.id.toMap)

        groupButton.setOnClickListener{
            Log.i("Jump Information","To Group")
            val intent = Intent(this@MainActivity,GroupActivity::class.java)
            startActivity(intent)
        }

        mapButton.setOnClickListener{
            Log.i("Jump Information","To Map")
            val intent = Intent(this@MainActivity,MapActivity::class.java)
            startActivity(intent)
        }

    }


}