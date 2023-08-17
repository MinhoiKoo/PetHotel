package com.minhoi.pethotel.data.model

data class CreateHotelRequest(
    private val name : String,
    private val bsNum : String,
    private val description : String,
    private val photos : List<ImageDto>
)
