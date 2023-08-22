package com.minhoi.pethotel.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.minhoi.pethotel.R
import com.minhoi.pethotel.adapter.HotelRegistrationImagesAdapter
import com.minhoi.pethotel.data.model.CreateHotelRequest
import com.minhoi.pethotel.databinding.ActivityHotelRegistrationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.InputStream


class HotelRegistrationActivity : AppCompatActivity() {
    private val images = arrayListOf<Uri>()
    private val imageParts = mutableListOf<MultipartBody.Part>()
    private lateinit var imageAdapter : HotelRegistrationImagesAdapter
    private lateinit var binding: ActivityHotelRegistrationBinding
    private lateinit var viewModel: HotelRegistrationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_registration)
        viewModel = ViewModelProvider(this).get(HotelRegistrationViewModel::class.java)

        imageAdapter = HotelRegistrationImagesAdapter(this) {
            //onDeleteClickListener
            images.removeAt(it)
            imageAdapter.setList(images)
        }

        binding.back.setOnClickListener {
            finish()
        }

        binding.imageListRv.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }


        binding.inputHotelImages.setOnClickListener {
            val readPermission = ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )

            if (readPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    200
                )
            } else {
                openGallery()
            }
        }

        binding.hotelRegisterBtn.setOnClickListener {
            val name = binding.inputHotelName.text.toString()
            val bs = binding.inputBsNum.text.toString()
            val des = binding.inputHotelDescription.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.registrationHotel(CreateHotelRequest(name, bs, des), imageParts)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 100) {
            images.clear()

            if (data?.clipData != null) {
                val count = data?.clipData!!.itemCount

                if (count > 10) {
                    Toast.makeText(applicationContext, "사진은 최대 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                        .show()
                    return
                }

                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    images.add(imageUri)
//                    Log.d(TAG, "onActivityResult: ${images[i]}\n")
                }
            } else {
                data?.data?.let { uri ->
                    val imageUri: Uri? = data?.data

                    if (imageUri != null) {
                        images.add(imageUri)
                    }
                }
            }
            // 불러온 이미지 Uri
            imageAdapter.setList(images)
            imagesToFile(images)
        }
    }

    private fun imagesToFile(images: List<Uri>) {

        for (imageUri in images) {
            val file = convertUriToFile(imageUri)
            val requestFile = RequestBody.create("image/*".toMediaType(), file)
            val imagePart = MultipartBody.Part.createFormData("imageFiles", file.name, requestFile)
            imageParts.add(imagePart)
        }
    }

    fun convertUriToFile(uri: Uri): File {
        val contentResolver = applicationContext.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(cacheDir, "image_${System.currentTimeMillis()}.jpg") // 파일명을 지정하세요
        if (inputStream != null) {
            file.copyInputStreamToFile(inputStream)
        }
        return file
    }

    fun File.copyInputStreamToFile(inputStream: InputStream) {
        this.outputStream().use { fileOut ->
            inputStream.copyTo(fileOut)
        }
    }

//    private fun compressImageAndUpload(images: List<Uri>) {
//        for (imageUri in images) {
//            val compressedBitmap = compressImage(imageUri)
//            val compressedFile = createTempImageFile(compressedBitmap)
//            val requestFile = RequestBody.create("image/*".toMediaType(), compressedFile)
//            val imagePart = MultipartBody.Part.createFormData("imageFiles", compressedFile.name, requestFile)
//            imageParts.add(imagePart)
//        }
//    }

//    private fun createTempImageFile(bitmap: Bitmap): File {
//        val tempFile = File(cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
//        tempFile.writeBytes(byteArrayOutputStream.toByteArray())
//        return tempFile
//    }

//    private fun compressImage(uri: Uri): Bitmap {
//        val options = BitmapFactory.Options()
//        options.inJustDecodeBounds = true // 이미지 크기만 읽기 위해 비트맵을 실제로 로드하지 않음
//        BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options)
//
//        val targetWidth = 800 // 목표 너비 (압축된 이미지의 너비)
//        val scaleFactor = options.outWidth.toFloat() / targetWidth
//
//        options.inJustDecodeBounds = false
//        options.inSampleSize =
//            calculateInSampleSize(options, targetWidth, (targetWidth * scaleFactor).toInt())
//
//        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options)
//        return bitmap!!
//    }
//
//    // 이미지 샘플 크기 계산
//    private fun calculateInSampleSize(
//        options: BitmapFactory.Options,
//        reqWidth: Int,
//        reqHeight: Int
//    ): Int {
//        val height = options.outHeight
//        val width = options.outWidth
//        var inSampleSize = 1
//
//        if (height > reqHeight || width > reqWidth) {
//            val heightRatio = (height.toFloat() / reqHeight.toFloat()).toInt()
//            val widthRatio = (width.toFloat() / reqWidth.toFloat()).toInt()
//
//            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
//        }
//        return inSampleSize
//    }
}