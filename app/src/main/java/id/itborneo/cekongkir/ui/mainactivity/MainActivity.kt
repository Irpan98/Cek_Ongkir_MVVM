package id.itborneo.cekongkir.ui.mainactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.itborneo.cekongkir.R
import id.itborneo.cekongkir.data.model.CostPost
import id.itborneo.cekongkir.data.model.CostResponse
import id.itborneo.cekongkir.ui.details.DetailActivity
import id.itborneo.cekongkir.ui.mainactivity.search.MainSearch
import id.itborneo.cekongkir.utils.COST_INTENT
import id.itborneo.cekongkir.utils.searchbar.SuggestionAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var mainKurir: MainActivityKurir
    private lateinit var mainViewModel: MainViewModel
    private val mainSeach = MainSearch(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        initSearch()
        initViewKurir()
        initCekOngkirListener()

    }

    private fun initCekOngkirListener() {
        btn_cek_ongkir.setOnClickListener {
            val costs = mutableListOf<CostPost>()

            for (i in 0 until mainKurir.getCourier().size) {
                costs.add(mainSeach.getcost().copy(courier = mainKurir.getCourier()[i].kurir))
            }
            mainViewModel.reqCost(costs)
        }
    }

    private fun initViewKurir() {
        mainKurir = MainActivityKurir()
        setCourierView()
    }

    private fun initSearch() {
        mainSeach.attachSearchBar(
            sb_kota_pengirim,
            sb_kota_tujuan,
            ml_root
        )
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        mainViewModel.setMainActivity(this)
    }


    fun cityFromApi(adapter: SuggestionAdapter) {
        mainViewModel.cityFromApi(adapter)
    }

    fun moveActivity(costResponses: ArrayList<CostResponse>?) {
        Intent(this, DetailActivity::class.java).apply {
            putParcelableArrayListExtra(COST_INTENT, costResponses)
            startActivity(this)
        }
    }

    private fun setCourierView() {
        val courierAdapter = KurirRecyclerViewAdapter(mainKurir.getCourier()) {}
        rv_kurir.adapter = courierAdapter
        rv_kurir.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
}