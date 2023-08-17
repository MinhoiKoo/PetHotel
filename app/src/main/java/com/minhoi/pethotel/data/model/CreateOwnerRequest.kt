package com.minhoi.pethotel.data.model

data class CreateOwnerRequest(
    private val name : String,
    private val email : String,
    private val pw : String,
    private val phonenum : String,
    private val identity : String
)