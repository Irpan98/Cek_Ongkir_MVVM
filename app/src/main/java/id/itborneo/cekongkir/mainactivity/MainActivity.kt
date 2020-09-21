package id.itborneo.cekongkir.mainactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.MaterialSearchBar
import id.itborneo.cekongkir.details.DetailActivity
import id.itborneo.cekongkir.KurirRecyclerViewAdapter
import id.itborneo.cekongkir.R
import id.itborneo.cekongkir.model.City
import id.itborneo.cekongkir.model.CostPost
import id.itborneo.cekongkir.model.CostResponse
import id.itborneo.cekongkir.model.Kurir
import id.itborneo.cekongkir.searchbar.SuggestionAdapter
import id.itborneo.cekongkir.utils.COST_INTENT
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var costPost = CostPost()

    private lateinit var suggestionAdapterPengirim: SuggestionAdapter
    private lateinit var suggestionAdapterTujuan: SuggestionAdapter
    private lateinit var mainActivityNetwork: MainActivityNetwork


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        suggestionAdapterPengirim = setSearchAdapter(sb_kota_pengirim)
        suggestionAdapterTujuan = setSearchAdapter(sb_kota_tujuan)

        val mainActivitySearch =
            MainActivitySearch(this, ml_root )


        mainActivityNetwork = MainActivityNetwork(this)



       mainActivitySearch.searchKota(sb_kota_pengirim, suggestionAdapterPengirim)
        mainActivitySearch.searchKota(sb_kota_tujuan, suggestionAdapterTujuan)

        setCourierView()


        //costPost.courier = "jne"

        val costs = mutableListOf<CostPost>()



        btn_cek_ongkir.setOnClickListener {

            for (i in 0 until kurir.size) {

                costs.add(costPost.copy(courier = kurir[i].kurir))

                Log.d(TAG,"costpos $i ${costs[i]}")

            }



            Log.d(TAG,"costpost $costs")
            mainActivityNetwork.reqCost(costs)



        }



        sb_kota_pengirim.setOnSearchActionListener(mainActivitySearch)
        sb_kota_tujuan.setOnSearchActionListener(mainActivitySearch)



    }


    private lateinit var kurir: MutableList<Kurir>

    private fun setCourierView() {

        val courierImage = mutableListOf("ic_jne_logo", "ic_pos_logo", "ic_tiki_logo")

        kurir = mutableListOf(
            Kurir("jne", courierImage[0]),
            Kurir("pos", courierImage[1]),
            Kurir("tiki", courierImage[2])
        )


        val courierAdapter =
            KurirRecyclerViewAdapter(kurir) {

            }

        rv_kurir.adapter = courierAdapter
        rv_kurir.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


    }


    fun getCourier(): MutableList<Kurir> {
        return kurir
    }


    private fun setSearchAdapter(searchBar: MaterialSearchBar): SuggestionAdapter {
        val inflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater



        searchBar.setOnClickListener {
            searchBar.onClick(it) //not override click

        }


        return SuggestionAdapter(inflater) { city: City, _: View ->

            if (searchBar == sb_kota_pengirim) {
                costPost.origin = city.id

            } else {
                costPost.destination = city.id
            }

            Log.d(TAG, city.name)

            searchBar.apply {
                setPlaceHolder(city.name)
                setMaxSuggestionCount(3)
                closeSearch()
            }
        }
    }

    fun cityFromApi(adapter: SuggestionAdapter) {
        mainActivityNetwork.cityFromApi(adapter)
    }

    fun moveActivity(costResponses: ArrayList<CostResponse>?) {
        Intent(this, DetailActivity::class.java).apply {
            putParcelableArrayListExtra(COST_INTENT, costResponses)
            startActivity(this)
        }
    }
}