package com.soviatul.projectschool.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soviatul.projectschool.DetailSekolahActivity
import com.soviatul.projectschool.R
import com.soviatul.projectschool.models.SekolahResponse
import com.squareup.picasso.Picasso

class sekolahAdapter (
    val dataSekolah: ArrayList<SekolahResponse.ListItems>

): RecyclerView.Adapter<sekolahAdapter.ViewHolder>() {
    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        //inisiallisasi widget
        val imgSekolah = view.findViewById<ImageView>(R.id.imgSekolah)
        val tvSekolah = view.findViewById<TextView>(R.id.tvSekolah)
        val tvNoTelpon = view.findViewById<TextView>(R.id.tvNoTelpon)
        val tvAkreditasi = view.findViewById<TextView>(R.id.tvAkreditasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSekolah.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //tampilkan data//
        val hasilResponse = dataSekolah[position]
        Picasso.get().load(hasilResponse.gambar_sekolah).into(holder.imgSekolah)
        holder.tvSekolah.text = hasilResponse.nama_sekolah
        holder.tvNoTelpon.text = hasilResponse.no_tlpn
        holder.tvAkreditasi.text = hasilResponse.akreditasi

        //klik item sekolah
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailSekolahActivity::class.java).apply {
                putExtra("gambar_sekolah", hasilResponse.gambar_sekolah)
                putExtra("nama_sekolah", hasilResponse.nama_sekolah)
                putExtra("no_tlpn", hasilResponse.no_tlpn)
                putExtra("akreditasi", hasilResponse.akreditasi)
                putExtra("informasi", hasilResponse.informasi)

            }
            holder.imgSekolah.context.startActivity(intent)
        }

    }
    fun setData(data: List<SekolahResponse.ListItems>){
        dataSekolah.clear()
        dataSekolah.addAll(data)
    }


}