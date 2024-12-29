package com.soviatul.projectschool.models

data class SekolahResponse(
    val success : Boolean,
    val message: String,
    val data : ArrayList<ListItems>
) {
    data class ListItems(
        val id: String,
        val nama_sekolah: String,
        val informasi: String,
        val gambar_sekolah: String,
        val no_tlpn: String,
        val akreditasi: String,
    )
}

