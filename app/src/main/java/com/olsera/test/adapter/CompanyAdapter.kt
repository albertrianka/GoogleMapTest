package com.olsera.test.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olsera.test.databinding.RowCompanyBinding
import com.olsera.test.entity.CompanyEntity

class CompanyAdapter(private val companyList: List<CompanyEntity>, private val listener: OnCompanyClickListener): RecyclerView.Adapter<CompanyAdapter.CompanyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyHolder {
        val binding = RowCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyHolder, position: Int) {
        val paymentBean: CompanyEntity = companyList[position]
        holder.bind(paymentBean, position, listener)
    }

    override fun getItemCount(): Int = companyList.size

    class CompanyHolder(private val binding: RowCompanyBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(company: CompanyEntity, position: Int, listener: OnCompanyClickListener) {
            binding.tvCompanyName.text = company.name

            if (position % 2 == 0) binding.cvCompany.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
            else binding.cvCompany.setBackgroundColor(Color.parseColor("#19000000"))

            if (company.status) {
                binding.tvInactive.visibility = View.GONE
                binding.tvCompanyDesc.visibility = View.VISIBLE
                binding.tvCompanyName.setTextColor(Color.parseColor("#FF000000"))
                binding.viewInactive.visibility = View.GONE
            }
            else {
                binding.tvInactive.visibility = View.VISIBLE
                binding.tvCompanyDesc.visibility = View.GONE
                binding.tvCompanyName.setTextColor(Color.parseColor("#4D000000"))
                binding.viewInactive.visibility = View.VISIBLE
            }

            binding.cvCompany.setOnClickListener { listener.onCompanyClick(company, position) }
        }
    }

    interface OnCompanyClickListener {
        fun onCompanyClick(company: CompanyEntity, position: Int)
    }
}