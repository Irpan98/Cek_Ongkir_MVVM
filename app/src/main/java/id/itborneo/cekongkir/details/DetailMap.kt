package id.itborneo.cekongkir.details

import android.os.Bundle
import android.util.Log
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import id.itborneo.cekongkir.R
import id.itborneo.cekongkir.jsonmodel.mapbox.jsonPlaces
import id.itborneo.cekongkir.model.CostResponse
import id.itborneo.cekongkir.network.mapbox.MapBoxIntance
import id.itborneo.cekongkir.network.mapbox.url_end
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class DetailMap(private val detailActivity: DetailActivity) : OnMapReadyCallback {

    private val TAG = "DetailMap"

    private lateinit var mapView: MapView

    private lateinit var listCost: MutableList<CostResponse>

    private lateinit var mapboxMap: MapboxMap
    fun initialize() {
        Mapbox.getInstance(detailActivity, detailActivity.getString(R.string.access_token));

    }


    fun onCreate(savedInstanceState: Bundle?, mapView: MapView, listCost: MutableList<CostResponse>) {
        this.listCost  =  listCost
        this.mapView = mapView

        mapView.onStart()
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)



    }


    fun onStart() {
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        mapboxMap.setStyle(detailActivity.getString(R.string.navigation_guidance_day))

        this.mapboxMap = mapboxMap

        var originString = listCost[0].originCity ?: return
        var destinationString = listCost[0].DestionationCity ?: return

        originString = originString.replace(" ".toRegex(),"%20")
        destinationString = destinationString.replace(" ".toRegex(),"%20")

        setOrigin(originString)
        setDestionation(destinationString)

    }


    private fun setOrigin(keyword: String) {

        val service = MapBoxIntance.retrofitIntance
        val call = service?.SearchPlaces(keyword + url_end)

        call?.enqueue(object : Callback<jsonPlaces> {
            override fun onFailure(call: Call<jsonPlaces>, t: Throwable) {
                Log.d(TAG, "onfailure called ${t.message}")
            }

            override fun onResponse(call: Call<jsonPlaces>, response: Response<jsonPlaces>) {
                Log.d(TAG, "onResponse called ${response.body()}")

                mapboxMap.uiSettings.setAllGesturesEnabled(false);


                response.body()?.let { updateMap(it, null) }

            }

        })

    }
    private fun setDestionation(keyword: String) {

        val service = MapBoxIntance.retrofitIntance
        val call = service?.SearchPlaces(keyword + url_end)

        call?.enqueue(object : Callback<jsonPlaces> {
            override fun onFailure(call: Call<jsonPlaces>, t: Throwable) {
                Log.d(TAG, "onfailure called ${t.message}")
            }

            override fun onResponse(call: Call<jsonPlaces>, response: Response<jsonPlaces>) {
                Log.d(TAG, "onResponse called ${response.body()}")



                response.body()?.let { updateMap(null, it) }

            }

        })

    }

    lateinit var originlatLng: LatLng
    lateinit var destinationLatLng : LatLng

    fun updateMap(originJsonPlace: jsonPlaces?, destinationjsonPlaces: jsonPlaces?) {



        if(destinationjsonPlaces == null){
             val originCoodinate  = originJsonPlace?.features?.get(0)?.geometry?.coordinates ?: return
             originlatLng = getLatLng(originCoodinate) ?: return

        }else if( originJsonPlace == null){
             val destinationCoordinate = destinationjsonPlaces.features?.get(0)?.geometry?.coordinates ?: return
             destinationLatLng = getLatLng(destinationCoordinate) ?: return

        }


        if (!this::originlatLng.isInitialized|| !this::destinationLatLng.isInitialized) return

        Log.d(TAG, "originLatlng: $originlatLng and destiLatLng : $destinationLatLng")

        val latLngBounds = LatLngBounds.Builder()
            .include(originlatLng)
            .include(destinationLatLng)
            .build()

        mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), 5000)

//        val position: CameraPosition = CameraPosition.Builder()
//            .target(originlatLng)
//            .zoom(10.0)
//            .tilt(20.0)
//            .build()
//        mapboxMap.cameraPosition = position

        addRoute(
            Point.fromLngLat( originlatLng.longitude,originlatLng.latitude),
            Point.fromLngLat( destinationLatLng.longitude,destinationLatLng.latitude)
        )
    }

    private fun getLatLng(coordinate: List<Double?>?): LatLng? {
        if (coordinate == null) return null

        val lng: Double? = coordinate[0]
        val lat: Double? = coordinate[1]


        val latLng =
            lat?.let {
                lng?.let { lng ->
                    LatLng(it, lng)
                }
            }

        return latLng
    }

    private val accesToken = "pk.eyJ1IjoiaXJwYW4iLCJhIjoiY2tjZzNnenU4MGlvYzJxbzM5cWd3MTI3MCJ9.ZbxgRVOg1NxgXcjjVvUA6w"
    private fun addRoute(origin: Point, destination: Point) {

        Mapbox.getAccessToken()?.let {
            NavigationRoute.builder(detailActivity)
                .accessToken(it)
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(object : Callback<DirectionsResponse> {

                    override fun onResponse(
                        call: Call<DirectionsResponse>,
                        response: Response<DirectionsResponse>
                    ) {
                        Log.d(TAG,"onRespose ROute"+response.toString())
                        Log.d(TAG,"onRespose ROute2"+response.body().toString())

                        val direction = response.body() ?:return

                        Log.d(TAG,direction.routes().toString())


                        val navigationMapRoute =
                            NavigationMapRoute(
                                null,
                                mapView,
                                mapboxMap,
                                R.style.NavigationMapRoute
                            )

                        val currentRoute = direction.routes()[0]
                        Log.d(TAG,"currentRoute ${currentRoute.toString()}")

                        navigationMapRoute.addRoute(currentRoute)
                        Log.d(TAG,"navigationMapRoute ${navigationMapRoute.toString()}")


                    }

                    override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {

                    }


                })
        }


    }





}