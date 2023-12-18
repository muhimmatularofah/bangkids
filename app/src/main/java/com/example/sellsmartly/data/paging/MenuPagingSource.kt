package com.example.sellsmartly.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sellsmartly.data.response.ListMenuItem
import com.example.sellsmartly.data.retrofit.ApiService

class MenuPagingSource (private val token: String, private val apiService: ApiService) : PagingSource<Int, ListMenuItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListMenuItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMenu("$token", page, params.loadSize)

            LoadResult.Page(
                data = responseData.listMenu,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listMenu.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListMenuItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}