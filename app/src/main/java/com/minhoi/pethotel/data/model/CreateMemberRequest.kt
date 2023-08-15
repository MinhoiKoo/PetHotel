package com.minhoi.pethotel.data.model

data class CreateMemberRequest(
    private val name : String,
    private val email : String,
    private val pw : String
)
