package com.example.your_precioustime.SecondActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.Model.*
import com.example.your_precioustime.R
import com.example.your_precioustime.Retrofit.Coroutines_InterFace
import com.example.your_precioustime.Retrofit.Retrofit_Client
import com.example.your_precioustime.Retrofit.Retrofit_InterFace
import com.example.your_precioustime.Retrofit.Retrofit_Manager
import com.example.your_precioustime.ThridActivity.BusSubwayActivity
import com.example.your_precioustime.Url
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.BusFragmentBinding
import kotlinx.android.synthetic.main.subway_fragment.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import kotlin.Exception
import kotlin.coroutines.CoroutineContext


class BusFragment:Fragment(R.layout.bus_fragment),CoroutineScope {

    lateinit var upAdpater : UpAdpater
    private var busbinding :BusFragmentBinding? =null
    private val binding get() = busbinding!!
    private var hicityCode: String? = null
    lateinit var busStationSearchAdapter: Bus_Station_Search_Adapter


    lateinit var job:Job

    override val coroutineContext : CoroutineContext
        get() = Dispatchers.Main + job


    private var retrofitInterface: Retrofit_InterFace =
        Retrofit_Client.getClient(Url.BUS_MAIN_URL).create(Retrofit_InterFace::class.java)

    private var retrofitFuckInterFace:Retrofit_InterFace=
        Retrofit_Client.getFuckClient(Url.ODSAY_BASE_URL).create(Retrofit_InterFace::class.java)


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        busbinding = BusFragmentBinding.bind(view)
        job = Job()

//        Retrofit_Manager.retrofitManager.GETBUS()
////        ClickSearchBtn()
//
//        binding.clickhere.setOnClickListener {
//            CoroutinesCall("31010","우남")
//            Log.d(TAG, "onViewCreated: sdfafasfasdfadfafd")
//        }

        ClickSearchBtn()



    }


    private fun ClickSearchBtn() =with(binding) {

        clickhere.setOnClickListener {
            val suwoncitycode:String = "31010"
            val StationEditName = SearchEditText.text.toString()
            SetRecyclerView(suwoncitycode,StationEditName)
//            hellomy(suwoncitycode,"GGB203000129")
            //경

       }

    }


    private fun citycodecall()= with(binding){
        val call = retrofitInterface.CityGet()

        call.enqueue(object :Callback<City>{
            override fun onResponse(call: Call<City>, response: Response<City>) {
                Log.d(TAG, "onResponse:${response.body()}")
            }

            override fun onFailure(call: Call<City>, t: Throwable) {
                Log.d(TAG, "onFailure:$t")
            }

        })
    }

    private fun stationname(citycode:String , stationName:String){
        val call = retrofitFuckInterFace.StationNameGet(citycode,stationName)

        call.enqueue(object :retrofit2.Callback<StationBus>{
            override fun onResponse(call: Call<StationBus>, response: Response<StationBus>) {
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<StationBus>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
    }

    private fun SetRecyclerView(citycode:String,stationName:String)=with(binding) {

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
                Log.d(TAG, "onFailure:$t")
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




    private fun CoroutinesCall(citycode:String,stationName: String)=with(binding){
        launch(coroutineContext) {
            try{
                withContext(Dispatchers.IO){
                    val response = Retrofit_Client.Retrofit_Object.Coroutines_BUS_NAMEGET(citycode,stationName)

                    if(response.isSuccessful){
                        val body = response.body()
                        withContext(Dispatchers.Main){
                            Log.d(TAG, "CoroutinesCall:${response.body()}")
                        }
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                Log.d(TAG, "CoroutinesCall:에러다에러다에러")
            }
        }
    }
}

