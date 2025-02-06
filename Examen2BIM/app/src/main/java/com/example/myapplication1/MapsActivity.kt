package com.example.myapplication1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Inicializa el mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Obtener la latitud y longitud desde el Intent
        val lat = intent.getDoubleExtra("latitud", 0.0)
        val lng = intent.getDoubleExtra("longitud", 0.0)

        // Verificar que las coordenadas sean válidas
        if (lat != 0.0 && lng != 0.0) {
            // Establecer la ubicación del concesionario en el mapa
            val location = LatLng(lat, lng)
            mMap.addMarker(MarkerOptions().position(location).title("Concesionario"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        } else {
            // Si no se reciben coordenadas válidas, mostrar un mensaje o hacer algo adicional
            // Mostrar un marcador predeterminado si no se encuentran coordenadas válidas
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 2f))
        }
    }
}
