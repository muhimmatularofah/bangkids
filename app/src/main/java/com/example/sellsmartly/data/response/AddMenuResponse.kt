package com.example.sellsmartly.data.response

import com.google.gson.annotations.SerializedName

data class AddMenuResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)