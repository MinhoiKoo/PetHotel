package com.minhoi.pethotel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhoi.pethotel.R
import com.minhoi.pethotel.adapter.HotelListAdapter
import com.minhoi.pethotel.data.MyApi
import com.minhoi.pethotel.data.RetrofitInstance
import com.minhoi.pethotel.databinding.ActivityHotelListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHotelListBinding
    private lateinit var hotelListAdapter : HotelListAdapter
    private val client = RetrofitInstance.getInstanceWithoutToken().create(MyApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_list)


        hotelListAdapter = HotelListAdapter() { id ->
            //onClickListener
            val intent = Intent(this, HotelInfoActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        lifecycleScope.launch(Dispatchers.Main) {
            val response = client.getHotelList()
            if(response.isSuccessful) {
                val list = response.body()?: emptyList()
                hotelListAdapter.setList(list)
            }
        }

        binding.rv.apply {
            adapter = hotelListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}