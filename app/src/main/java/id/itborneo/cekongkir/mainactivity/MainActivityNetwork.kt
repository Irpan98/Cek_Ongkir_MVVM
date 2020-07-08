package id.itborneo.cekongkir.mainactivity

import android.util.Log
import id.itborneo.cekongkir.jsonmodel.JsonCity
import id.itborneo.cekongkir.jsonmodel.JsonCost
import id.itborneo.cekongkir.model.*
import id.itborneo.cekongkir.network.RetrofitClientInstance
import id.itborneo.cekongkir.searchbar.SuggestionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityNetwork(private val mainActivity: MainActivity) {

    private val TAG = "MainActivity : Network"

    fun cityFromApi(adapter: SuggestionAdapter) {
        val cities = mutableListOf<City>()


        val service = RetrofitClientInstance.retrofitInstance
        val call = service?.allCity()

        call?.enqueue(object : Callback<JsonCity> {
            override fun onFailure(call: Call<JsonCity>, t: Throwable) {
//                Log.d(TAG, "Retrofit: onFailure")
            }


            override fun onResponse(call: Call<JsonCity>, response: Response<JsonCity>) {
//                Log.d(TAG, "Retrofit: onSuccess")

                val result = response.body()

                val JsonlistKota = result?.rajaongkir?.results


                JsonlistKota?.forEach {
                    cities.add(City(it.cityId, it.cityName))
                }

                adapter.maxSuggestionsCount = 3


                adapter.cities(cities)

                Log.d(TAG, "Retrofit: onSuccess call, ${response.body()}")

            }
        })

    }


    fun reqCost(costPost: CostPost) {

        val service = RetrofitClientInstance.retrofitInstance
        val call = service?.getCost(costPost)

        call?.enqueue(object : Callback<JsonCost> {
            override fun onFailure(call: Call<JsonCost>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<JsonCost>, response: Response<JsonCost>) {
                Log.d(TAG, response.body().toString())

                val result = response.body()

                val jsonCost: JsonCost = result ?: return
                val rajaongkir = jsonCost.rajaongkir

                val ongkirResults = rajaongkir?.results

                val itemCostsRespose = mutableListOf<ItemCostsRespose>()
                val ongkircosts = ongkirResults?.get(0)?.costs





                ongkircosts?.forEach {
                    itemCostsRespose.add(
                        ItemCostsRespose(
                            mainActivity.getCourier()[0],
                            it?.service,
                            it?.description,
                            it?.cost?.get(0)?.value,
                            it?.cost?.get(0)?.etd
                            )

                    )
                }

                val costResponse = CostResponse(
                    jsonCost.rajaongkir?.originDetails?.cityName,
                    jsonCost.rajaongkir?.destinationDetails?.cityName,
                    jsonCost.rajaongkir?.query?.courier,
                    "",
                    itemCostsRespose

                    )

                val listCostRespose = ArrayList<CostResponse>()
                listCostRespose.add(costResponse)
                Log.d(TAG,"response+ $listCostRespose")

                mainActivity.moveActivity(listCostRespose)


            }

        })


    }

}