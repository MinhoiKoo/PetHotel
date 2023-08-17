package com.minhoi.pethotel.data.model

data class LoginResponse(
    val token : String,
    val name : String,
    val error : String
)
