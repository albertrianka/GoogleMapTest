package com.olsera.test.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.olsera.test.entity.CompanyEntity
import com.olsera.test.repository.CompanyRepository

class CompanyViewModel : ViewModel() {
    var companyLiveData: LiveData<List<CompanyEntity>>? = null
    var companyLiveData2: LiveData<CompanyEntity>? = null
    var companyStatus: Boolean = true
    var companyId: Int = -1
    var companyLongitude: Double = 0.0
    var companyLatitude: Double = 0.0

    fun insertCompany(context: Context, name: String, address: String?, city: String?, zipCode: String?, status: Boolean, longitude: String?, latitude: String?) {
        CompanyRepository.insertCompany(context, name, address, city, zipCode, status, longitude, latitude)
    }

    fun updateCompany(context: Context, company: CompanyEntity) {
        CompanyRepository.updateCompany(context, company)
    }

    fun deleteCompany(context: Context, company: CompanyEntity) {
        CompanyRepository.deleteCompany(context, company)
    }

    fun getAllCompany(context: Context): LiveData<List<CompanyEntity>>? {
        companyLiveData = CompanyRepository.getAllCompany(context)
        return companyLiveData
    }

    fun getActiveCompany(context: Context): LiveData<List<CompanyEntity>>? {
        companyLiveData = CompanyRepository.getActiveCompany(context)
        return companyLiveData
    }

    fun getInactiveCompany(context: Context): LiveData<List<CompanyEntity>>? {
        companyLiveData = CompanyRepository.getInactiveCompany(context)
        return companyLiveData
    }

    fun getCompanyById(context: Context, id: Int): LiveData<CompanyEntity>? {
        companyLiveData2 = CompanyRepository.getCompanyById(context, id)
        return companyLiveData2
    }
}