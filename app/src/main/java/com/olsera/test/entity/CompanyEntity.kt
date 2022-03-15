package com.olsera.test.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "company")
data class CompanyEntity (
    @ColumnInfo(name = "company_name")
    var name: String? = null,

    @ColumnInfo(name = "company_address")
    var address: String? = null,

    @ColumnInfo(name = "company_city")
    var city: String? = null,

    @ColumnInfo(name = "company_zip_code")
    var zipCode: String? = null,

    @ColumnInfo(name = "company_status")
    var status: Boolean = true,

    @ColumnInfo(name = "company_longitude")
    var longitude: String? = null,

    @ColumnInfo(name = "company_latitude")
    var latitude: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_company")
    var id: Int? = null
}
