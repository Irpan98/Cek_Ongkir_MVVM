package id.itborneo.cekongkir.data.remote.mapbox

import id.itborneo.cekongkir.data.jsonmodel.mapbox.jsonPlaces
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface MapBoxGetDataService {


    //    https://api.mapbox.com/geocoding/v5/mapbox.places/aceh.json?country=id

    @GET
    fun SearchPlaces(@Url url: String?): Call<jsonPlaces>


}