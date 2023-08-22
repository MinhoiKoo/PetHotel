package com.minhoi.pethotel.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minhoi.pethotel.R
import com.minhoi.pethotel.databinding.HotelRegistrationImagesBinding

class HotelRegistrationImagesAdapter(private val context : Context, private val onDeleteClickListener : (Int) -> Unit) : RecyclerView.Adapter<HotelRegistrationImagesAdapter.ViewHolder>() {

    val imageList = arrayListOf<Uri>()

    inner class ViewHolder(binding: HotelRegistrationImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.hotelRegImages
        private val deleteBtn = binding.imageDeleteBtn

        fun bind(imageUri : Uri) {
            deleteBtn.setOnClickListener {
                onDeleteClickListener(adapterPosition)
            }
            Glide.with(context)
                .load(imageUri)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DataBindingUtil.inflate<HotelRegistrationImagesBinding>(
            LayoutInflater.from(parent.context),
            R.layout.hotel_registration_images, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    fun setList(items : ArrayList<Uri>) {
        imageList.clear()
        imageList.addAll(items)
        notifyDataSetChanged()
    }

}