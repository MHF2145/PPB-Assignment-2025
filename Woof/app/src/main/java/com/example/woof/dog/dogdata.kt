package com.example.woof.dog

import com.example.woof.R

object DogData {
    data class Dog(
        val imageRes: Int,
        val about: String,
        val age: String,
        val condition: String
    )

    fun getDogList(): List<Dog> {
        return listOf(
            Dog(
                R.drawable.zen,
                "Zen adalah anjing ras besar yang sangat jinak dan penuh kasih.",
                "3 tahun",
                "Sehat dan aktif"
            ),
            Dog(
                R.drawable.chi,
                "Chi adalah Chihuahua kecil yang energik dan suka bermain.",
                "2 tahun",
                "Sedikit sensitif terhadap dingin"
            ),
            Dog(
                R.drawable.zan,
                "Zan adalah campuran Siberian Husky yang cerdas dan setia.",
                "4 tahun",
                "Sangat sehat"
            ),
            Dog(
                R.drawable.jack,
                "Jack adalah Jack Russell Terrier dengan semangat luar biasa.",
                "1.5 tahun",
                "Dalam masa pelatihan"
            ),
            Dog(
                R.drawable.shibainu,
                "Shiba Inu dengan sifat tenang, mandiri, dan penuh rasa ingin tahu.",
                "2.5 tahun",
                "Perlu sosialisasi lebih lanjut"
            )
        )
    }
}
