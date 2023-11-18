package com.example.gipolanfinalprojectpwr

data class Plant(val name: String, val wateringInterval: Int) {
    // Rename the method
    fun getPlantName(): String {
        return name
    }
}


