package com.example.m3lesson6_recycleviewkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m3lesson6_recycleviewkotlin.databinding.ItemLocationBinding

class LocationAdapter
    (
    private val items: List<Location>,
    private val context: Context,
    private val onItemClick: (item: Location) -> Unit):
    RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter.ViewHolder {
        val binding = ItemLocationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: Location){
            binding.textViewName.text = item.name
            Glide.with(context).load(item.imageUrl).into(binding.imageView)
        }
    }
}