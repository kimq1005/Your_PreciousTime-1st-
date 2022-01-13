package com.example.your_precioustime.SecondActivity.Busfragment

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.your_precioustime.Model.StationBus
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.SecondActivity.SecondActivity
import com.example.your_precioustime.SecondActivity.SubwayFragment.SubwayFragment
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util
import com.example.your_precioustime.databinding.ActivityBusBinding
import com.example.your_precioustime.databinding.BusFragmentBinding
import kotlinx.android.synthetic.main.activity_bus.*
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Response

class Bus_Activity : AppCompatActivity() {

    private var busBinding:ActivityBusBinding?=null
    private val binding get() = busBinding!!
    private var isFabOpen = false

    lateinit var busStationSearchAdapter: Bus_Station_Search_Adapter

    private var retrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        busBinding = ActivityBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val suwoncitycode:String = "31010"
        val StationEditName = binding.SearchEditText2.text.toString()
        SetRecyclerView(suwoncitycode,null)
//            hellomy(suwoncitycode,"GGB203000129")

        ClickSearchBtn()
        binding.floatingBtn.setOnClickListener {
            ToggleSet()
        }

    }

    private fun ClickSearchBtn() =with(binding) {

        clickhere.setOnClickListener {
            val suwoncitycode:String = "31010"
            val StationEditName = SearchEditText2.text.toString()
            SetRecyclerView(suwoncitycode,StationEditName)
//            hellomy(suwoncitycode,"GGB203000129")
            //경
        }

        SearchEditText.setOnClickListener {
            noResultTextView.visibility = View.INVISIBLE
        }

    }

    private fun ToggleSet(){

        if(isFabOpen){
            ObjectAnimator.ofFloat(binding.FvBtnFloat,"translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.SubwayFloatBtn,"translationY", 0f).apply { start() }
        }
        else{
            ObjectAnimator.ofFloat(binding.FvBtnFloat,"translationY", -150f).apply { start() }
            ObjectAnimator.ofFloat(binding.SubwayFloatBtn,"translationY", -300f).apply { start() }
        }

        binding.FvBtnFloat.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.SubwayFloatBtn.setOnClickListener {
            val intent = Intent(this, SubwayFragment::class.java)
            startActivity(intent)
            finish()
        }

        isFabOpen = !isFabOpen

    }




    fun SetRecyclerView(citycode:String,stationName:String?)=with(binding) {

        val stationcalls= retrofitInterface.StationNameGet(
            cityCode = citycode,
            staionName = stationName
        )

        stationcalls.enqueue(object :retrofit2.Callback<StationBus>{

            override fun onResponse(call: Call<StationBus>, response: Response<StationBus>) {
                val body = response.body()
                busStationSearchAdapter = Bus_Station_Search_Adapter()

                body?.let{it->
                    val hello = body.body.items.item
                    busRecyclerView.apply {
                        adapter = busStationSearchAdapter
                        layoutManager = LinearLayoutManager(context)
                        busStationSearchAdapter.submitList(hello)
                    }
                }
            }
            override fun onFailure(call: Call<StationBus>, t: Throwable) {
                Log.d(Util.TAG, "onFailure:$t")
                noResultTextView.visibility = View.VISIBLE

            }

        })






//        val buscall = retrofitInterface.BusGet(citycode,nodeId)
//
//        buscall.enqueue(object:retrofit2.Callback<Bus>{
//            override fun onResponse(call: Call<Bus>, response: Response<Bus>) {
//                val body = response.body()
//                val higg = body?.body?.items
//                Log.d(TAG, "onResponse: ${higg}")
//                body?.let{Bus->
//
//                    busRecyclerView.apply {
//                        val hello = Bus.body.items.item
//                        adapter=upAdpater
//                        upAdpater.submitList(hello)
//                        layoutManager = LinearLayoutManager(context)
//                    }
//
//                }
//            }
//
//            override fun onFailure(call: Call<Bus>, t: Throwable) {
//                Log.d(TAG, "onFailure: $t")
//            }
//
//        })

    }

}