package com.example.mypokemon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mypokemon.image_upload.ImageUpload

class Welcome : AppCompatActivity() {

    lateinit var btn_first: Button
    lateinit var btn_secon: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        btn_first = findViewById(R.id.btn_first)
        btn_secon = findViewById(R.id.btn_second)

        btn_first.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btn_secon.setOnClickListener {
            val intent = Intent(this,ImageUpload::class.java)
            startActivity(intent)
        }
    }
}