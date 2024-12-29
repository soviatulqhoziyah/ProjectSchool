package com.soviatul.projectschool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.soviatul.projectschool.api.ApiClient
import com.soviatul.projectschool.models.TambahSekolahResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TambahSekolahActivity : AppCompatActivity() {
    private lateinit var etSekolah: EditText
    private lateinit var etInformasi: EditText
    private lateinit var btnGambar: Button
    private lateinit var etNotlpn: EditText
    private lateinit var etAkreditasi: EditText
    private lateinit var btnTambah: Button
    private lateinit var imgGambar: ImageView
    private lateinit var progressBar: ProgressBar
    private var imageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_sekolah)

        btnGambar = findViewById(R.id.btnGambar)
        imgGambar = findViewById(R.id.imgGambar)
        etInformasi = findViewById(R.id.etInformasi)
        btnTambah = findViewById(R.id.btnTambah)
        etSekolah = findViewById(R.id.etSekolah)
        etNotlpn = findViewById(R.id.etNotlpn)
        etAkreditasi = findViewById(R.id.etAkreditasi)
        progressBar = findViewById(R.id.progressBar)


        btnGambar.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        btnTambah.setOnClickListener {
            imageFile?.let {
                    file ->
                addSekolah(etSekolah.text.toString(), etInformasi.text.toString(),  file, etNotlpn.text.toString(), etAkreditasi.text.toString())
            }
        }

    }

    //menampilkan gambar
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data!!
            imageFile = File(uri.path!!)
            imgGambar.visibility = View.VISIBLE
            imgGambar.setImageURI(uri)

        }
    }


    //proses tambah berita
    private fun addSekolah(nama_sekolah: String, informasi: String, gambar_sekolah: File, no_tlpn: String, akreditasi: String) {
        progressBar.visibility = View.VISIBLE
        val requestBody = gambar_sekolah.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val gambar_sekolah =
            MultipartBody.Part.createFormData("gambar_sekolah", gambar_sekolah.name, requestBody)
        val nama_sekolah = nama_sekolah.toRequestBody("text/plain".toMediaTypeOrNull())
        val informasi = informasi.toRequestBody("text/plain".toMediaTypeOrNull())
        val no_tlpn = no_tlpn.toRequestBody("text/plain".toMediaTypeOrNull())
        val akreditasi = akreditasi.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.apiService.addSekolah(nama_sekolah,informasi,gambar_sekolah, no_tlpn, akreditasi)
            .enqueue(object : Callback<TambahSekolahResponse> {
                override fun onResponse(
                    call: Call<TambahSekolahResponse>,
                    response: Response<TambahSekolahResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            startActivity(
                                Intent(
                                    this@TambahSekolahActivity,
                                    DashboardActivity::class.java
                                )
                            )

                        } else {
                            Toast.makeText(
                                this@TambahSekolahActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } else {
                        Toast.makeText(
                            this@TambahSekolahActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<TambahSekolahResponse>, t: Throwable) {
                    Toast.makeText(
                        this@TambahSekolahActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.GONE
                }
            })
    }
}