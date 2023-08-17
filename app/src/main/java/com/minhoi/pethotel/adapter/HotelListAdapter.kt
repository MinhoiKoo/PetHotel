package com.minhoi.pethotel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.minhoi.pethotel.R
import com.minhoi.pethotel.data.model.HotelDto
import com.minhoi.pethotel.databinding.HotelListItemBinding

class HotelListAdapter(private val onClickListener : (Long) -> Unit) : RecyclerView.Adapter<HotelListAdapter.ViewHolder>() {
    private val hotelList = arrayListOf<HotelDto>()

    inner class ViewHolder(binding : HotelListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.hotelName

        fun bind(items : HotelDto ) {
            itemView.setOnClickListener {
                onClickListener(hotelList[adapterPosition].id)
            }
            name.text = items.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<HotelListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.hotel_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hotelList[position])
    }

    fun setList(items: List<HotelDto>) {
        hotelList.clear()
        hotelList.addAll(items)
        notifyDataSetChanged()
    }
}