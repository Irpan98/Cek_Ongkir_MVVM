package id.itborneo.cekongkir.network.rajaongkir

import id.itborneo.cekongkir.jsonmodel.JsonCity
import id.itborneo.cekongkir.jsonmodel.JsonCost
import id.itborneo.cekongkir.model.CostPost
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface GetDataService {


    @Headers("key: a4f85abf61d573caf0e0b6667080389f")
    @GET("city/")
    fun allCity(): Call<JsonCity>

    @Headers("key: a4f85abf61d573caf0e0b6667080389f")
    @POST("cost/")
    fun getCost(@Body body: CostPost): Call<JsonCost>


}

