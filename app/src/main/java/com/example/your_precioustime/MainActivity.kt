package com.example.your_precioustime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.your_precioustime.SecondActivity.SecondActivity
import com.example.your_precioustime.TestMapActivity.MapsActivity
import com.example.your_precioustime.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var mainActivitybinding : ActivityMainBinding? = null
    private val binding get() = mainActivitybinding!!
    //hi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivitybinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.MainLogoDd.setOnClickListener {
//            val intent = Intent(this,BusSubwayActivity::class.java)
            val intent = Intent(this,SecondActivity::class.java)
//
            startActivity(intent)
//            Toast.makeText(this,"gdgd",Toast.LENGTH_SHORT).show()
        }

        binding.mapGobtn.setOnClickListener {
            val intent = Intent(this,MapsActivity::class.java)
            startActivity(intent)
        }

    }





}