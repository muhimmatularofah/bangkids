package com.example.MyStoryApp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sellsmartly.data.response.ListMenuItem
import com.example.sellsmartly.databinding.ItemMenuBinding

class MenuAdapter(private val onItemClickCallback: OnItemClickCallBack):
    PagingDataAdapter<ListMenuItem, MenuAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private var onItemCallback:OnItemClickCallBack?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null){
            holder.bind(story)
            onItemCallback?.let { callback ->

            }
        }
    }

    inner class MyViewHolder(val binding: ItemMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: ListMenuItem) {
            with(binding) {
                tvName.text = menu.name
                tvPrice.text = menu.price.toString()

                root.setOnClickListener{
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        onItemClickCallback.onItemClicked(menu)
                    }
                }
            }
        }
    }

    interface OnItemClickCallBack{
        fun onItemClicked(data: ListMenuItem)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListMenuItem>(){
            override fun areItemsTheSame(oldItem: ListMenuItem, newItem: ListMenuItem): Boolean {
                return  oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListMenuItem,
                newItem: ListMenuItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}