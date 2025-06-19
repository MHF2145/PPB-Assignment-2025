package com.example.scrollimage.Data

import com.example.scrollimage.R
import com.example.scrollimage.Model.Item

object Load {
    fun loadItems(): List<Item> {
        return listOf(
            Item(
                imageRes = R.drawable.dscf2925,
                title = "Jembatan Tempozan yang Megah",
                description = "Nikmati pemandangan spektakuler dari ketinggian melalui struktur modern Jembatan Tempozan. Dengan latar belakang langit senja yang mempesona, jembatan cable-stayed ini menawarkan vista panoramik yang tak terlupakan menuju pelabuhan Osaka. Spot fotografi terbaik untuk mengabadikan arsitektur kontemporer Jepang yang memukau."
            ),
            Item(
                imageRes = R.drawable.dscf2926,
                title = "Jam Antik di Pusat Kota",
                description = "Temukan pesona nostalgia di tengah hiruk-pikuk kota melalui jam klasik berukuran besar yang terpasang megah di area komersial. Jam bergaya vintage ini menjadi landmark unik yang mengingatkan pengunjung akan perjalanan waktu, dikelilingi oleh arsitektur tradisional Jepang yang autentik."
            ),
            Item(
                imageRes = R.drawable.dscf2927,
                title = "Distrik Perbelanjaan Dotonbori",
                description = "Jelajahi jantung kehidupan malam Osaka di kawasan Dotonbori yang legendaris. Deretan bangunan bertingkat dengan papan reklame warna-warni menciptakan atmosfer urban yang dinamis. Area ini merupakan surga kuliner dan belanja yang tidak pernah tidur, sempurna untuk merasakan energi kota metropolitan Jepang."
            ),
            Item(
                imageRes = R.drawable.dscf2928,
                title = "Pelabuhan Osaka di Saat Senja",
                description = "Saksikan keajaiban golden hour dari pelabuhan Osaka yang menawan. Panorama laut yang tenang berpadu dengan siluet gedung pencakar langit menciptakan pemandangan romantis yang memukau. Crane pelabuhan dan kapal-kapal yang berlabuh menambah nuansa industri maritim yang khas."
            ),
            Item(
                imageRes = R.drawable.dscf2929,
                title = "Momen Magis di Teluk Osaka",
                description = "Abadikan keindahan transisi siang menuju malam di Teluk Osaka. Permainan cahaya alami yang memancar dari langit senja menciptakan refleksi memesona di permukaan air. Pemandangan ini menawarkan kedamaian di tengah kesibukan kota pelabuhan yang tak pernah berhenti."
            ),
            Item(
                imageRes = R.drawable.dscf2930,
                title = "Kuliner Khas: Kani (Kepiting)",
                description = "Rasakan kelezatan kuliner autentik Osaka dengan hidangan kepiting segar yang terkenal. Kepiting merah yang menggoda ini merupakan speciality lokal yang wajib dicoba, biasanya disajikan dalam berbagai olahan tradisional Jepang. Nikmati cita rasa laut yang otentik di pusat kuliner Dotonbori."
            ),
            Item(
                imageRes = R.drawable.dscf2930,
                title = "Neon Dotonbori di Malam Hari",
                description = "Tenggelam dalam kemegahan lampu neon yang menyilaukan di kawasan entertainment Dotonbori yang ikonik. Papan reklame raksasa dengan karakter anime dan brand terkenal menciptakan spektakel visual yang tak ada duanya. Inilah wajah Osaka yang paling terkenal di dunia - jantung kehidupan malam yang penuh warna dan energi."
            )
        )
    }
}
