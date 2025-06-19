package com.example.cakeclicker.data

import com.example.cakeclicker.R
import com.example.cakeclicker.model.Cake

object Source {
    val cakes = listOf(
        Cake(1, "Cupcake", R.drawable.cupcake, 5, 0),
        Cake(2, "Donut", R.drawable.donut, 10, 5),
        Cake(3, "Eclair", R.drawable.eclair, 15, 20),
        Cake(4, "Froyo", R.drawable.froyo, 30, 50),
        Cake(5, "Gingerbread", R.drawable.gingerbread, 50, 100),
        Cake(6, "Honeycomb", R.drawable.honeycomb, 100, 200),
        Cake(7, "Ice Cream Sandwich", R.drawable.icecreamsandwich, 200, 500),
        Cake(8, "Jelly Bean", R.drawable.jellybean, 500, 1000),
        Cake(9, "KitKat", R.drawable.kitkat, 1000, 2000),
        Cake(10, "Lollipop", R.drawable.lollipop, 2000, 5000)
    )
}