package com.example.mobile_smart_pantry_project_iv

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mobile_smart_pantry_project_iv.databinding.ItemProductBinding
import com.example.mobile_smart_pantry_project_iv.models.Product

class PantryAdapter(
    private val context: Context,
    private val products: List<Product>,
    private val onQuantityChanged: () -> Unit
) : ArrayAdapter<Product>(context, 0, products) {

    private var filteredProducts = products.toMutableList()

    override fun getCount(): Int {
        return filteredProducts.size
    }

    override fun getItem(position: Int): Product {
        return filteredProducts[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding: ItemProductBinding

        if (convertView == null) {
            binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            binding = ItemProductBinding.bind(convertView)
        }

        val product = filteredProducts[position]

        binding.productName.text = product.name
        binding.productCategory.text = product.category
        binding.productUUID.text = product.uuid.toString()
        binding.productQuantity.text = product.quantity.toString()

        when(product.imageRef) {
            "bulka" -> binding.productImage.setImageResource(R.drawable.bulka)
            "mlotek" -> binding.productImage.setImageResource(R.drawable.mlotek)
            "zegarek" -> binding.productImage.setImageResource(R.drawable.zegarek)
            else -> binding.productImage.setImageResource(R.drawable.placeholder)
        }

        if (product.quantity <= 0) {
            binding.removeButton.isEnabled = false
        }

        binding.addButton.setOnClickListener {
            product.quantity++
            notifyDataSetChanged()
            onQuantityChanged()
        }

        binding.removeButton.setOnClickListener {

            if (product.quantity > 0) {
                product.quantity--
                notifyDataSetChanged()
            }

            onQuantityChanged()
        }

        if (product.quantity < 3) {
            binding.root.setBackgroundColor(Color.RED)
        } else {
            binding.root.setBackgroundColor(Color.WHITE)
        }

        return binding.root
    }

    fun filter(text: String) {

        filteredProducts.clear()

        if (text.isEmpty()) {
            filteredProducts.addAll(products)
        } else {
            val search = text.lowercase()

            for (product in products) {
                if (product.name.lowercase().contains(search)) {
                    filteredProducts.add(product)
                }
            }
        }

        notifyDataSetChanged()
    }
}