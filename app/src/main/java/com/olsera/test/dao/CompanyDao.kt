package com.olsera.test.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.olsera.test.entity.CompanyEntity

@Dao
interface CompanyDao {
    @Query("SELECT * FROM company")
    fun getAllCompany(): LiveData<List<CompanyEntity>>

    @Query("SELECT * FROM company WHERE company_status")
    fun getActiveCompany(): LiveData<List<CompanyEntity>>

    @Query("SELECT * FROM company WHERE NOT company_status")
    fun getInactiveCompany(): LiveData<List<CompanyEntity>>

    @Query("SELECT * FROM company WHERE id_company = :idCompany")
    fun getCompanyById(idCompany: Int): LiveData<CompanyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompany(company: CompanyEntity)

    @Update
    fun updateCompany(company: CompanyEntity)

    @Delete
    fun deleteCompany(company: CompanyEntity)
}