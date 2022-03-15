package com.olsera.test.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.olsera.test.entity.CompanyEntity
import com.olsera.test.utils.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CompanyRepository {
    companion object {
        var appDatabase: AppDatabase? = null

        var companyLiveData: LiveData<List<CompanyEntity>>? = null
        var companyLiveData2: LiveData<CompanyEntity>? = null

        fun initializeDB(context: Context) : AppDatabase {
            return AppDatabase.getDatabaseClient(context)
        }

        fun getAllCompany(context: Context): LiveData<List<CompanyEntity>>? {
            appDatabase = initializeDB(context)
            companyLiveData = appDatabase!!.companyDao().getAllCompany()
            return companyLiveData
        }

        fun getActiveCompany(context: Context): LiveData<List<CompanyEntity>>? {
            appDatabase = initializeDB(context)
            companyLiveData = appDatabase!!.companyDao().getActiveCompany()
            return companyLiveData
        }

        fun getInactiveCompany(context: Context): LiveData<List<CompanyEntity>>? {
            appDatabase = initializeDB(context)
            companyLiveData = appDatabase!!.companyDao().getInactiveCompany()
            return companyLiveData
        }

        fun getCompanyById(context: Context, id: Int): LiveData<CompanyEntity>? {
            appDatabase = initializeDB(context)
            companyLiveData2 = appDatabase!!.companyDao().getCompanyById(id)
            return companyLiveData2
        }

        fun insertCompany(context: Context, name: String, address: String?, city: String?, zipCode: String?, status: Boolean, longitude: String?, latitude: String?) {
            appDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                val data = CompanyEntity(name, address, city, zipCode, status, longitude, latitude)
                appDatabase!!.companyDao().insertCompany(data)
            }
        }

        fun updateCompany(context: Context, company:CompanyEntity) {
            appDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                appDatabase!!.companyDao().updateCompany(company)
            }
        }

        fun deleteCompany(context: Context, company:CompanyEntity) {
            appDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                appDatabase!!.companyDao().deleteCompany(company)
            }
        }
    }
}