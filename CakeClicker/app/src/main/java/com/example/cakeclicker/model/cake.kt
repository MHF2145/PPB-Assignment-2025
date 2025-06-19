package com.example.cakeclicker.model

data class Cake(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val price: Int,
    val startProductionAmount: Int
)