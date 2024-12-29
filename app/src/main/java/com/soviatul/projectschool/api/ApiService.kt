package com.soviatul.projectschool.api

import com.soviatul.projectschool.models.LoginResponse
import com.soviatul.projectschool.models.RegisterResponse
import com.soviatul.projectschool.models.SekolahResponse
import com.soviatul.projectschool.models.TambahSekolahResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("school/register.php")
    fun register(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("fullname") fullname:String,
        @Field("email") email:String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("school/login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @GET("school/get_sekolah.php")
    fun getListSekolah(@Query("nama_sekolah")nama_sekolah: String): Call<SekolahResponse>

    @Multipart
    @POST("school/add_sekolah.php")
    fun addSekolah(
        @Part("nama_sekolah") nama_sekolah: RequestBody,
        @Part("informasi") informasi: RequestBody,
        @Part gambar_sekolah: MultipartBody.Part,
        @Part("no_tlpn") no_tlpn: RequestBody,
        @Part("akreditasi") akreditasi: RequestBody,
    ): Call<TambahSekolahResponse>
}