package com.minhoi.pethotel.ui

import android.app.Application
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.minhoi.pethotel.R
import com.minhoi.pethotel.adapter.ImageAdapter
import com.minhoi.pethotel.databinding.ActivityImageChooseBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageChooseActivity : AppCompatActivity() {
    private lateinit var viewModel : ImageChooseViewModel
    private lateinit var binding : ActivityImageChooseBinding
    private val images = mutableListOf<Uri>()
    private lateinit var imageAdapter : ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ImageChooseViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_choose)

        imageAdapter = ImageAdapter(this) {

        }

        binding.imageRv.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(this@ImageChooseActivity, 3)
        }

        viewModel.getImages()

        viewModel.images.observe(this) {
            imageAdapter.setList(it)
        }

    }


}