package com.example.your_precioustime.SecondActivity.subwayfavorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.ObjectManager.Myobject
import com.example.your_precioustime.Retrofit.Retrofit_Manager.Companion.retrofitManager
import com.example.your_precioustime.SecondActivity.SubwayFragment.SubwayAdapter
import com.example.your_precioustime.databinding.ActivitySubwayFavroiteDeevInfoBinding

class SubwayFavroiteDeevInfo : AppCompatActivity() {

    var subwayFavroiteDeevInfoBinding: ActivitySubwayFavroiteDeevInfoBinding? = null
    val binding get() = subwayFavroiteDeevInfoBinding!!

    lateinit var subwayAdapter: SubwayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subwayFavroiteDeevInfoBinding =
            ActivitySubwayFavroiteDeevInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setRecyclearView()

        binding.backbtn.setOnClickListener {
            onBackPressed()
        }

        Myobject.myobject.ToggleSet(
            this,
            binding.floatingBtn,
            binding.FavroiteFloatBtn,
            binding.SubwayFloatBtn,
            binding.BusfloatBtn
        )


    }

    private fun setRecyclearView() {
        val subwayname = intent.getStringExtra("subwayname").toString()
        binding.SubwayStationName.text = subwayname
        retrofitManager.getsubwayCall(subwayname, mymodel = { subwaymodel ->
            subwayAdapter = SubwayAdapter()
            binding.SubwayFVDeepInFoRecyclerView.apply {
                adapter = subwayAdapter
                layoutManager = LinearLayoutManager(this@SubwayFavroiteDeevInfo)
                subwayAdapter.submitList(subwaymodel)
            }
        })

    }
}