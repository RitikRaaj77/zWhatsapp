package com.example.zwhatsapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var dataP = ""
        if(intent.action == Intent.ACTION_PROCESS_TEXT){
            dataP = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        }
        val number = dataP.replace("\\s".toRegex(), "")
        if(number.isEmpty()){
            Toast.makeText(this,"Thank You",Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        if(number.length<10){
            Toast.makeText(this,"Number is less than 10 digits",Toast.LENGTH_LONG).show()
            finish()
            return
        }
        val data :String  = if(number[0]=='+'){
            number.substring(1)
        }
        else if (number.length==10){
            "91$number"
        }
        else{
            number
        }
        if (data.isDigitsOnly()){
            startWhatsapp(data)
        }
        else{
            Toast.makeText(this,"Invalid Number",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    private fun startWhatsapp(data :String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")

        intent.data = Uri.parse("https://wa.me/$data")
        startActivity(intent)
        finish()
    }
}


