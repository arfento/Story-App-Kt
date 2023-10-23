package com.pinto.storyappkt.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.pinto.storyappkt.R
import com.pinto.storyappkt.data.models.stories.Story
import com.pinto.storyappkt.data.remote.Result
import com.pinto.storyappkt.databinding.ActivityMapsBinding
import com.pinto.storyappkt.utils.ViewModelFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback{
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val mapViewModel: MapsViewModel by viewModels{
        ViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        mapViewModel.getStoriesWithLocation().observe(this){
            if(it !=null){
                when(it){
                    is Result.Loading -> {
//                        showLoading
                    }
                    is Result.Error -> {
                        Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                    }
                    is Result.Success -> {
                        addManyMarker(it.data.listStory)

                    }
                }
            }
        }
    }

            private val boundsBuilder = LatLngBounds.builder()
    private fun addManyMarker(listStory: List<Story>) {
        listStory.forEach {story ->
            val latLng = LatLng(story.lat, story.lon)
            mMap.addMarker(MarkerOptions().position(latLng)
                .title(story.name))
            boundsBuilder.include(latLng)
        }

        val bounds : LatLngBounds = boundsBuilder.build()

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                30
            )
        )
    }
}