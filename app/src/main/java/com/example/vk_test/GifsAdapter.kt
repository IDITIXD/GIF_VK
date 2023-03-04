//_________________GifsAdapter__________________
package com.example.vk_test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.http.GET

class GifsAdapter(val context: Context,val gifs: List<DataObject>) : RecyclerView.Adapter<GifsAdapter.ViewHolder>()
{
    lateinit var mListener: OnItemClickListener
    interface OnItemClickListener
    {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener)
    {
        mListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false), mListener)
    }

    override fun getItemCount(): Int
    {
        return  gifs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=gifs[position]
        Glide.with(context).load(data.images.ogImage.url).into(holder.imageView)
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView)
    {
        val imageView =itemView.findViewById<ImageView>(R.id.ivGif)
        init
        {
            itemView.setOnClickListener { listener.onItemClick(adapterPosition) }
        }

    }

}
