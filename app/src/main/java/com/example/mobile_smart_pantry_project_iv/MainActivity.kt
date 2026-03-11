package com.example.mobile_smart_pantry_project_iv

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityMainBinding
import com.example.mobile_smart_pantry_project_iv.models.Product

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PantryAdapter


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

        val productList = listOf(
            Product(1, "Bułka", 5, "Food", "bulka"),
            Product(2, "Młotek", 2, "Tools", "mlotek"),
            Product(3, "Zegarek", 1, "Life-Support", "zegarek")
        )

        adapter = PantryAdapter(this, productList)
        binding.productListView.adapter = adapter


        binding.productListView.setOnItemClickListener { parent, view, position, id ->
            val clickedItem = adapter.getItem(position)
            Log.d("LIST_CLICK", "Kliknięto: $clickedItem")
        }
    }
}