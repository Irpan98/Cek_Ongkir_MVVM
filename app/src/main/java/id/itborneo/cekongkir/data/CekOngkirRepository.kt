package id.itborneo.cekongkir.data

import android.util.Log
import id.itborneo.cekongkir.data.jsonmodel.JsonCity
import id.itborneo.cekongkir.data.jsonmodel.JsonCost
import id.itborneo.cekongkir.data.model.City
import id.itborneo.cekongkir.data.model.CostPost
import id.itborneo.cekongkir.data.model.CostResponse
import id.itborneo.cekongkir.data.model.ItemCostsRespose
import id.itborneo.cekongkir.data.remote.rajaongkir.RetrofitClientInstance
import id.itborneo.cekongkir.ui.mainactivity.MainActivity
import id.itborneo.cekongkir.ui.mainactivity.MainActivityKurir
import id.itborneo.cekongkir.utils.searchbar.SuggestionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekOngkirRepository(private val mainActivity: MainActivity) {

    val costPosts = mutableListOf<CostPost>()

    private var indexCostReq: Int = 0

    val listCostRespose = ArrayList<CostResponse>()


    private val TAG = "CekOngkirRepository"

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

    fun reqCost(costPost: MutableList<CostPost>) {

        costPost.forEach {
            Log.d(TAG, "costPost for each ${it}")
            costPosts.add(it)
            req2Cost(it)


        }
    }

    private fun req2Cost(costPost: CostPost) {
//        Log.d(TAG, "request $costPost $indexCostReq")
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
                            MainActivityKurir().getCourier()[indexCostReq],
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

                Log.d(TAG, "costResponse+ $costResponse")

                listCostRespose.add(costResponse)
                Log.d(TAG, "response+ $listCostRespose")


                indexCostReq += 1
                Log.d(TAG, "indexCostReq  $indexCostReq and ${costPosts.size}")

                if (indexCostReq == costPosts.size) {


                    mainActivity.moveActivity(listCostRespose)
                    indexCostReq = 0
                }

            }

        })


    }
}