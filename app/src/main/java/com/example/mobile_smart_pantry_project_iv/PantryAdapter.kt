package com.example.mobile_smart_pantry_project_iv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mobile_smart_pantry_project_iv.databinding.ItemProductBinding
import com.example.mobile_smart_pantry_project_iv.models.Product

class PantryAdapter(
    private val context: Context,
    private val products: List<Product>
) : ArrayAdapter<Product>(context, 0, products) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding: ItemProductBinding

        if (convertView == null) {
            binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            binding = ItemProductBinding.bind(convertView)
        }

        val product = products[position]

        binding.productName.text = product.name
        binding.productCategory.text=product.category
        binding.productUUID.text=product.uuid.toString()
        binding.productQuantity.text=product.quantity.toString()

        when(product.imageRef) {
            "bulka" -> binding.productImage.setImageResource(R.drawable.bulka)
            "mlotek" -> binding.productImage.setImageResource(R.drawable.mlotek)
            "zegarek" -> binding.productImage.setImageResource(R.drawable.zegarek)
            else -> binding.productImage.setImageResource(R.drawable.placeholder)
        }


        return binding.root
    }
}