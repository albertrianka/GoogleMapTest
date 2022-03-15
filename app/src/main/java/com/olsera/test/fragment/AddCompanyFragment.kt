package com.olsera.test.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.location.Address
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import com.adevinta.leku.*
import com.adevinta.leku.locale.SearchZoneRect
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.olsera.test.R
import com.olsera.test.databinding.FragmentAddCompanyBinding
import com.olsera.test.viewmodel.CompanyViewModel

class AddCompanyFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentAddCompanyBinding? = null
    private val binding get() = _binding!!

    val companyViewModel: CompanyViewModel by viewModels()
    private lateinit var gMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = AddCompanyFragmentArgs.fromBundle(requireArguments())
        companyViewModel.companyId = args.idCompany
        if (args.idCompany == -1) {
            binding.tvAddLocation.text = getString(R.string.add_location)
            binding.btnDelete.visibility = View.GONE
        }
        else {
            binding.tvAddLocation.text = getString(R.string.edit_location)
            binding.btnDelete.visibility = View.VISIBLE
            companyViewModel.getCompanyById(requireContext(), args.idCompany)

            companyViewModel.companyLiveData2!!.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.etName.setText(it.name)
                    binding.etAddress.setText(it.address)
                    binding.etCity.setText(it.city)
                    binding.etZipCode.setText(it.zipCode)
                    companyViewModel.companyStatus = it.status
                    companyViewModel.companyLongitude = it.longitude!!.toDouble()
                    companyViewModel.companyLatitude = it.latitude!!.toDouble()

                    val marker = LatLng(companyViewModel.companyLatitude, companyViewModel.companyLongitude)
                    gMap.addMarker(MarkerOptions()
                        .position(marker))
                    gMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
                }
            }
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.tvActive.setOnClickListener {
            companyViewModel.companyStatus = true
            binding.tvActive.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.tvActive.setBackgroundColor(Color.parseColor("#2881f5"))

            binding.tvInactive.setTextColor(Color.parseColor("#80000000"))
            binding.tvInactive.setBackgroundColor(Color.parseColor("#0D000000"))
        }

        binding.tvInactive.setOnClickListener {
            companyViewModel.companyStatus = false
            binding.tvInactive.setTextColor(Color.parseColor("#FFFFFFFF"))
            binding.tvInactive.setBackgroundColor(Color.parseColor("#2881f5"))

            binding.tvActive.setTextColor(Color.parseColor("#80000000"))
            binding.tvActive.setBackgroundColor(Color.parseColor("#0D000000"))
        }

        binding.ivClose.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.fabEditMap.setOnClickListener {
            var longitude = 112.0
            var latitude = -7.0

            if (companyViewModel.companyId != -1) {
                latitude = companyViewModel.companyLiveData2!!.value!!.latitude!!.toDouble()
                longitude = companyViewModel.companyLiveData2!!.value!!.longitude!!.toDouble()
            }

            val locationPickerIntent = LocationPickerActivity.Builder()
                .withLocation(latitude, longitude)
                // .withGeolocApiKey("<PUT API KEY HERE>")
                .withGooglePlacesApiKey("AIzaSyANbtJ9gsTcihUxmwu_WSPlZ-sJY6h252Y")
                .withSearchZone("id_ID")
                //.withSearchZone(SearchZoneRect(LatLng(3.0, 127.0), LatLng(2.5, 100.0)))
                .withDefaultLocaleSearchZone()
                // .shouldReturnOkOnBackPressed()
//                 .withStreetHidden()
//                 .withCityHidden()
//                 .withZipCodeHidden()
                .withSatelliteViewHidden()
                .withGoogleTimeZoneEnabled()
                // .withVoiceSearchHidden()
                .withUnnamedRoadHidden()
                .build(requireContext())

            // this is optional if you want to return RESULT_OK if you don't set the
            // latitude/longitude and click back button
            locationPickerIntent.putExtra("test", "this is a test")

            startActivityForResult(locationPickerIntent, 100)
        }

        binding.btnDelete.setOnClickListener {
            companyViewModel.deleteCompany(requireContext(), companyViewModel.companyLiveData2!!.value!!)
            activity?.onBackPressed()
        }

        binding.btnCancel.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            if (binding.etName.getText().isEmpty()) {
                binding.etName.setError(getString(R.string.name_empty_alert))
            }
            else {
                if (companyViewModel.companyId == -1) {
                    companyViewModel.insertCompany(requireContext(), binding.etName.getText(), binding.etAddress.getText(),
                        binding.etCity.getText(), binding.etZipCode.getText(), companyViewModel.companyStatus, "112.0", "-7.0")
                }
                else {
                    val cp = companyViewModel.companyLiveData2!!.value!!
                    cp.name = binding.etName.getText()
                    cp.address = binding.etAddress.getText()
                    cp.city = binding.etCity.getText()
                    cp.zipCode = binding.etZipCode.getText()
                    cp.status = companyViewModel.companyStatus
                    cp.longitude = companyViewModel.companyLongitude.toString()
                    cp.latitude = companyViewModel.companyLatitude.toString()
                    companyViewModel.updateCompany(requireContext(), cp)
                }
                activity?.onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap) {
        gMap = map

        if (companyViewModel.companyId == -1) {
            val marker = LatLng(-7.0, 112.0)
            map.addMarker(MarkerOptions()
                .position(marker))
            map.moveCamera(CameraUpdateFactory.newLatLng(marker))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val latitude = data.getDoubleExtra(LATITUDE, 0.0)
            Log.d("LATITUDE****", latitude.toString())
            val longitude = data.getDoubleExtra(LONGITUDE, 0.0)
            Log.d("LONGITUDE****", longitude.toString())
            val address = data.getStringExtra(LOCATION_ADDRESS)
            Log.d("ADDRESS****", address.toString())
            val postalcode = data.getStringExtra(ZIPCODE)
            Log.d("POSTALCODE****", postalcode.toString())
            val bundle = data.getBundleExtra(TRANSITION_BUNDLE)
            Log.d("BUNDLE TEXT****", bundle?.getString("test").toString())
            val fullAddress = data.getParcelableExtra<Address>(ADDRESS)
            if (fullAddress != null) {
                Log.d("FULL ADDRESS****", fullAddress.toString())
            }
            val timeZoneId = data.getStringExtra(TIME_ZONE_ID)
            if (timeZoneId != null) {
                Log.d("TIME ZONE ID****", timeZoneId)
            }
            val timeZoneDisplayName = data.getStringExtra(TIME_ZONE_DISPLAY_NAME)
            if (timeZoneDisplayName != null) {
                Log.d("TIME ZONE NAME****", timeZoneDisplayName)
            }

            companyViewModel.companyLongitude = longitude
            companyViewModel.companyLatitude = latitude

            val marker = LatLng(latitude, longitude)
            gMap.clear()
            gMap.addMarker(MarkerOptions()
                .position(marker))
            gMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        }
    }
}