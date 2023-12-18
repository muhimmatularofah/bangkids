package com.example.sellsmartly.data.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.sellsmartly.data.response.AddMenuResponse
import com.example.sellsmartly.data.response.ListMenuItem
import com.example.sellsmartly.data.retrofit.ApiService
import com.example.sellsmartly.data.Result
import com.example.sellsmartly.data.paging.MenuPagingSource
import java.io.File

class MenuRepository private constructor(
    private val apiService: ApiService,
) {

    fun getMenu(token: String): LiveData<PagingData<ListMenuItem>> {

        return Pager(
            config = PagingConfig(pageSize = 10),

            pagingSourceFactory = {
                MenuPagingSource(token, apiService)
            }
        ).liveData
    }

    fun postMenu(token: String, imageFile: File, description: String): LiveData<Result<AddMenuResponse>> = liveData {
        emit(Result.Loading)

    }

    companion object {
        @Volatile
        private var instance: MenuRepository? = null

        fun getInstance(
            apiService: ApiService,
        ): MenuRepository =
            instance ?: synchronized(this) {
                instance ?: MenuRepository(apiService)
            }.also { instance = it }
    }
}
