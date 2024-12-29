package com.soviatul.projectschool

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class DetailSekolahActivity : AppCompatActivity() {
    private lateinit var imgSekolah: ImageView
    private lateinit var tvSekolah: TextView
    private lateinit var tvInformasi: TextView
    private lateinit var tvNoTelpon: TextView
    private lateinit var tvAkreditasi: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_sekolah)

        imgSekolah= findViewById(R.id.imgSekolah)
        tvSekolah= findViewById(R.id.tvSekolah)
        tvInformasi= findViewById(R.id.tvInformasi)
        tvNoTelpon=findViewById(R.id.tvNoTelpon)
        tvAkreditasi=findViewById(R.id.tvAkreditasi)

        Picasso.get().load(intent.getStringExtra("gambar_sekolah")).into(imgSekolah)
        tvSekolah.text= intent.getStringExtra("nama_sekolah")
        tvInformasi.text= intent.getStringExtra("informasi")
        tvNoTelpon.text= intent.getStringExtra("no_tlpn")
        tvAkreditasi.text= intent.getStringExtra("akreditasi")
    }
}