package com.olsera.test.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.olsera.test.R
import com.olsera.test.adapter.CompanyAdapter
import com.olsera.test.databinding.FragmentCompanyBinding
import com.olsera.test.entity.CompanyEntity
import com.olsera.test.viewmodel.CompanyViewModel

class CompanyFragment : Fragment() {

    private var _binding: FragmentCompanyBinding? = null
    private val binding get() = _binding!!

    val companyViewModel: CompanyViewModel by viewModels()
    lateinit var companyAdapter: CompanyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCompany.layoutManager = LinearLayoutManager(context)

        binding.tvAllStatus.setOnClickListener {
            binding.tvAllStatus.setTextColor(Color.parseColor("#00d79e"))
            binding.tvAllStatus.setBackgroundColor(Color.parseColor("#2500D79E"))

            binding.tvActive.setTextColor(Color.parseColor("#80000000"))
            binding.tvActive.setBackgroundColor(Color.parseColor("#0D000000"))

            binding.tvInactive.setTextColor(Color.parseColor("#80000000"))
            binding.tvInactive.setBackgroundColor(Color.parseColor("#0D000000"))

            companyViewModel.getAllCompany(requireContext())!!.observe(viewLifecycleOwner) {
                if (it != null) {
                    companyAdapter = CompanyAdapter(it, object : CompanyAdapter.OnCompanyClickListener {
                        override fun onCompanyClick(company: CompanyEntity, position: Int) {
                            val b = Bundle()
                            b.putInt("idCompany", company.id!!)
                            findNavController().navigate(R.id.action_CompanyFragment_to_AddCompanyFragment, b)
                        }
                    })
                    binding.rvCompany.adapter = companyAdapter
                }
            }
        }

        binding.tvActive.setOnClickListener {
            binding.tvAllStatus.setTextColor(Color.parseColor("#80000000"))
            binding.tvAllStatus.setBackgroundColor(Color.parseColor("#0D000000"))

            binding.tvActive.setTextColor(Color.parseColor("#00d79e"))
            binding.tvActive.setBackgroundColor(Color.parseColor("#2500D79E"))

            binding.tvInactive.setTextColor(Color.parseColor("#80000000"))
            binding.tvInactive.setBackgroundColor(Color.parseColor("#0D000000"))

            companyViewModel.getActiveCompany(requireContext())!!.observe(viewLifecycleOwner) {
                if (it != null) {
                    companyAdapter = CompanyAdapter(it, object : CompanyAdapter.OnCompanyClickListener {
                        override fun onCompanyClick(company: CompanyEntity, position: Int) {
                            val b = Bundle()
                            b.putInt("idCompany", company.id!!)
                            findNavController().navigate(R.id.action_CompanyFragment_to_AddCompanyFragment, b)
                        }
                    })
                    binding.rvCompany.adapter = companyAdapter
                }
            }
        }

        binding.tvInactive.setOnClickListener {
            binding.tvAllStatus.setTextColor(Color.parseColor("#80000000"))
            binding.tvAllStatus.setBackgroundColor(Color.parseColor("#0D000000"))

            binding.tvActive.setTextColor(Color.parseColor("#80000000"))
            binding.tvActive.setBackgroundColor(Color.parseColor("#0D000000"))

            binding.tvInactive.setTextColor(Color.parseColor("#00d79e"))
            binding.tvInactive.setBackgroundColor(Color.parseColor("#2500D79E"))

            companyViewModel.getInactiveCompany(requireContext())!!.observe(viewLifecycleOwner) {
                if (it != null) {
                    companyAdapter = CompanyAdapter(it, object : CompanyAdapter.OnCompanyClickListener {
                        override fun onCompanyClick(company: CompanyEntity, position: Int) {
                            val b = Bundle()
                            b.putInt("idCompany", company.id!!)
                            findNavController().navigate(R.id.action_CompanyFragment_to_AddCompanyFragment, b)
                        }
                    })
                    binding.rvCompany.adapter = companyAdapter
                }
            }
        }

        binding.fabAddLocation.setOnClickListener {
            findNavController().navigate(R.id.action_CompanyFragment_to_AddCompanyFragment)
        }

        companyViewModel.getAllCompany(requireContext())!!.observe(viewLifecycleOwner) {
            if (it != null) {
                companyAdapter = CompanyAdapter(it, object : CompanyAdapter.OnCompanyClickListener {
                    override fun onCompanyClick(company: CompanyEntity, position: Int) {
                        val b = Bundle()
                        b.putInt("idCompany", company.id!!)
                        findNavController().navigate(R.id.action_CompanyFragment_to_AddCompanyFragment, b)
                    }
                })
                binding.rvCompany.adapter = companyAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()

        companyViewModel.getAllCompany(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}