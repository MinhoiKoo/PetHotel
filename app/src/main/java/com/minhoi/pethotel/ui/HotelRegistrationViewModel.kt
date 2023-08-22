package com.minhoi.pethotel.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.minhoi.pethotel.MainApplication
import com.minhoi.pethotel.data.MyApi
import com.minhoi.pethotel.data.RetrofitInstance
import com.minhoi.pethotel.data.model.CreateHotelRequest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.logging.Level.parse

class HotelRegistrationViewModel : ViewModel() {
    private val _hotelName = MutableLiveData<String>()
    private val _hotelbsNum = MutableLiveData<String>()
    private val _hotelDescription = MutableLiveData<String>()

    val hotelName: LiveData<String> = _hotelName
    val hotelbsNum: LiveData<String> = _hotelbsNum
    val hotelDescription: LiveData<String> = _hotelDescription



    suspend fun registrationHotel(request : CreateHotelRequest, images : List<MultipartBody.Part>) {
        val client = RetrofitInstance.clientWithToken(MainApplication.prefs.getToken("ownerToken", "")).create(MyApi::class.java)

        val response = client.registerHotel(request.name, request.bsNum, request.description, images)
        if(response.isSuccessful) {
            Log.d("viewModel", "${response.body()}")
        } else {
            Log.d("viewModel", "error : ${response.errorBody()}")
        }

    }
}