package com.example.mobile_smart_pantry_project_iv

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityMainBinding
import com.example.mobile_smart_pantry_project_iv.models.Product
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PantryAdapter

    private var productList = mutableListOf<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = PantryAdapter(this, productList)
        binding.productListView.adapter = adapter


        binding.productListView.setOnItemClickListener { parent, view, position, id ->
            val clickedItem = adapter.getItem(position)
            Log.d("LIST_CLICK", "Kliknięto: $clickedItem")
        }


        productList.addAll(listOf(
            Product(1, "Bułka", 5, "Food", "bulka"),
            Product(2, "Młotek", 2, "Tools", "mlotek"),
            Product(3, "Zegarek", 1, "Life-Support", "zegarek")
        )
        )




        saveProductsFromJsonFile()
        loadProductsFromJsonFile()



    }

    private fun saveProductsFromJsonFile(){
        val json = Json {prettyPrint = true}
        val jsonString = json.encodeToString(productList)

        val file = File(filesDir, "pantry.json")

        file.writeText(jsonString)


    }


    private fun loadProductsFromJsonFile() {
        try {
            val file = File(filesDir, "pantry.json")
            if (file.exists()) {
                val jsonString = file.readText()
                val json = Json { ignoreUnknownKeys = true }
                val loadedList = json.decodeFromString<List<Product>>(jsonString)

                productList.clear()
                productList.addAll(loadedList)
                adapter.notifyDataSetChanged()
            }else {
                val startFile = File("res/raw/pantry.json")
                val jsonString = startFile.readText()
                val json = Json { ignoreUnknownKeys = true }
                val loadedList = json.decodeFromString<List<Product>>(jsonString)

                productList.clear()
                productList.addAll(loadedList)
                adapter.notifyDataSetChanged()
            }
        }catch(e: java.lang.Exception){
            Log.e("JSON_ERROR", "Błąd ładowania: ${e.message}")
        }

    }
}