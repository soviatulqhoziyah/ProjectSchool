package com.soviatul.projectschool

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.soviatul.projectschool.adapter.sekolahAdapter
import com.soviatul.projectschool.api.ApiClient
import com.soviatul.projectschool.models.SekolahResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {
    private lateinit var svSekolah: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var rvSekolah: RecyclerView
    private lateinit var floatbtnTambah: FloatingActionButton
    private lateinit var sekolahAdapter: sekolahAdapter
    private lateinit var imgNotFound: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        svSekolah = findViewById(R.id.svSekolah)
        progressBar = findViewById(R.id.progressBar)
        rvSekolah = findViewById(R.id.rvBerita)
        floatbtnTambah = findViewById(R.id.floatBtnTambah)
        imgNotFound = findViewById(R.id.imgNotFound)

        getSekolah("")

        svSekolah.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(pO: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(pencarian: String?): Boolean {
                getSekolah(pencarian.toString())
                return true
            }
        })


    }

    private fun getSekolah(nama_sekolah: String){
        progressBar.visibility = View.VISIBLE
        ApiClient.apiService.getListSekolah(nama_sekolah).enqueue(object: Callback<SekolahResponse> {
            override fun onResponse(
                call: Call<SekolahResponse>,
                response: Response<SekolahResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.success){
                        //set data
                        sekolahAdapter= sekolahAdapter(arrayListOf())
                        rvSekolah.adapter = sekolahAdapter
                        sekolahAdapter.setData(response.body()!!.data)

                        // Sembunyikan gambar error
                        imgNotFound.visibility = View.GONE
                    }else{
                        //jika data tidak ditemukan
                        sekolahAdapter= sekolahAdapter(arrayListOf())
                        rvSekolah.adapter = sekolahAdapter
                        imgNotFound.visibility= View.VISIBLE

                    }
                }
                progressBar.visibility= View.GONE
            }

            override fun onFailure(call: Call<SekolahResponse>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Error : ${t.message}", Toast.LENGTH_LONG)
                progressBar.visibility = View.GONE
            }
        })
        floatbtnTambah.setOnClickListener {
            startActivity(Intent(this@DashboardActivity,TambahSekolahActivity::class.java))
        }
    }
}