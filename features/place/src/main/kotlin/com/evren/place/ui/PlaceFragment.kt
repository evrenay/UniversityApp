package com.evren.place.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.evren.core.actions.openAuthentication
import com.evren.core.actions.openDetail
import com.evren.core.base.lifecycle.BaseDaggerFragment
import com.evren.core.extensions.observe
import com.evren.place.R
import com.evren.place.databinding.FragmentPlaceBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class PlaceFragment : BaseDaggerFragment<FragmentPlaceBinding>() {

    //region Properties

    lateinit var googleMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        observe(viewModel.sharedValues.sharedUniversity){
            it?.let {list->
                list.forEach {
                    it.lat?.let { it1 -> it.lng?.let { it2 -> it.name?.let { it3 ->
                        it.id?.let { it4->
                            addMarker(it1, it2, it3,it4
                            )
                        }
                    } } }
                }
            }
        }

    }

    private val viewModel by viewModels<PlaceViewModel> { viewModelFactory }
    //endregion

    //region Functions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }

    override fun observeDataChanges() {
        observe(viewModel.errorData) {
            showSnackbarWithAction(it) {
                viewModel.retry()
            }
        }


    }


    fun addMarker(lat: Double , lng : Double , universityName: String,uniId : Int){

        val location = LatLng(lat,lng)
        val options = MarkerOptions().position(location)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        val marker = googleMap.addMarker(options)
        marker.title = universityName
        marker.tag = uniId
        marker.showInfoWindow()

        googleMap.setOnInfoWindowClickListener {

                when(viewModel.userInfoInstance.isLoginUser()){
                    true ->{
                        openDetail(it.tag as Int)
                    }
                    false->{
                        openAuthentication(it.tag as Int)
                    }
                }

        }

        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter{
            override fun getInfoContents(p0: Marker?): View? {
                return null
            }

            override fun getInfoWindow(p0: Marker?): View {
                val v: View = layoutInflater.inflate(R.layout.info_marker, null)
                val tvName = v.findViewById<View>(R.id.txtUniName) as TextView
                val btnDetail = v.findViewById<View>(R.id.btnDetail) as Button

                tvName.text = p0?.title
                btnDetail.setOnClickListener {
                    Toast.makeText(context,universityName,Toast.LENGTH_SHORT).show()
                }
                return v
            }

        })
    }


    override fun getLayoutId(): Int = R.layout.fragment_place
    //endregion
}
