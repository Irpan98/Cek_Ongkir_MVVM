package id.itborneo.cekongkir.network

import id.itborneo.cekongkir.jsonmodel.JsonCity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

public interface GetDataService {


    @Headers("key: a4f85abf61d573caf0e0b6667080389f")
    @GET("city/")
    fun allCity(): Call<JsonCity>


}

