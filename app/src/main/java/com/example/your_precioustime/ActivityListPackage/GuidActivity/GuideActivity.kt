package com.example.your_precioustime.ActivityListPackage.GuidActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.your_precioustime.databinding.ActivityGuideBinding

class GuideActivity : AppCompatActivity() {

    private var guideBinding: ActivityGuideBinding? = null
    private val binding get() = guideBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        guideBinding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backwowbtn.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
}