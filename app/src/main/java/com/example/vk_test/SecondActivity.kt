//_____________SecondActivity__________________
package com.example.vk_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import com.bumptech.glide.Glide

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        //вывод гифки
        val imageView=findViewById<ImageView>(R.id.imageView)
        val url = intent.getStringExtra("url")
        Glide.with(this).load(url).into(imageView)
        //вывод названия
        val str=intent.getStringExtra("title")
        val textView =  findViewById<TextView>(R.id.textView)
        textView.setText(str);
    }
}