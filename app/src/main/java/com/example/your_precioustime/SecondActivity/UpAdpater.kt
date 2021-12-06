package com.example.your_precioustime.SecondActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.your_precioustime.App
import com.example.your_precioustime.Model.Item
import com.example.your_precioustime.SecondActivity.DB.BUSDataBase
import com.example.your_precioustime.SecondActivity.DB.BUSEntity
import com.example.your_precioustime.ThridActivity.BusSubwayActivity
import com.example.your_precioustime.Util.Companion.TAG
import com.example.your_precioustime.databinding.BusitemLayoutBinding
import kotlinx.android.synthetic.main.busitem_layout.view.*

@SuppressLint("StaticFieldLeak")
class UpAdpater:RecyclerView.Adapter<UpAdpater.MyViewHolder>() {
    private var item: List<Item>? = null
    lateinit var busEntity: List<BUSEntity>
    lateinit var busDataBase: BUSDataBase

    class MyViewHolder(val binding:BusitemLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Item){
            binding.BusNumber.text =item.routeno.toString()
            binding.waitBusNumber.text = item.arrprevstationcnt.toString()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = BusitemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        item?.get(position)?.let {
            holder.bind(it)
        }

//        holder.itemView.SaveBtn.setOnClickListener {
//
//            val busnumber = item?.get(position)?.routeno.toString()
//            val arriveStaion = item?.get(position)?.arrprevstationcnt.toString()
//
//
//            val bushi = BUSEntity(
//                null,
//                busnumber,
//                arriveStaion,
//                null
//            )
//
//            Toast.makeText(holder.itemView.context,"저장되었습니다. $bushi",Toast.LENGTH_SHORT).show()
//            businsert(bushi)
//        }



    }



    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list:List<Item>){
        item = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }





    private fun businsert(busEntity: BUSEntity){

        busDataBase = BUSDataBase.getinstance(App.instance)!!

        var businsertTask = (object : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                busDataBase.busDAO().businsert(busEntity)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                busGetAll()
            }
        }).execute()

    }

    private fun busGetAll(){
        val busGetAllTask = (object:AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                busEntity=busDataBase.busDAO().busgetAll()
            }
        }).execute()
    }





}