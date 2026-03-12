package com.example.mobile_smart_pantry_project_iv

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

        loadProductsFromJsonFile()

        adapter = PantryAdapter(this, productList){
            saveProductsFromJsonFile()
        }
        binding.productListView.adapter = adapter


        binding.productListView.setOnItemClickListener { parent, view, position, id ->
            val clickedItem = adapter.getItem(position)
            Log.d("LIST_CLICK", "Kliknięto: $clickedItem")
        }




        binding.searchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")

                return true
            }
        })

        binding.btnAll.setOnClickListener {
            adapter.filter("")
        }

        binding.btnFood.setOnClickListener {
            adapter.filterByCategory("Food")
        }
        binding.btnTools.setOnClickListener {
            adapter.filterByCategory("Tools")
        }

    }

     fun saveProductsFromJsonFile(){
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
                val inputStream = resources.openRawResource(R.raw.pantry)
                val jsonString = inputStream.bufferedReader().use { it.readText() }

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