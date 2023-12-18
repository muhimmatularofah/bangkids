package com.example.sellsmartly.data.response

import com.google.gson.annotations.SerializedName

data class ErrorMenuResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)