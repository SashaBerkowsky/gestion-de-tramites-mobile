package com.ort.gestiondetramitesmobile.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ort.gestiondetramitesmobile.R
import com.ort.gestiondetramitesmobile.models.ProcedureType

class ImageListAdapter(private var imageList: Array<String>,
                       var context : Context)
    : RecyclerView.Adapter<ImageListAdapter.ImageHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_image_item,parent,false)
        return(ImageHolder(view))
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        Glide.with(context).load(imageList[position]).centerInside().into(holder.getImgView())
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun getImgView(): ImageView{
            return view.findViewById(R.id.imgItem)
        }
    }
}