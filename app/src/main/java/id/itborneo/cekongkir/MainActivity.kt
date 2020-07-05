package id.itborneo.cekongkir

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mancj.materialsearchbar.MaterialSearchBar
import id.itborneo.cekongkir.jsonmodel.JsonCity
import id.itborneo.cekongkir.model.City
import id.itborneo.cekongkir.network.RetrofitClientInstance
import id.itborneo.cekongkir.searchbar.SuggestionAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"


    lateinit var SuggestionAdapterPengirim: SuggestionAdapter
    lateinit var SuggestionAdapterTujuan: SuggestionAdapter


    //var listKota = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        SuggestionAdapterPengirim = setSearchAdapter(sb_kota_pengirim)
        SuggestionAdapterTujuan = setSearchAdapter(sb_kota_tujuan)


        // search kota in searchbar
        searchKota(sb_kota_pengirim, SuggestionAdapterPengirim)
        searchKota(sb_kota_tujuan, SuggestionAdapterTujuan)


    }

    private fun setSearchAdapter(searchBar: MaterialSearchBar): SuggestionAdapter {
        val inflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


        return SuggestionAdapter(inflater) { city: City, itemView: View ->


            Log.d(TAG, city.name)

            searchBar.apply {
                setPlaceHolder(city.name)
                setMaxSuggestionCount(3)
                closeSearch()

            }


        }
    }

    private fun CityFromAPI(adapter: SuggestionAdapter) {
        var cities = mutableListOf<City>()

        adapter.suggestions = cities

        //val service = RetrofitClientInstance.retrofitInstance?.(GetDataService::class.java)
        val service = RetrofitClientInstance.retrofitInstance
        val call = service?.allCity()

        call?.enqueue(object : retrofit2.Callback<JsonCity> {
            override fun onFailure(call: Call<JsonCity>, t: Throwable) {
                Log.d(TAG, "Retrofit: onFailure")
            }


            override fun onResponse(call: Call<JsonCity>, response: Response<JsonCity>) {
                Log.d(TAG, "Retrofit: onSuccess")

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


    private fun searchKota(searchBar: MaterialSearchBar, adapter: SuggestionAdapter) {



        searchBar.setCustomSuggestionAdapter(adapter)


        CityFromAPI(adapter)


        searchBar.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "sb_text changed $s")
                // send the entered text to our filter and let it manage everything

//                if (!s.isNullOrBlank()) {
                    adapter.filter.filter(searchBar.text)
//                    Log.d(TAG, "textChanged; not null")
//                } else {
//                    Log.d(TAG, "textChanged; isNull")

//                }

            }
        })

    }


}